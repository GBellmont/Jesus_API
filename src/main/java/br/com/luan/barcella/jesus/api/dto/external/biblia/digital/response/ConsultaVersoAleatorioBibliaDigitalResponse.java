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
public final class ConsultaVersoAleatorioBibliaDigitalResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -8476430598370897410L;

    @ToString.Include
    @JsonProperty("book")
    private LivroComplementarBibliaDigitalResponse livro;

    @ToString.Include
    @JsonProperty("chapter")
    private Integer numeroCapitulo;

    @ToString.Include
    @JsonProperty("number")
    private Integer numeroVerso;

    @ToString.Include
    @JsonProperty("text")
    private String texto;
}
