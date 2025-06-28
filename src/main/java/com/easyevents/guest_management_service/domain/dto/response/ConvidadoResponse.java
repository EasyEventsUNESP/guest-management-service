package com.easyevents.guest_management_service.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvidadoResponse {
    private String message;
    private boolean success;
    private String guestId;
}
