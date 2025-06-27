// src/main/java/com/easyevents/guest_management_service/service/ConvidadoService.java
package com.easyevents.guest_management_service.service;

import com.easyevents.guest_management_service.domain.dto.request.AdicionarConvidadoRequest;
import com.easyevents.guest_management_service.domain.dto.request.AtualizarConvidadoRequest;
import com.easyevents.guest_management_service.domain.dto.response.ConvidadoResponse;
import com.easyevents.guest_management_service.domain.enumerator.StatusConfirmacao;
import com.easyevents.guest_management_service.domain.model.ConvidadoModel;
import com.easyevents.guest_management_service.repository.ConvidadoRepository;
import com.easyevents.guest_management_service.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConvidadoService {

    private final ConvidadoRepository convidadoRepository;
    private final EventoRepository eventoRepository;

    // Método auxiliar para validar se um evento existe
    private ResponseEntity<ConvidadoResponse> validarEvento(String eventoId) {
        if (eventoId == null || eventoRepository.findById(eventoId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ConvidadoResponse.builder()
                            .message("Evento de ID " + eventoId + " não encontrado.")
                            .success(false)
                            .build());
        }
        return null;
    }

    // Adiciona um convidado a um evento específico
    public ResponseEntity<ConvidadoResponse> adicionarConvidado(AdicionarConvidadoRequest request) {
        ResponseEntity<ConvidadoResponse> eventValidation = validarEvento(request.getEventoId());
        if (eventValidation != null) return eventValidation;

        // VERIFICA UNICIDADE PELA COMBINAÇÃO DE EMAIL E EVENTO ID
        // Você precisará de um método findByEmailAndEventoId no seu ConvidadoRepository
        Optional<ConvidadoModel> existingConvidadoInEvent = convidadoRepository.findByEmailAndEventoId(request.getEmail(), request.getEventoId());

        if (existingConvidadoInEvent.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ConvidadoResponse.builder()
                            .message("Convidado com email " + request.getEmail() + " já existe para o evento ID " + request.getEventoId() + ".")
                            .success(false)
                            .build());
        }

        // Cria um novo ConvidadoModel associado a este evento
        ConvidadoModel novoConvidado = ConvidadoModel.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .eventoId(request.getEventoId()) // Associa diretamente ao ID do evento
                .statusConfirmacao(StatusConfirmacao.PENDENTE) // Define o status inicial
                .build();

        convidadoRepository.save(novoConvidado);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ConvidadoResponse.builder()
                        .message("Convidado " + request.getEmail() + " adicionado ao evento " + request.getEventoId() + " com sucesso.")
                        .success(true)
                        .guestId(novoConvidado.getId())
                        .build());
    }

    // Lista convidados para um evento específico
    public ResponseEntity<List<ConvidadoModel>> listarConvidados(String eventoId) {
        ResponseEntity<ConvidadoResponse> eventValidation = validarEvento(eventoId);
        if (eventValidation != null) return ResponseEntity.status(eventValidation.getStatusCode()).build();

        // Busca convidados diretamente pelo ID do evento
        List<ConvidadoModel> guests = convidadoRepository.findByEventoId(eventoId);

        if (guests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(guests);
    }

    // Deleta um convidado de um evento específico
    public ResponseEntity<ConvidadoResponse> deletarConvidado(String eventoId, String email) {
        ResponseEntity<ConvidadoResponse> eventValidation = validarEvento(eventoId);
        if (eventValidation != null) return eventValidation;

        // Busca o convidado pela combinação de email e eventoId
        Optional<ConvidadoModel> convidadoToDeleteOpt = convidadoRepository.findByEmailAndEventoId(email, eventoId);

        if (convidadoToDeleteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ConvidadoResponse.builder()
                            .message("Convidado com email " + email + " não encontrado para o evento ID " + eventoId + ".")
                            .success(false)
                            .build());
        }

        convidadoRepository.delete(convidadoToDeleteOpt.get());

        return ResponseEntity.ok(ConvidadoResponse.builder()
                .message("Convidado " + email + " removido do evento " + eventoId + " com sucesso.")
                .success(true)
                .build());
    }

    // Atualiza dados e status de convite de um convidado em um evento específico
    public ResponseEntity<ConvidadoResponse> atualizarConvidado(AtualizarConvidadoRequest request) {
        ResponseEntity<ConvidadoResponse> eventValidation = validarEvento(request.getEventoId());
        if (eventValidation != null) return eventValidation;

        // Busca o convidado pela combinação de email e eventoId
        Optional<ConvidadoModel> existingConvidadoOpt = convidadoRepository.findByEmailAndEventoId(request.getEmail(), request.getEventoId());

        if (existingConvidadoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ConvidadoResponse.builder()
                            .message("Convidado com email " + request.getEmail() + " não encontrado para o evento ID " + request.getEventoId() + " para atualização.")
                            .success(false)
                            .build());
        }

        ConvidadoModel convidadoToUpdate = existingConvidadoOpt.get();

        // Atualiza o nome, se fornecido
        if (request.getNome() != null && !request.getNome().isBlank()) {
            convidadoToUpdate.setNome(request.getNome());
        }
        // Atualiza o status de confirmação
        convidadoToUpdate.setStatusConfirmacao(request.getStatusConfirmacao());

        convidadoRepository.save(convidadoToUpdate);

        return ResponseEntity.ok(ConvidadoResponse.builder()
                .message("Convidado " + request.getEmail() + " atualizado no evento " + request.getEventoId() + " com sucesso.")
                .success(true)
                .guestId(convidadoToUpdate.getId())
                .build());
    }

    // Busca um convidado específico em um evento pelo email
    public ResponseEntity<ConvidadoModel> buscarPorEmail(String eventoId, String email) {
        ResponseEntity<ConvidadoResponse> eventValidation = validarEvento(eventoId);
        if (eventValidation != null) return ResponseEntity.status(eventValidation.getStatusCode()).build();

        // Busca o convidado pela combinação de email e eventoId
        Optional<ConvidadoModel> guestOpt = convidadoRepository.findByEmailAndEventoId(email, eventoId);

        return guestOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}