package com.uniq.gestao.service;

import com.uniq.gestao.model.ProdutoFinal;
import com.uniq.gestao.repository.ProdutoFinalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoFinalService {

    private final ProdutoFinalRepository produtoFinalRepository;

    @Autowired
    public ProdutoFinalService(ProdutoFinalRepository produtoFinalRepository) {
        this.produtoFinalRepository = produtoFinalRepository;
    }

    public List<ProdutoFinal> listarTodos() {
        return produtoFinalRepository.findAll();
    }

    public Optional<ProdutoFinal> buscarPorId(Long id) {
        return produtoFinalRepository.findById(id);
    }

    public ProdutoFinal salvar(ProdutoFinal produtoFinal) {
        return produtoFinalRepository.save(produtoFinal);
    }

    public void excluir(Long id) {
        produtoFinalRepository.deleteById(id);
    }
}