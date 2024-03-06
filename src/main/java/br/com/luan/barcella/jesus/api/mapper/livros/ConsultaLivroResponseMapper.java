package br.com.luan.barcella.jesus.api.mapper.livros;

import static java.util.Objects.isNull;

import java.util.function.Function;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.AbreviacaoBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivroBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.AbreviacaoResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivroResponse;

public class ConsultaLivroResponseMapper implements Function<ConsultaLivroBibliaDigitalResponse, ConsultaLivroResponse> {

    @Override
    public ConsultaLivroResponse apply(final ConsultaLivroBibliaDigitalResponse consultaLivroBibliaDigitalResponse) {
        if (isNull(consultaLivroBibliaDigitalResponse)) {
            return null;
        }

        return ConsultaLivroResponse.builder()
            .abreviacao(buildAbreviacao(consultaLivroBibliaDigitalResponse.getAbreviacao()))
            .autor(consultaLivroBibliaDigitalResponse.getAutor())
            .numeroCapitulos(consultaLivroBibliaDigitalResponse.getNumeroCapitulos())
            .comentario(consultaLivroBibliaDigitalResponse.getComentario())
            .grupo(consultaLivroBibliaDigitalResponse.getGrupo())
            .nome(consultaLivroBibliaDigitalResponse.getNome())
            .testamento(consultaLivroBibliaDigitalResponse.getTestamento())
            .build();
    }

    private AbreviacaoResponse buildAbreviacao(final AbreviacaoBibliaDigitalResponse abreviacao) {
        if (isNull(abreviacao)) {
            return null;
        }

        return AbreviacaoResponse.builder()
            .portugues(abreviacao.getPortugues())
            .ingles(abreviacao.getIngles())
            .build();
    }
}
