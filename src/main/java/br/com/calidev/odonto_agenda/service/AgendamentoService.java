package br.com.calidev.odonto_agenda.service;

import br.com.calidev.odonto_agenda.dto.AgendamentoRequestDTO;
import br.com.calidev.odonto_agenda.dto.AgendamentoResponseDTO;
import br.com.calidev.odonto_agenda.infrastructure.entity.Agendamento;
import br.com.calidev.odonto_agenda.infrastructure.repository.AgendamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    @Transactional
    public Agendamento salvarAgendamento(Agendamento agendamento) {

        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaFim = agendamento.getDataHoraAgendamento().plusHours(1);

        Agendamento agendados = agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(agendamento.getServico(),
                horaAgendamento, horaFim);


        if (Objects.nonNull(agendados)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Horário já está preenchido"
            );
        }
        return agendamentoRepository.save(agendamento);
    }

    public void deletarAgendamento(LocalDateTime dataHoraAgendamento, String cliente) {
        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }

    public List<AgendamentoResponseDTO> buscarAgendamentosDia(LocalDate data) {
        LocalDateTime primeiraHoraDia = data.atStartOfDay();
        LocalDateTime horaFinalDia = data.atTime(23, 59, 59);

        return agendamentoRepository
                .findByDataHoraAgendamentoBetween(primeiraHoraDia, horaFinalDia)
                .stream()
                .map(a -> new AgendamentoResponseDTO(
                        a.getId(),
                        a.getServico(),
                        a.getProfissional(),
                        a.getDataHoraAgendamento(),
                        a.getCliente()
                ))
                .toList();
    }

    @Transactional
    public AgendamentoResponseDTO alterarAgendamento(
            AgendamentoRequestDTO dto,
            String cliente,
            LocalDateTime dataHoraAgendamento) {

        Agendamento agendamento = agendamentoRepository
                .findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

        if (agendamento == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Agendamento não encontrado"
            );
        }

        agendamento.setServico(dto.servico());
        agendamento.setProfissional(dto.profissional());
        agendamento.setDataHoraAgendamento(dto.dataHoraAgendamento());
        agendamento.setTelefoneCliente(dto.telefoneCliente());

        Agendamento atualizado = agendamentoRepository.save(agendamento);

        return toResponseDTO(atualizado);
    }

    public AgendamentoResponseDTO salvar(AgendamentoRequestDTO dto) {

        Agendamento agendamento = new Agendamento();
        agendamento.setServico(dto.servico());
        agendamento.setProfissional(dto.profissional());
        agendamento.setDataHoraAgendamento(dto.dataHoraAgendamento());
        agendamento.setCliente(dto.cliente());
        agendamento.setTelefoneCliente(dto.telefoneCliente());

        Agendamento salvo = salvarAgendamento(agendamento);

        return toResponseDTO(salvo);
    }

    private AgendamentoResponseDTO toResponseDTO(Agendamento agendamento) {
        return new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getServico(),
                agendamento.getProfissional(),
                agendamento.getDataHoraAgendamento(),
                agendamento.getCliente()
        );
    }
}
