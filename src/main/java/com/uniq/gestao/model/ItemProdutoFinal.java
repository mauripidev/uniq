package com.uniq.gestao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemProdutoFinal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "item_id")
    @NotNull(message = "O item é obrigatório")
    private Item item;
    
    @ManyToOne
    @JoinColumn(name = "produto_final_id")
    @JsonBackReference
    private ProdutoFinal produtoFinal;
    
    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser maior que zero")
    private BigDecimal quantidade;
    
    public BigDecimal getSubtotal() {
        return item.getPrecoCusto().multiply(quantidade);
    }
}