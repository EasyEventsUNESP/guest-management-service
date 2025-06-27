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
public class AdicionarConvidadoRequest {
    private String nome;
    private String email;
    private int eventoId;
}
