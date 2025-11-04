package com.uniq.gestao.repository;

import com.uniq.gestao.model.ProdutoFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoFinalRepository extends JpaRepository<ProdutoFinal, Long> {
}