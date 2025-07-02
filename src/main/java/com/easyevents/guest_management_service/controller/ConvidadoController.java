package com.easyevents.guest_management_service.controller;

import com.easyevents.guest_management_service.domain.dto.request.AdicionarConvidadoRequest;
import com.easyevents.guest_management_service.domain.dto.request.AtualizarConvidadoRequest;
import com.easyevents.guest_management_service.domain.dto.response.ConvidadoResponse;
import com.easyevents.guest_management_service.domain.model.ConvidadoModel;
import com.easyevents.guest_management_service.service.ConvidadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    @DeleteMapping("/deletar/{eventoId}/{email}")
    public ResponseEntity<ConvidadoResponse> delete(@PathVariable String eventoId, @PathVariable String email) {
        return convidadoService.deletarConvidado(eventoId, email);
    }

    // Endpoint para atualizar um convidado de um evento específico
    @PutMapping("/atualizar")
    public ResponseEntity<ConvidadoResponse> update(@RequestBody AtualizarConvidadoRequest atualizarConvidadoRequest) {
        return convidadoService.atualizarConvidado(atualizarConvidadoRequest);
    }

    // Endpoint para buscar um convidado por e-mail de um evento específico
    @GetMapping("/buscar-convidado/{eventoId}/{email}")
    public ResponseEntity<ConvidadoModel> searchByEmail(@PathVariable String eventoId, @PathVariable String email) {
        return convidadoService.buscarPorEmail(eventoId, email);
    }

    // Endpoint para confirmar presença de um convidado em um evento específico
    @GetMapping("/confirmar/{eventoId}/{email}")
    public ResponseEntity<String> confirmarPresenca(@PathVariable String eventoId, @PathVariable String email) {
        convidadoService.confirmarPresenca(eventoId, email);
        String htmlResponse = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Confirmação de Presença</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        margin: 0;
                        background-color: #f5f5f5;
                    }
                    .container {
                        text-align: center;
                        padding: 20px;
                        background-color: white;
                        border-radius: 8px;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                    }
                    .success-icon {
                        color: green;
                        font-size: 48px;
                        margin-bottom: 20px;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="success-icon">✓</div>
                    <h1>Obrigado!</h1>
                    <p>Sua resposta foi registrada com sucesso.</p>
                </div>
            </body>
            </html>
            """;

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlResponse);

    }

    // Endpoint para negar presença de um convidado em um evento específico
    @GetMapping("/negar/{eventoId}/{email}")
    public ResponseEntity<String> negarPresenca(@PathVariable String eventoId, @PathVariable String email) {
        convidadoService.negarPresenca(eventoId, email);

        String htmlResponse = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Status Participacao</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        height: 100vh;
                        margin: 0;
                        background-color: #f5f5f5;
                    }
                    .container {
                        text-align: center;
                        padding: 20px;
                        background-color: white;
                        border-radius: 8px;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                    }
                    .success-icon {
                        color: green;
                        font-size: 48px;
                        margin-bottom: 20px;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="success-icon">✓</div>
                    <h1>Obrigado!</h1>
                    <p>Sua resposta foi registrada com sucesso.</p>
                </div>
            </body>
            </html>
            """;

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlResponse);

    }

}