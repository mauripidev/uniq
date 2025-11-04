package com.uniq.gestao.controller;

import com.uniq.gestao.model.ProdutoFinal;
import com.uniq.gestao.service.ProdutoFinalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos-finais")
public class ProdutoFinalController {

    private final ProdutoFinalService produtoFinalService;

    @Autowired
    public ProdutoFinalController(ProdutoFinalService produtoFinalService) {
        this.produtoFinalService = produtoFinalService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoFinal>> listarTodos() {
        return ResponseEntity.ok(produtoFinalService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoFinal> buscarPorId(@PathVariable Long id) {
        return produtoFinalService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProdutoFinal> criar(@Valid @RequestBody ProdutoFinal produtoFinal) {
        ProdutoFinal produtoSalvo = produtoFinalService.salvar(produtoFinal);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoFinal> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoFinal produtoFinal) {
        return produtoFinalService.buscarPorId(id)
                .map(produtoExistente -> {
                    produtoFinal.setId(id);
                    return ResponseEntity.ok(produtoFinalService.salvar(produtoFinal));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!produtoFinalService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        produtoFinalService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}