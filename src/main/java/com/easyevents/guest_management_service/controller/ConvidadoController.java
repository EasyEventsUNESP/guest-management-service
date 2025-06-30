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
public class ConvidadoController {

    @Autowired
    private ConvidadoService convidadoService;

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

    // Endpoint para deletar um convidado por e-mail de um evento específico
    @DeleteMapping("/deletar/{eventoId}{email}")
    public ResponseEntity<ConvidadoResponse> delete(@PathVariable String eventoId, @PathVariable String email) {
        return convidadoService.deletarConvidado(eventoId, email);
    }

    // Endpoint para atualizar um convidado de um evento específico
    @PutMapping("/atualizar")
    public ResponseEntity<ConvidadoResponse> update(@RequestBody AtualizarConvidadoRequest atualizarConvidadoRequest) {
        return convidadoService.atualizarConvidado(atualizarConvidadoRequest);
    }

    // Endpoint para buscar um convidado por e-mail de um evento específico
    @GetMapping("/buscar/{eventoId}/{email}")
    public ResponseEntity<ConvidadoModel> searchByEmail(@PathVariable String eventoId, @PathVariable String email) {
        return convidadoService.buscarPorEmail(eventoId, email);
    }

    // Endpoint para confirmar presença de um convidado em um evento específico
    @PostMapping("/confirmar/{eventoId}/{email}")
    public ResponseEntity<ConvidadoResponse> confirmarPresenca(@PathVariable String eventoId, @PathVariable String email) {
        return convidadoService.confirmarPresenca(eventoId, email);
    }

    // Endpoint para negar presença de um convidado em um evento específico
    @PostMapping("/negar/{eventoId}/{email}")
    public ResponseEntity<ConvidadoResponse> negarPresenca(@PathVariable String eventoId, @PathVariable String email) {
        return convidadoService.negarPresenca(eventoId, email);
    }

}