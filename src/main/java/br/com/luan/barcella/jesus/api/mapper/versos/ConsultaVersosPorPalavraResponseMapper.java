package br.com.luan.barcella.jesus.api.mapper.versos;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.AbreviacaoBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersosPorPalavraBibliaDigitalResponse.ConsultaVersosPorPalavraLivroBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersosPorPalavraBibliaDigitalResponse.ConsultaVersosPorPalavraVersoBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.AbreviacaoResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersosPorPalavraResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersosPorPalavraResponse.ConsultaVersosPorPalavraLivroResponse;

public class ConsultaVersosPorPalavraResponseMapper
    implements Function<List<ConsultaVersosPorPalavraVersoBibliaDigitalResponse>, List<ConsultaVersosPorPalavraResponse>> {

    @Override
    public List<ConsultaVersosPorPalavraResponse> apply(
        final List<ConsultaVersosPorPalavraVersoBibliaDigitalResponse> versosResponse) {

        if (isNull(versosResponse) || isEmpty(versosResponse)) {
            return null;
        }

        return versosResponse.stream()
            .map((versoResponse -> ConsultaVersosPorPalavraResponse.builder()
                .livro(buildLivro(versoResponse.getLivro()))
                .numeroCapitulo(versoResponse.getNumeroCapitulo())
                .numeroVerso(versoResponse.getNumeroVerso())
                .texto(versoResponse.getTexto())
                .build()))
            .collect(Collectors.toList());
    }

    private ConsultaVersosPorPalavraLivroResponse buildLivro(final ConsultaVersosPorPalavraLivroBibliaDigitalResponse livroResponse) {
        if (isNull(livroResponse)) {
            return null;
        }

        return ConsultaVersosPorPalavraLivroResponse.builder()
            .abreviacao(buildAbreviacao(livroResponse.getAbreviacao()))
            .autor(livroResponse.getAutor())
            .numeroCapitulos(livroResponse.getNumeroCapitulos())
            .grupo(livroResponse.getGrupo())
            .nome(livroResponse.getNome())
            .testamento(livroResponse.getTestamento())
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
