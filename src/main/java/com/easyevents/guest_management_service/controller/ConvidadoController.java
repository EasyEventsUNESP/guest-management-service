package com.easyevents.guest_management_service.controller;

import com.easyevents.guest_management_service.domain.dto.request.AdicionarConvidadoRequest;
import com.easyevents.guest_management_service.domain.dto.request.AtualizarConvidadoRequest;
import com.easyevents.guest_management_service.domain.dto.response.ConvidadoResponse;
import com.easyevents.guest_management_service.domain.model.ConvidadoModel;
import com.easyevents.guest_management_service.service.ConvidadoService; // Supondo que você terá um GuestService
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
public class ConvidadoController {

    @Autowired
    private final ConvidadoService convidadoService; // Injeção do GuestService

    @GetMapping
    public String endpointTest() {
        return "ERALDO VS MARIO!";
    }

    // Endpoint para adicionar um novo convidado
    @PostMapping("/adicionar")
    public ResponseEntity<ConvidadoResponse> add(@RequestBody AdicionarConvidadoRequest adicionarConvidadoRequest) {
        return convidadoService.adicionarConvidado(adicionarConvidadoRequest);
    }

    // Endpoint para listar todos os convidados
    @GetMapping("/listar/{eventoId}")
    public ResponseEntity<List<ConvidadoModel>> list(@PathVariable String eventoId) {
        return convidadoService.listarConvidados(eventoId);
    }

    // Endpoint para deletar um convidado por e-mail
    @DeleteMapping("/deletar/{eventoId}{email}")
    public ResponseEntity<ConvidadoResponse> delete(@PathVariable String eventoId, @PathVariable String email) {
        return convidadoService.deletarConvidado(eventoId, email);
    }

    // Endpoint para atualizar um convidado
    @PutMapping("/atualizar")
    public ResponseEntity<ConvidadoResponse> update(@RequestBody AtualizarConvidadoRequest atualizarConvidadoRequest) {
        return convidadoService.atualizarConvidado(atualizarConvidadoRequest);
    }

    // Endpoint para buscar um convidado por e-mail
    @GetMapping("/buscar/{eventoId}/{email}")
    public ResponseEntity<ConvidadoModel> searchByEmail(@PathVariable String eventoId, @PathVariable String email) {
        return convidadoService.buscarPorEmail(eventoId, email);
    }
}