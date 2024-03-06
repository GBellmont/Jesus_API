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
public final class ConsultaVersoesBibliaDigitalResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 7221177500062999906L;

    @ToString.Include
    @JsonProperty("version")
    private String codigoVersao;

    @ToString.Include
    @JsonProperty("verses")
    private Integer numeroVersos;

}
