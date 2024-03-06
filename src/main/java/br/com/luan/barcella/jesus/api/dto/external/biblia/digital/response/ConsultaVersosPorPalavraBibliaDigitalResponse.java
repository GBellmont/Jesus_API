package br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.luan.barcella.jesus.api.domain.Testamento;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public final class ConsultaVersosPorPalavraBibliaDigitalResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -6984642527246994535L;

    @ToString.Include
    @JsonProperty("occurrence")
    private String ocorrencias;

    @ToString.Include
    @JsonProperty("version")
    private String versao;

    @ToString.Include
    @JsonProperty("verses")
    private List<ConsultaVersosPorPalavraVersoBibliaDigitalResponse> versos;

    @Data
    @NoArgsConstructor
    @ToString(onlyExplicitlyIncluded = true)
    public static final class ConsultaVersosPorPalavraVersoBibliaDigitalResponse implements Serializable {

        @Serial
        private static final long serialVersionUID = 1638959664682404780L;

        @ToString.Include
        @JsonProperty("book")
        private ConsultaVersosPorPalavraLivroBibliaDigitalResponse livro;

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

    @Data
    @NoArgsConstructor
    @ToString(onlyExplicitlyIncluded = true)
    public static final class ConsultaVersosPorPalavraLivroBibliaDigitalResponse implements Serializable {

        @Serial
        private static final long serialVersionUID = 4168190144835378504L;

        @ToString.Include
        @JsonProperty("abbrev")
        private AbreviacaoBibliaDigitalResponse abreviacao;

        @ToString.Include
        @JsonProperty("author")
        private String autor;

        @ToString.Include
        @JsonProperty("chapters")
        private Integer numeroCapitulos;

        @ToString.Include
        @JsonProperty("group")
        private String grupo;

        @ToString.Include
        @JsonProperty("name")
        private String nome;

        @ToString.Include
        @JsonProperty("testament")
        private Testamento testamento;

    }
}
