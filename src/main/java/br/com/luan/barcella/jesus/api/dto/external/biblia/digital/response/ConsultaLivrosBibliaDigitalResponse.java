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
public final class ConsultaLivrosBibliaDigitalResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -3202482368535076062L;

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
    @JsonProperty("testament")
    private Testamento testamento;

    @ToString.Include
    @JsonProperty("name")
    private String nome;

}
