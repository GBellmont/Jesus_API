package br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public final class AbreviacaoBibliaDigitalResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5659350751119467365L;

    @ToString.Include
    @JsonProperty("pt")
    private String portugues;

    @ToString.Include
    @JsonProperty("en")
    private String ingles;

}
