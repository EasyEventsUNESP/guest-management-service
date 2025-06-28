package com.easyevents.guest_management_service.domain.dto.request;

import com.easyevents.guest_management_service.domain.enumerator.StatusConfirmacao;
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
public class AtualizarConvidadoRequest {
    private String email;
    private String nome;
    private String eventoId;
    private StatusConfirmacao statusConfirmacao;
}