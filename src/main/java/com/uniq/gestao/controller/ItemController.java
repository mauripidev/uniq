package com.uniq.gestao.controller;

import com.uniq.gestao.model.Item;
import com.uniq.gestao.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> listarTodos() {
        return ResponseEntity.ok(itemService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> buscarPorId(@PathVariable Long id) {
        return itemService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Item> criar(@Valid @RequestBody Item item) {
        Item itemSalvo = itemService.salvar(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> atualizar(@PathVariable Long id, @Valid @RequestBody Item item) {
        return itemService.buscarPorId(id)
                .map(itemExistente -> {
                    item.setId(id);
                    return ResponseEntity.ok(itemService.salvar(item));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!itemService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        itemService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}