package br.com.luan.barcella.jesus.api.dto.request;

import java.io.Serial;
import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@ToString(onlyExplicitlyIncluded = true)
public final class ConsultaVersosPorPalavraRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3654916533330457451L;

    @ToString.Include
    private String versao;

    @ToString.Include
    private String palavra;

    @ToString.Include
    private Integer index;

    @ToString.Include
    private Integer numeroItens;

}
