package com.Project.Online_Food_Ordering_System.service;

import com.Project.Online_Food_Ordering_System.dto.MenuItemRequestDto;
import com.Project.Online_Food_Ordering_System.dto.MenuItemResponseDto;
import com.Project.Online_Food_Ordering_System.entity.Menu_items;
import com.Project.Online_Food_Ordering_System.entity.Resturants;
import com.Project.Online_Food_Ordering_System.exception.ResourceNotFoundException;
import com.Project.Online_Food_Ordering_System.repository.Menu_itemsRepository;
import com.Project.Online_Food_Ordering_System.repository.ResturantsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class Menu_itemsService {
    private final Menu_itemsRepository menuItemsRepository;
    private final ResturantsRepository resturantsRepository;

    public Menu_itemsService(
            Menu_itemsRepository menuItemsRepository,
            ResturantsRepository resturantsRepository
    ) {
        this.menuItemsRepository = menuItemsRepository;
        this.resturantsRepository = resturantsRepository;
    }

    @Transactional(readOnly = true)
    public List<MenuItemResponseDto> findAll() {
        return menuItemsRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public MenuItemResponseDto findById(Long id) {
        return toResponse(getMenuItem(id));
    }

    public MenuItemResponseDto create(MenuItemRequestDto request) {
        Menu_items menuItem = new Menu_items();
        applyRequest(menuItem, request);
        return toResponse(menuItemsRepository.save(menuItem));
    }

    public MenuItemResponseDto update(Long id, MenuItemRequestDto request) {
        Menu_items menuItem = getMenuItem(id);
        applyRequest(menuItem, request);
        return toResponse(menuItemsRepository.save(menuItem));
    }

    public void delete(Long id) {
        menuItemsRepository.delete(getMenuItem(id));
    }

    private Menu_items getMenuItem(Long id) {
        return menuItemsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found: " + id));
    }

    private Resturants getRestaurant(Long id) {
        return resturantsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found: " + id));
    }

    private void applyRequest(Menu_items menuItem, MenuItemRequestDto request) {
        menuItem.setName(request.name());
        menuItem.setDescription(request.description());
        menuItem.setPrice(request.price());
        menuItem.setAvailable(request.available());
        menuItem.setRestaurant(getRestaurant(request.restaurantId()));
    }

    private MenuItemResponseDto toResponse(Menu_items menuItem) {
        return new MenuItemResponseDto(
                menuItem.getMenuItemId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isAvailable(),
                menuItem.getRestaurant().getRestaurantId()
        );
    }
}
