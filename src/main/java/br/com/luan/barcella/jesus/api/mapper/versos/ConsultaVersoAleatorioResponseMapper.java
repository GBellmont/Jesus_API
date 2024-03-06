package br.com.luan.barcella.jesus.api.mapper.versos;

import static java.util.Objects.isNull;

import java.util.function.Function;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.AbreviacaoBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoAleatorioBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.LivroComplementarBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.AbreviacaoResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoAleatorioResponse;
import br.com.luan.barcella.jesus.api.dto.response.LivroComplementarResponse;

public class ConsultaVersoAleatorioResponseMapper
    implements Function<ConsultaVersoAleatorioBibliaDigitalResponse, ConsultaVersoAleatorioResponse> {

    @Override
    public ConsultaVersoAleatorioResponse apply(final ConsultaVersoAleatorioBibliaDigitalResponse response) {

        if (isNull(response)) {
            return null;
        }

        return ConsultaVersoAleatorioResponse.builder()
            .livro(buildLivro(response.getLivro()))
            .numeroCapitulo(response.getNumeroCapitulo())
            .numeroVerso(response.getNumeroVerso())
            .texto(response.getTexto())
            .build();
    }

    private LivroComplementarResponse buildLivro(final LivroComplementarBibliaDigitalResponse livroResponse) {
        if (isNull(livroResponse)) {
            return null;
        }

        return LivroComplementarResponse.builder()
            .abreviacao(buildAbreviacao(livroResponse.getAbreviacao()))
            .nome(livroResponse.getNome())
            .autor(livroResponse.getAutor())
            .grupo(livroResponse.getGrupo())
            .versao(livroResponse.getVersao())
            .build();
    }

    private AbreviacaoResponse buildAbreviacao(final AbreviacaoBibliaDigitalResponse abreviacaoResponse) {
        if (isNull(abreviacaoResponse)) {
            return null;
        }

        return AbreviacaoResponse.builder()
            .portugues(abreviacaoResponse.getPortugues())
            .ingles(abreviacaoResponse.getIngles())
            .build();
    }
}
