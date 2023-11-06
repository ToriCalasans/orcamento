package br.com.batcaverna.orcamento.rest.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class LancamentosUpdateForm {
    private Byte lancamentoInvalido;
    private int numeroLancamento;

    @Size(max = 255)
    private String descricao;

    private LocalDate dataLancamento;
    private int idLancamentoPai;
    private Float valor;

    @NotNull
    private int idTipoLancamento;

    @NotNull
    private int idUnidade;

    @NotNull
    private int idUnidadeOrcamentaria;

    @NotNull
    private int idPrograma;

    @NotNull
    private int idAcao;

    @NotNull
    private int idFonteRecurso;

    @NotNull
    private int idGrupoDespesa;

    @NotNull
    private int idModalidadeAplicacao;

    @NotNull
    private int idElementoDespesa;

    private int idSolicitante;
    private int idObjetivoEstrategico;

    @NotNull
    private int idTipoTransacao;

    @Size(max = 27)
    private String ged;

    @Size(max = 255)
    private String contratado;
}
