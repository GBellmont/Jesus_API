package br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class ConsultaCapituloBibliaDigitalResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 4809306290016201119L;

    @ToString.Include
    @JsonProperty("book")
    private LivroComplementarBibliaDigitalResponse livro;

    @ToString.Include
    @JsonProperty("chapter")
    private ConsultaCapituloCapituloBibliaDigitalResponse capitulo;

    @ToString.Include
    @JsonProperty("verses")
    private List<ConsultaCapituloVersoBibliaDigitalResponse> versos;


    @Data
    @NoArgsConstructor
    @ToString(onlyExplicitlyIncluded = true)
    public static final class ConsultaCapituloCapituloBibliaDigitalResponse implements Serializable {

        @Serial
        private static final long serialVersionUID = 3822881773235001683L;

        @ToString.Include
        @JsonProperty("number")
        private Integer numero;

        @ToString.Include
        @JsonProperty("verses")
        private Integer quantidadeVersos;

    }

    @Data
    @NoArgsConstructor
    @ToString(onlyExplicitlyIncluded = true)
    public static final class ConsultaCapituloVersoBibliaDigitalResponse implements Serializable {

        @Serial
        private static final long serialVersionUID = -868078696443561863L;

        @ToString.Include
        @JsonProperty("number")
        private Integer numero;

        @ToString.Include
        @JsonProperty("text")
        private String texto;

    }
}
