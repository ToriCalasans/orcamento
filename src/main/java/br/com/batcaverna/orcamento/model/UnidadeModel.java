package br.com.batcaverna.orcamento.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Unidade")
public class UnidadeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;
    @Column(name = "dataCadastro", nullable = false)
    private LocalDate dataCadastro;
    @Column(name = "dataAlteracao", nullable = false)
    private LocalDate dataAlteracao;
}
