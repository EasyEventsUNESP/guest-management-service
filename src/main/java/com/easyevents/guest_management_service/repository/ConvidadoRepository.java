package com.easyevents.guest_management_service.repository;

import com.easyevents.guest_management_service.domain.model.ConvidadoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ConvidadoRepository extends MongoRepository<ConvidadoModel, String> {

    // Método para encontrar um convidado pela combinação de email e eventoId
    Optional<ConvidadoModel> findByEmailAndEventoId(String email, String eventoId);

    // Método para listar todos os convidados para um evento específico
    List<ConvidadoModel> findByEventoId(String eventoId);

}
