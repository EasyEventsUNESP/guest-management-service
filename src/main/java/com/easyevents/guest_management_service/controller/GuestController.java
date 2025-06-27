package com.easyevents.guest_management_service.controller;

import com.easyevents.guest_management_service.domain.dto.request.AdicionarConvidadoRequest;
import com.easyevents.guest_management_service.domain.dto.request.UpdateConvidadoRequest;
import com.easyevents.guest_management_service.domain.dto.response.ConvidadoResponse;
import com.easyevents.guest_management_service.domain.model.ConvidadoModel;
import com.easyevents.guest_management_service.service.GuestService; // Supondo que você terá um GuestService
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestController {

    @Autowired
    private final GuestService guestService; // Injeção do GuestService

    @GetMapping
    public String endpointTest() {
        return "ERALDO VS MARIO!";
    }

    // Endpoint para adicionar um novo convidado
    @PostMapping("/adicionar")
    public ResponseEntity<ConvidadoResponse> add(@RequestBody AdicionarConvidadoRequest addGuestRequest) {
        return guestService.adicionarConvidado(addGuestRequest);
    }

    // Endpoint para listar todos os convidados
    @GetMapping("/listar")
    public ResponseEntity<List<ConvidadoModel>> list() {
        return guestService.listarConvidados();
    }

    // Endpoint para deletar um convidado por e-mail
    @DeleteMapping("/deletar/{email}")
    public ResponseEntity<ConvidadoResponse> delete(@PathVariable String email) {
        return guestService.deletarConvidado(email);
    }

    // Endpoint para atualizar um convidado
    @PutMapping("/atualizar")
    public ResponseEntity<ConvidadoResponse> update(@RequestBody UpdateConvidadoRequest updateGuestRequest) {
        return guestService.atualizarConvidado(updateGuestRequest);
    }

    // Endpoint para buscar um convidado por e-mail
    @GetMapping("/buscar/{email}")
    public ResponseEntity<ConvidadoModel> searchByEmail(@PathVariable String email) {
        return guestService.buscarPorEmail(email);
    }
}