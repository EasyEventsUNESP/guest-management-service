package com.easyevents.guest_management_service.domain.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConvidadoRequest {
    private String email;
    private String nome;
    // Adicione outros campos que podem ser atualizados
}