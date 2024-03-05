package br.com.luan.barcella.jesus.api.dto.external.biblia.digital.request;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public final class ConsultaVersosPorPalavraBibliaDigitalRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5595110917689845568L;

    @ToString.Include
    @JsonProperty("version")
    private String versao;

    @ToString.Include
    @JsonProperty("search")
    private String palavra;

}
