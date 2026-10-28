package com.Project.Online_Food_Ordering_System.service;

import com.Project.Online_Food_Ordering_System.dto.OrderRequestDto;
import com.Project.Online_Food_Ordering_System.dto.OrderResponseDto;
import com.Project.Online_Food_Ordering_System.entity.Customers;
import com.Project.Online_Food_Ordering_System.entity.Menu_items;
import com.Project.Online_Food_Ordering_System.entity.Orders;
import com.Project.Online_Food_Ordering_System.entity.Resturants;
import com.Project.Online_Food_Ordering_System.exception.BadRequestException;
import com.Project.Online_Food_Ordering_System.exception.ResourceNotFoundException;
import com.Project.Online_Food_Ordering_System.repository.CustomersRepository;
import com.Project.Online_Food_Ordering_System.repository.Menu_itemsRepository;
import com.Project.Online_Food_Ordering_System.repository.OrdersRepository;
import com.Project.Online_Food_Ordering_System.repository.ResturantsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final CustomersRepository customersRepository;
    private final ResturantsRepository resturantsRepository;
    private final Menu_itemsRepository menuItemsRepository;

    public OrdersService(
            OrdersRepository ordersRepository,
            CustomersRepository customersRepository,
            ResturantsRepository resturantsRepository,
            Menu_itemsRepository menuItemsRepository
    ) {
        this.ordersRepository = ordersRepository;
        this.customersRepository = customersRepository;
        this.resturantsRepository = resturantsRepository;
        this.menuItemsRepository = menuItemsRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> findAll() {
        return ordersRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDto findById(Long id) {
        return toResponse(getOrder(id));
    }

    public OrderResponseDto create(OrderRequestDto request) {
        Orders order = new Orders();
        applyRequest(order, request, true);
        return toResponse(ordersRepository.save(order));
    }

    public OrderResponseDto update(Long id, OrderRequestDto request) {
        Orders order = getOrder(id);
        applyRequest(order, request, false);
        return toResponse(ordersRepository.save(order));
    }

    public void delete(Long id) {
        ordersRepository.delete(getOrder(id));
    }

    private Orders getOrder(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    private Customers getCustomer(Long id) {
        return customersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    private Resturants getRestaurant(Long id) {
        return resturantsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found: " + id));
    }

    private void applyRequest(Orders order, OrderRequestDto request, boolean creating) {
        Resturants restaurant = getRestaurant(request.restaurantId());
        Set<Long> itemIds = request.menuItemIds() == null ? Set.of() : request.menuItemIds();
        Set<Menu_items> menuItems = new HashSet<>();

        for (Long itemId : itemIds) {
            Menu_items menuItem = menuItemsRepository.findById(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found: " + itemId));
            if (!menuItem.getRestaurant().getRestaurantId().equals(restaurant.getRestaurantId())) {
                throw new BadRequestException(
                        "Menu item " + itemId + " does not belong to restaurant " + restaurant.getRestaurantId()
                );
            }
            menuItems.add(menuItem);
        }

        order.setOrderDate(request.orderDate() == null && creating
                ? LocalDateTime.now()
                : request.orderDate() == null ? order.getOrderDate() : request.orderDate());
        order.setStatus(request.status());
        order.setTotalAmount(request.totalAmount());
        order.setCustomer(getCustomer(request.customerId()));
        order.setRestaurant(restaurant);
        order.setMenuItems(menuItems);
    }

    private OrderResponseDto toResponse(Orders order) {
        Set<Long> menuItemIds = order.getMenuItems().stream()
                .map(Menu_items::getMenuItemId)
                .collect(java.util.stream.Collectors.toUnmodifiableSet());
        return new OrderResponseDto(
                order.getOrderId(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCustomer().getCustomerId(),
                order.getRestaurant().getRestaurantId(),
                menuItemIds
        );
    }
}
