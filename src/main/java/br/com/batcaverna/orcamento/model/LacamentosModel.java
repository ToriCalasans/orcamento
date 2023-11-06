package br.com.batcaverna.orcamento.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Lacamentos")
public class LacamentosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;
    @Column(name = "lacamentoInvalido", nullable = false)
    private Byte lacamentoInvalido;
    @Column(name = "idTipolancamento", nullable = false)
    private int idTipolancamento;
    @Column(name = "dataLacamento", nullable = false)
    private LocalDate dataLacamento;
    @Column(name = "idLacamentoPai", nullable = false)
    private int idLacamentoPai;
    @Column(name = "idUnidade", nullable = false)
    private int idUnidade;
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;
    @Column(name = "idUnidadeOrcamentaria", nullable = false)
    private int idUnidadeOrcamentaria;
    @Column(name = "idPrograma", nullable = false)
    private int idPrograma;
    @Column(name = "idAcao", nullable = false)
    private int idAcao;
    @Column(name = "idFonteRecurso", nullable = false)
    private int idFonteRecurso;
    @Column(name = "idGrupoDespesa", nullable = false)
    private int idGrupoDespesa;
    @Column(name = "idSolicitante", nullable = false)
    private int idSolicitante;
    @Column(name = "ged", length = 27, nullable = false)
    private String ged;
    @Column(name = "idObjetivoEstrategico", nullable = false)
    private int idObjetivoEstrategico;
    @Column(name = "valor", nullable = false)
    private Float valor;
    @Column(name = "idTipoTransacao", nullable = false)
    private int idTipoTransacao;
    @Column(name = "anoOrcamento", nullable = false)
    private int anoOrcamento;
}
 