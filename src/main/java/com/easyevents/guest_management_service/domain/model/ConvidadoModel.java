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

    // Agora, cada ConvidadoModel representa a associação de uma pessoa a um único evento.
    // O ID do evento se torna uma parte crucial da identidade deste 'convidado'.
    private String eventoId;
    private StatusConfirmacao statusConfirmacao;

}
