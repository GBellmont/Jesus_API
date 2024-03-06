package br.com.luan.barcella.jesus.api.dto.response;

import java.io.Serial;
import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(onlyExplicitlyIncluded = true)
public final class VersaoResponse  implements Serializable {

    @Serial
    private static final long serialVersionUID = -8267578733432204549L;

    @ToString.Include
    private final String codigoVersao;

    @ToString.Include
    private final Integer numeroVersos;

    @ToString.Include
    private final String nomeCompleto;

    @ToString.Exclude
    private final String descricao;

}
