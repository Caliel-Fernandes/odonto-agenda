package br.com.calidev.odonto_agenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(

        @NotBlank(message = "Serviço")
        String servico,

        @NotBlank(message = "Profissional")
        String profissional,

        @NotNull(message = "Data e hora")
        LocalDateTime dataHoraAgendamento,

        @NotBlank(message = "Cliente")
        String cliente,

        @NotBlank(message = "Telefone")
        String telefoneCliente
){
}
