package com.easyevents.guest_management_service.service;

import com.easyevents.guest_management_service.domain.dto.request.AdicionarConvidadoRequest;
import com.easyevents.guest_management_service.domain.dto.request.UpdateConvidadoRequest;
import com.easyevents.guest_management_service.domain.dto.response.ConvidadoResponse;
import com.easyevents.guest_management_service.domain.enumerator.StatusConfirmacao;
import com.easyevents.guest_management_service.domain.model.ConvidadoModel;
import com.easyevents.guest_management_service.repository.ConvidadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final ConvidadoRepository convidadoRepository; // Injeção do repositório

    // Método para adicionar um novo convidado
    public ResponseEntity<ConvidadoResponse> adicionarConvidado(AdicionarConvidadoRequest request) {
        // TODO: Adicionar validações de negócio aqui (ex: email já existe?)

        // Verificar se o convidado já existe pelo email para evitar duplicidade
        if (convidadoRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT) // 409 Conflict
                    .body(ConvidadoResponse.builder()
                            .message("Convidado com email " + request.getEmail() + " já existe.")
                            .success(false)
                            .build());
        }

        // Mapeia o DTO de requisição para o modelo de domínio
        // Você precisará de um construtor ou builder no GuestModel que aceite esses campos
        ConvidadoModel novoConvidado = ConvidadoModel.builder() // Supondo que GuestModel tem um @SuperBuilder
                .nome(request.getNome())
                .email(request.getEmail())
                .conviteStatus(Map.of(request.getEventoId(), StatusConfirmacao.PENDENTE))
                .build();
        convidadoRepository.save(novoConvidado);

        return ResponseEntity.status(HttpStatus.CREATED) // 201 Created
                .body(ConvidadoResponse.builder()
                        .message("Convidado adicionado com sucesso.")
                        .success(true)
                        .guestId(novoConvidado.getId()) // Retorna o ID do novo convidado
                        .build());
    }

    // Método para listar todos os convidados
    public ResponseEntity<List<ConvidadoModel>> listarConvidados() {
        List<ConvidadoModel> guests = convidadoRepository.findAll();
        if (guests.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content se não houver convidados
        }
        return ResponseEntity.ok(guests); // 200 OK com a lista de convidados
    }

    // Método para deletar um convidado por e-mail
    public ResponseEntity<ConvidadoResponse> deletarConvidados(String email) {
        Optional<ConvidadoModel> existingGuest = convidadoRepository.findByEmail(email);

        if (existingGuest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404 Not Found
                    .body(ConvidadoResponse.builder()
                            .message("Convidado com email " + email + " não encontrado.")
                            .success(false)
                            .build());
        }

        convidadoRepository.delete(existingGuest.get());

        return ResponseEntity.ok(ConvidadoResponse.builder() // 200 OK
                .message("Guest deleted successfully.")
                .success(true)
                .build());
    }

    // Método para atualizar um convidado
    public ResponseEntity<ConvidadoResponse> atualizarConvidados(UpdateConvidadoRequest request) {
        Optional<ConvidadoModel> existingGuest = convidadoRepository.findByEmail(request.getEmail());

        if (existingGuest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404 Not Found
                    .body(ConvidadoResponse.builder()
                            .message("Convidado com email " + request.getEmail() + " não encontrado.")
                            .success(false)
                            .build());
        }

        // Atualiza os dados do convidado existente
        ConvidadoModel guestToUpdate = existingGuest.get();
        guestToUpdate.setNome(request.getNome()); // Supondo que UpdateGuestRequest tem getName()
        // TODO: Atualize outros campos relevantes do UpdateGuestRequest para o GuestModel
        // Ex: guestToUpdate.setAlgumCampoNovo(request.getAlgumCampoNovo());

        // Salva as alterações no banco de dados
        convidadoRepository.save(guestToUpdate);

        return ResponseEntity.ok(ConvidadoResponse.builder() // 200 OK
                .message("Convidado atualizado com sucesso.")
                .success(true)
                .guestId(guestToUpdate.getId()) // Retorna o ID do convidado atualizado
                .build());
    }

    // Método para buscar um convidado por e-mail
    public ResponseEntity<ConvidadoModel> buscarPorEmail(String email) {
        Optional<ConvidadoModel> guest = convidadoRepository.findByEmail(email);
        return guest.map(ResponseEntity::ok) // Se encontrado, retorna 200 OK com o convidado
                .orElseGet(() -> ResponseEntity.notFound().build()); // Se não encontrado, retorna 404 Not Found
    }
}