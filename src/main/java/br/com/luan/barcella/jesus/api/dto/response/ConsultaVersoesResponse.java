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
public final class ConsultaVersoesResponse  implements Serializable {

    @Serial
    private static final long serialVersionUID = -8267578733432204549L;

    private final List<VersaoResponse> versoes;

    @Getter
    @Builder
    @ToString(onlyExplicitlyIncluded = true)
    public static final class VersaoResponse implements Serializable {

        @Serial
        private static final long serialVersionUID = 8245976812091867722L;

        @ToString.Include
        private final String codigoVersao;

        @ToString.Include
        private final Integer numeroVersos;

        @ToString.Include
        private final String nomeCompleto;

        @ToString.Exclude
        private final String descricao;
    }

}
