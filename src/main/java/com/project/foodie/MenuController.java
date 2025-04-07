package com.project.foodie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        return menuService.save(menu);
    }

    @GetMapping
    public List<Menu> getAllMenu() {
        return menuService.getAllMenu();
    }

    @GetMapping("/{id}")
    public Optional<Menu> getMenu(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
    }

}
