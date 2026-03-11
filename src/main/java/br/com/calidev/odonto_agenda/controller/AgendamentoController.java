package br.com.calidev.odonto_agenda.controller;

import br.com.calidev.odonto_agenda.dto.AgendamentoRequestDTO;
import br.com.calidev.odonto_agenda.dto.AgendamentoResponseDTO;
import br.com.calidev.odonto_agenda.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> salvar(
            @Valid @RequestBody AgendamentoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(agendamentoService.salvar(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAgendamento(@RequestParam String cliente,
                                                   @RequestParam LocalDateTime dataHoraAgendamento) {

        agendamentoService.deletarAgendamento(dataHoraAgendamento, cliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> buscarAgendamentosDia(
            @RequestParam LocalDate data) {

        return ResponseEntity.ok(
                agendamentoService.buscarAgendamentosDia(data)
        );
    }

    @PutMapping
    public ResponseEntity<AgendamentoResponseDTO> alterarAgendamento(
            @Valid @RequestBody AgendamentoRequestDTO dto,
            @RequestParam String cliente,
            @RequestParam LocalDateTime dataHoraAgendamento) {

        return ResponseEntity.ok(
                agendamentoService.alterarAgendamento(dto, cliente, dataHoraAgendamento)
        );
    }
}


