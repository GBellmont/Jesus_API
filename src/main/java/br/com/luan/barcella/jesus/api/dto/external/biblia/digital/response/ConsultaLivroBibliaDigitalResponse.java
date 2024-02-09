package br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.luan.barcella.jesus.api.domain.Testamento;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public final class ConsultaLivroBibliaDigitalResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -5196488324824191961L;

    @ToString.Include
    @JsonProperty("abbrev")
    private AbreviacaoBibliaDigitalResponse abreviacao;

    @ToString.Include
    @JsonProperty("author")
    private String autor;

    @ToString.Include
    @JsonProperty("chapters")
    private Integer numeroCapitulos;

    @ToString.Exclude
    @JsonProperty("comment")
    private String comentario;

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