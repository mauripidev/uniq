package com.uniq.gestao.controller.web;

import com.uniq.gestao.model.ItemProdutoFinal;
import com.uniq.gestao.model.ProdutoFinal;
import com.uniq.gestao.service.ItemService;
import com.uniq.gestao.service.ProdutoFinalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos-finais")
public class ProdutoFinalWebController {

    private final ProdutoFinalService produtoFinalService;
    private final ItemService itemService;

    @Autowired
    public ProdutoFinalWebController(ProdutoFinalService produtoFinalService, ItemService itemService) {
        this.produtoFinalService = produtoFinalService;
        this.itemService = itemService;
    }

    @GetMapping
    public String listarTodos(Model model) {
        model.addAttribute("produtosFinais", produtoFinalService.listarTodos());
        return "produtos-finais/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produtoFinal", new ProdutoFinal());
        return "produtos-finais/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("produtoFinal") ProdutoFinal produtoFinal, BindingResult result) {
        if (result.hasErrors()) {
            return "produtos-finais/form";
        }
        produtoFinalService.salvar(produtoFinal);
        return "redirect:/produtos-finais";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("produtoFinal", produtoFinalService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto final inválido: " + id)));
        return "produtos-finais/form";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        produtoFinalService.excluir(id);
        return "redirect:/produtos-finais";
    }

    @GetMapping("/{id}/itens")
    public String gerenciarItens(@PathVariable Long id, Model model) {
        ProdutoFinal produtoFinal = produtoFinalService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto final inválido: " + id));
        model.addAttribute("produtoFinal", produtoFinal);
        model.addAttribute("itens", itemService.listarTodos());
        model.addAttribute("itemProdutoFinal", new ItemProdutoFinal());
        return "produtos-finais/itens";
    }

    @PostMapping("/{id}/itens")
    public String adicionarItem(@PathVariable Long id, @ModelAttribute ItemProdutoFinal itemProdutoFinal) {
        ProdutoFinal produtoFinal = produtoFinalService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto final inválido: " + id));
        produtoFinal.adicionarItem(itemProdutoFinal);
        produtoFinalService.salvar(produtoFinal);
        return "redirect:/produtos-finais/" + id + "/itens";
    }

    @GetMapping("/{produtoId}/itens/{itemId}/excluir")
    public String removerItem(@PathVariable Long produtoId, @PathVariable Long itemId) {
        ProdutoFinal produtoFinal = produtoFinalService.buscarPorId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto final inválido: " + produtoId));
        
        produtoFinal.getItens().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .ifPresent(produtoFinal::removerItem);
        
        produtoFinalService.salvar(produtoFinal);
        return "redirect:/produtos-finais/" + produtoId + "/itens";
    }
}