package br.com.batcaverna.orcamento.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "FonteRecurso")
public class FonteRecursoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;
    @Column(name = "codigo", nullable = false, unique = true)
    private Long codigo;
    @Column(name = "dataCadastro", nullable = false)
    private LocalDate dataCadastro;
    @Column(name = "dataAlteracao", nullable = false)
    private LocalDate dataAlteracao;
}
