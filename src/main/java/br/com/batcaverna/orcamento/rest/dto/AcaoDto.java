package br.com.batcaverna.orcamento.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcaoDto {
    private String nome;
    private Long codigo;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAlteracao;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;
}
