package br.com.luan.barcella.jesus.api.dto.response;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(onlyExplicitlyIncluded = true)
public final class PaginacaoResponse <T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 8328354782095126924L;

    private final Integer index;
    private final Integer numeroItens;
    private final List<T> itens;
    private final boolean primeiraPagina;
    private final boolean ultimaPagina;

}
