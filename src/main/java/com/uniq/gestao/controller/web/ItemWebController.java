package com.uniq.gestao.controller.web;

import com.uniq.gestao.model.Item;
import com.uniq.gestao.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/itens")
public class ItemWebController {

    private final ItemService itemService;

    @Autowired
    public ItemWebController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String listarTodos(Model model) {
        model.addAttribute("itens", itemService.listarTodos());
        return "itens/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("item", new Item());
        return "itens/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("item") Item item, BindingResult result) {
        if (result.hasErrors()) {
            return "itens/form";
        }
        itemService.salvar(item);
        return "redirect:/itens";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("item", itemService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Item inválido: " + id)));
        return "itens/form";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        itemService.excluir(id);
        return "redirect:/itens";
    }
}