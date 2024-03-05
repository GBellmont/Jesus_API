package br.com.luan.barcella.jesus.api.dto.response;

import java.io.Serial;
import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@Builder
@ToString(onlyExplicitlyIncluded = true)
public final class ConsultaVersoAleatorioResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 6208406840450436485L;

    @ToString.Include
    private LivroComplementarResponse livro;

    @ToString.Include
    private Integer numeroCapitulo;

    @ToString.Include
    private Integer numeroVerso;

    @ToString.Include
    private String texto;
}
