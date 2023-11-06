package br.com.batcaverna.orcamento.rest.form;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class LancamentoForm{

    @NotNull(message = "O campo 'lancamentoInvalido' é obrigatório.")
    private Byte lancamentoInvalido;

    private int numeroLancamento;

    @NotEmpty
    @NotBlank(message = "É essencial uma descrição preenchida.")
    @Size(max = 255)
    private String descricao;

    @NotNull(message = "A Data de Lançamento é um campo obrigatório.")
    private LocalDate dataLancamento;

    private int idLancamentoPai;

    @NotNull(message = "O Valor é um campo obrigatório.")
    private Float valor;

    private int idTipoLancamento;
    private int idUnidade;
    private int idUnidadeOrcamentaria;
    private int idPrograma;
    private int idAcao;
    private int idFonteRecurso;
    private int idGrupoDespesa;
    private int idModalidadeAplicacao;
    private int idElementoDespesa;
    private int idSolicitante;
    private int idObjetivoEstrategico;
    private int idTipoTransacao;

    private String ged;

    @Size(max = 255)
    private String contratado;


    private int anoOrcamento;
}
