package com.Project.Online_Food_Ordering_System.controller;

import com.Project.Online_Food_Ordering_System.dto.MenuItemRequestDto;
import com.Project.Online_Food_Ordering_System.dto.MenuItemResponseDto;
import com.Project.Online_Food_Ordering_System.service.Menu_itemsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class Menu_itemsController {
    private final Menu_itemsService menuItemsService;

    public Menu_itemsController(Menu_itemsService menuItemsService) {
        this.menuItemsService = menuItemsService;
    }

    @GetMapping
    public List<MenuItemResponseDto> findAll() {
        return menuItemsService.findAll();
    }

    @GetMapping("/{id}")
    public MenuItemResponseDto findById(@PathVariable Long id) {
        return menuItemsService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItemResponseDto create(@Valid @RequestBody MenuItemRequestDto request) {
        return menuItemsService.create(request);
    }

    @PutMapping("/{id}")
    public MenuItemResponseDto update(@PathVariable Long id, @Valid @RequestBody MenuItemRequestDto request) {
        return menuItemsService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        menuItemsService.delete(id);
    }
}
