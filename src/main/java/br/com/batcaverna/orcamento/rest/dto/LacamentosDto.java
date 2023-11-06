package br.com.batcaverna.orcamento.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LacamentosDto {
    private String nome;
    private Byte lacamentoInvalido;
    private LocalDate dataLacamento;
    private String descricao;
    private String ged;
    private String contratado;
    private Float valor;
    private Long anoOrcamento;
}
