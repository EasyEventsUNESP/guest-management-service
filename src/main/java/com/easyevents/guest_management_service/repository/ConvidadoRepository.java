package com.easyevents.guest_management_service.repository;

import com.easyevents.guest_management_service.domain.model.ConvidadoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConvidadoRepository extends MongoRepository<ConvidadoModel, String> {

    Optional<ConvidadoModel> findByEmail(String email);
}
