package com.easyevents.guest_management_service.domain.model;

import com.easyevents.guest_management_service.domain.enumerator.StatusConfirmacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "convidado")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ConvidadoModel extends PessoaModel {

    private String eventoId;
    private StatusConfirmacao statusConfirmacao;

}
