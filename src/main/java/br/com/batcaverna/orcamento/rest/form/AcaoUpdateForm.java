package br.com.batcaverna.orcamento.rest.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class AcaoUpdateForm {

    @NotEmpty
    @NotBlank(message = "O Nome não pode estar em branco.")
    @Size(max = 100)
    private String nome;

    @NotNull(message = "O código não pode estar em branco.")
    @Max(value = 100)
    private Long codigo;

    @NotNull(message = "Data de alteração não pode ser nula.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAlteracao;

}