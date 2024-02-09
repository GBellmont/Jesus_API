package br.com.luan.barcella.jesus.api.mapper.livros;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.AbreviacaoBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivrosBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.AbreviacaoResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivrosResponse;

public class ConsultaLivrosResponseMapper implements Function<List<ConsultaLivrosBibliaDigitalResponse>, List<ConsultaLivrosResponse>> {

    @Override
    public List<ConsultaLivrosResponse> apply(
        final List<ConsultaLivrosBibliaDigitalResponse> consultaLivrosBibliaDigitalResponses) {

        if (isNull(consultaLivrosBibliaDigitalResponses) || consultaLivrosBibliaDigitalResponses.isEmpty()) {
            return null;
        }

        return consultaLivrosBibliaDigitalResponses.stream()
            .map(livro -> ConsultaLivrosResponse.builder()
                .abreviacao(buildAbreviacao(livro.getAbreviacao()))
                .autor(livro.getAutor())
                .numeroCapitulos(livro.getNumeroCapitulos())
                .grupo(livro.getGrupo())
                .testamento(livro.getTestamento())
                .nome(livro.getNome())
                .build())
            .collect(Collectors.toList());
    }

    private AbreviacaoResponse buildAbreviacao(final AbreviacaoBibliaDigitalResponse abreviacao) {
        return AbreviacaoResponse.builder()
            .portugues(abreviacao.getPortugues())
            .ingles(abreviacao.getIngles())
            .build();
    }
}
