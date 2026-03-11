package br.com.calidev.odonto_agenda.dto;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        String servico,
        String profissional,
        LocalDateTime dataHoraAgendamento,
        String cliente
) {}
