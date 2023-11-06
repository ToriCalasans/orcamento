package br.com.batcaverna.orcamento.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "UnidadeOrcamentaria")
public class UnidadeOrcamentariaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;
    @Column(name = "codigo", nullable = false, unique = true)
    private Long codigo;
}
