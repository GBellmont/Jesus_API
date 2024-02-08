package br.com.luan.barcella.jesus.api.dto.response;

import java.io.Serial;
import java.io.Serializable;

import br.com.luan.barcella.jesus.api.domain.Testamento;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(onlyExplicitlyIncluded = true)
public final class ConsultaLivroResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 2667061750099381350L;

    @ToString.Include
    private AbreviacaoResponse abreviacao;

    @ToString.Include
    private String autor;

    @ToString.Include
    private Integer numeroCapitulos;

    @ToString.Exclude
    private String comentario;

    @ToString.Include
    private String grupo;

    @ToString.Include
    private String nome;

    @ToString.Include
    private Testamento testamento;

}
