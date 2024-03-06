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
public final class ConsultaVersosPorPalavraResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5178622093720241294L;

    @ToString.Include
    private ConsultaVersosPorPalavraLivroResponse livro;

    @ToString.Include
    private Integer numeroCapitulo;

    @ToString.Include
    private Integer numeroVerso;

    @ToString.Include
    private String texto;

    @Getter
    @Builder
    @ToString(onlyExplicitlyIncluded = true)
    public static final class ConsultaVersosPorPalavraLivroResponse implements Serializable {

        @Serial
        private static final long serialVersionUID = 4168190144835378504L;

        @ToString.Include
        private AbreviacaoResponse abreviacao;

        @ToString.Include
        private String autor;

        @ToString.Include
        private Integer numeroCapitulos;

        @ToString.Include
        private String grupo;

        @ToString.Include
        private String nome;

        @ToString.Include
        private Testamento testamento;

    }
}
