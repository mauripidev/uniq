package com.uniq.gestao.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoFinal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    
    private String descricao;
    
    @OneToMany(mappedBy = "produtoFinal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemProdutoFinal> itens = new ArrayList<>();
    
    public BigDecimal getCustoTotal() {
        return itens.stream()
                .map(ItemProdutoFinal::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public void adicionarItem(ItemProdutoFinal item) {
        itens.add(item);
        item.setProdutoFinal(this);
    }
    
    public void removerItem(ItemProdutoFinal item) {
        itens.remove(item);
        item.setProdutoFinal(null);
    }
}