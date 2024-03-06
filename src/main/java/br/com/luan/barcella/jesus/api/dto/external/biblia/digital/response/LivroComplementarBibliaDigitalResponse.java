package br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.luan.barcella.jesus.api.dto.response.AbreviacaoResponse;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public final class LivroComplementarBibliaDigitalResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 4320139243966314333L;

    @ToString.Include
    @JsonProperty("abbrev")
    private AbreviacaoBibliaDigitalResponse abreviacao;

    @ToString.Include
    @JsonProperty("name")
    private String nome;

    @ToString.Include
    @JsonProperty("author")
    private String autor;

    @ToString.Include
    @JsonProperty("group")
    private String grupo;

    @ToString.Include
    @JsonProperty("version")
    private String versao;

}