package br.com.luan.barcella.jesus.api.dto.response;

import java.io.Serial;
import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(onlyExplicitlyIncluded = true)
public final class LivroComplementarResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 4320139243966314333L;

    @ToString.Include
    private final AbreviacaoResponse abreviacao;

    @ToString.Include
    private final String nome;

    @ToString.Include
    private final String autor;

    @ToString.Include
    private final String grupo;

    @ToString.Include
    private final String versao;

}