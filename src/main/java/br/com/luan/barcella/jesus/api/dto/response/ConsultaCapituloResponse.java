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
public final class ConsultaCapituloResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -639299168936393755L;

    @ToString.Include
    private final LivroComplementarResponse livro;

    @ToString.Include
    private final ConsultaCapituloCapituloResponse capitulo;

    @ToString.Include
    private final List<ConsultaCapituloVersoResponse> versos;


    @Getter
    @Builder
    @ToString(onlyExplicitlyIncluded = true)
    public static final class ConsultaCapituloCapituloResponse implements Serializable {

        @Serial
        private static final long serialVersionUID = 7623511752340902068L;

        @ToString.Include
        private final Integer numero;

        @ToString.Include
        private final Integer quantidadeVersos;

    }

    @Getter
    @Builder
    @ToString(onlyExplicitlyIncluded = true)
    public static final class ConsultaCapituloVersoResponse implements Serializable {

        @Serial
        private static final long serialVersionUID = -4627701000127299956L;

        @ToString.Include
        private final Integer numero;

        @ToString.Include
        private final String texto;

    }
}
