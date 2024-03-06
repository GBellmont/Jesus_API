package br.com.luan.barcella.jesus.api.mapper.capitulos;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.AbreviacaoBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaCapituloBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaCapituloBibliaDigitalResponse.ConsultaCapituloCapituloBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaCapituloBibliaDigitalResponse.ConsultaCapituloVersoBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.LivroComplementarBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.AbreviacaoResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaCapituloResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaCapituloResponse.ConsultaCapituloCapituloResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaCapituloResponse.ConsultaCapituloVersoResponse;
import br.com.luan.barcella.jesus.api.dto.response.LivroComplementarResponse;

public class ConsultaCapituloResponseMapper implements Function<ConsultaCapituloBibliaDigitalResponse, ConsultaCapituloResponse> {

    @Override
    public ConsultaCapituloResponse apply(final ConsultaCapituloBibliaDigitalResponse capituloResponse) {

        if (isNull(capituloResponse)) {
            return null;
        }

        return ConsultaCapituloResponse.builder()
            .livro(buildLivro(capituloResponse.getLivro()))
            .capitulo(buildCapitulo(capituloResponse.getCapitulo()))
            .versos(buildVersos(capituloResponse.getVersos()))
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

    private ConsultaCapituloCapituloResponse buildCapitulo(final ConsultaCapituloCapituloBibliaDigitalResponse capituloResponse) {
        if (isNull(capituloResponse)) {
            return null;
        }

        return ConsultaCapituloCapituloResponse.builder()
            .numero(capituloResponse.getNumero())
            .quantidadeVersos(capituloResponse.getQuantidadeVersos())
            .build();
    }

    private List<ConsultaCapituloVersoResponse> buildVersos(final List<ConsultaCapituloVersoBibliaDigitalResponse> versosResponse) {
        if (isEmpty(versosResponse)) {
            return new ArrayList<>();
        }

        return versosResponse.stream()
            .map((versoResponse) -> ConsultaCapituloVersoResponse.builder()
                .numero(versoResponse.getNumero())
                .texto(versoResponse.getTexto())
                .build())
            .collect(Collectors.toList());
    }


}
