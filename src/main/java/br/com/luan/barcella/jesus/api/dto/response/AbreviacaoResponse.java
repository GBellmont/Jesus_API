package br.com.luan.barcella.jesus.api.dto.response;

import java.io.Serial;
import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(onlyExplicitlyIncluded = true)
public final class AbreviacaoResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5659350751119467365L;

    @ToString.Include
    private String portugues;

    @ToString.Include
    private String ingles;

}
