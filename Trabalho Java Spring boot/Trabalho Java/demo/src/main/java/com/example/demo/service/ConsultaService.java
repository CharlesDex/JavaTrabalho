package com.example.demo.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entities.Consulta;
import com.example.demo.Entities.Disponibilidade;
import com.example.demo.Entities.Medico;
import com.example.demo.Entities.Pacientes;
import com.example.demo.dto.ConsultaDTO;
import com.example.demo.mapper.ConsultaMapper;
import com.example.demo.repository.DisponibilidadeRepository;
import com.example.demo.repository.ConsultaRepository;
import com.example.demo.repository.MedicoRepository;
import com.example.demo.repository.PacienteRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    @Autowired
    private ConsultaMapper consultaMapper;

    // Constantes para status de consulta
    public static final String STATUS_AGENDADA = "agendada";
    public static final String STATUS_CANCELADA = "cancelada";
    public static final String STATUS_CONCLUIDA = "concluida";

    /**
     * Agenda uma nova consulta
     */
    @Transactional
    public ConsultaDTO agendar(ConsultaDTO consultaDTO) {
        Pacientes paciente = pacienteRepository.findById(consultaDTO.getPacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

        Medico medico = medicoRepository.findById(consultaDTO.getMedicoId())
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));

        LocalDateTime dataHora = consultaDTO.getDataHora();

        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data da consulta deve ser futura");
        }

        LocalTime horario = dataHora.toLocalTime();

        int diaSemana = dataHora.getDayOfWeek().getValue();
        List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByMedicoId(medico.getId());

        boolean horarioDisponivel = false;
        for (Disponibilidade disp : disponibilidades) {
            if (disp.getDiaDaSemana() == (diaSemana) && !horario.isBefore(disp.getHorarioInicio())
                    && !horario.isAfter(disp.getHorarioFim())) {
                horarioDisponivel = true;
                break;
            }
        }

        if (!horarioDisponivel) {
            throw new IllegalArgumentException("Médico não disponível neste horário");
        }

        LocalDateTime inicio = dataHora.minusMinutes(30);
        LocalDateTime fim = dataHora.plusMinutes(30);

        List<Consulta> consultasConflitantes = consultaRepository.findByMedicoIdAndDataHoraBetween(medico.getId(),
                inicio, fim);

        if (!consultasConflitantes.isEmpty()) {
            throw new IllegalArgumentException("Médico já possui consulta agendada neste horário");
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHora(dataHora);
        consulta.setStatus(STATUS_AGENDADA);
        consulta.setObservacoes(consultaDTO.getObservacoes());

        return consultaMapper.toDTO(consultaRepository.save(consulta));
    }

    /**
     * Lista todas as consultas com filtros opcionais
     */
    public List<ConsultaDTO> listar(Long medicoId, Long pacienteId, String status, LocalDateTime dataInicio,
            LocalDateTime dataFim) {
        List<String> statusList = List.of(STATUS_AGENDADA, STATUS_CANCELADA, STATUS_CONCLUIDA);
        if (status != null && (!statusList.contains(status) || status.isEmpty())) {
            throw new IllegalArgumentException("Status inválido. Use: Agendada, Cancelada ou Concluída");
        }

        if (dataInicio != null && dataFim != null && dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data de início não pode ser posterior à data de fim");
        }

        List<Consulta> consultas = consultaRepository.findAllUsing(medicoId, pacienteId, status,
                dataInicio != null ? dataInicio.toString() : null, dataFim != null ? dataFim.toString() : null);

        return consultaMapper.toDTOList(consultas);
    }

    /**
     * Busca uma consulta pelo ID
     */
    public Optional<ConsultaDTO> buscarPorId(Long id) {
        return consultaRepository.findById(id).map(consultaMapper::toDTO);
    }

    /**
     * Atualiza o status de uma consulta
     */
    @Transactional
    public ConsultaDTO atualizarStatus(Long id, String novoStatus) {
        if (!STATUS_AGENDADA.equals(novoStatus) && !STATUS_CANCELADA.equals(novoStatus)
                && !STATUS_CONCLUIDA.equals(novoStatus)) {
            throw new IllegalArgumentException("Status inválido. Use: Agendada, Cancelada ou Concluída");
        }

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        consulta.setStatus(novoStatus);

        return consultaMapper.toDTO(consultaRepository.save(consulta));
    }

    /**
     * Cancela uma consulta (só permitido com 24h de antecedência)
     */
    @Transactional
    public void cancelar(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        if (STATUS_CANCELADA.equals(consulta.getStatus())) {
            throw new IllegalArgumentException("Consulta já está cancelada");
        }

        if (consulta.getDataHora().minusHours(24).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cancelamento só é permitido com no mínimo 24 horas de antecedência");
        }

        consulta.setStatus(STATUS_CANCELADA);
        consultaRepository.save(consulta);
    }
}