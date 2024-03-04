package br.com.luan.barcella.jesus.api.mapper.livros;

import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.generateList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivrosBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivrosResponse;
import br.com.luan.barcella.jesus.api.fixture.Fixture;

public class ConsultaLivrosResponseMapperTest {

    private static final ConsultaLivrosResponseMapper CONSULTA_LIVROS_RESPONSE_MAPPER = new ConsultaLivrosResponseMapper();

    @Test
    public void deveRetornarNullQuandoListaEstiverNulla() {
        final List<ConsultaLivrosResponse> response = CONSULTA_LIVROS_RESPONSE_MAPPER.apply(null);

        assertNull(response);
    }

    @Test
    public void deveRetornarNullQuandoListaEstiverVazia() {
        final List<ConsultaLivrosResponse> response = CONSULTA_LIVROS_RESPONSE_MAPPER.apply(new ArrayList<>());

        assertNull(response);
    }

    @Test
    public void deveMapearConsultaLivrosResponseCorretamente() {
        final List<ConsultaLivrosBibliaDigitalResponse> livrosBibliaDigitalResponses = generateList(
            () -> make(new ConsultaLivrosBibliaDigitalResponse()), 1, 10);

        final List<ConsultaLivrosResponse> response = CONSULTA_LIVROS_RESPONSE_MAPPER.apply(livrosBibliaDigitalResponses);

        assertNotNull(response);
        for (int i = 0; i < response.size(); i++) {
            this.assertConsultaLivrosResponse(response.get(i), livrosBibliaDigitalResponses.get(i));
        }
    }

    @Test
    public void deveMapearConsultaLivrosResponseCorretamenteComAbreviacaoNull() {
        final List<ConsultaLivrosBibliaDigitalResponse> livrosBibliaDigitalResponses = generateList(() -> {
            final ConsultaLivrosBibliaDigitalResponse livro = make(new ConsultaLivrosBibliaDigitalResponse());
            livro.setAbreviacao(null);

            return livro;
        }, 1, 10);

        final List<ConsultaLivrosResponse> response = CONSULTA_LIVROS_RESPONSE_MAPPER.apply(livrosBibliaDigitalResponses);

        assertNotNull(response);
        for (int i = 0; i < response.size(); i++) {
            this.assertConsultaLivrosResponseComAbreviacaoNull(response.get(i), livrosBibliaDigitalResponses.get(i));
        }
    }

    private void assertConsultaLivrosResponse(final ConsultaLivrosResponse response, final ConsultaLivrosBibliaDigitalResponse expected) {
        assertNotNull(response);
        assertEquals(response.getAbreviacao().getPortugues(), expected.getAbreviacao().getPortugues());
        assertEquals(response.getAbreviacao().getIngles(), expected.getAbreviacao().getIngles());
        assertEquals(response.getAutor(), expected.getAutor());
        assertEquals(response.getNumeroCapitulos(), expected.getNumeroCapitulos());
        assertEquals(response.getGrupo(), expected.getGrupo());
        assertEquals(response.getTestamento(), expected.getTestamento());
        assertEquals(response.getNome(), expected.getNome());
    }

    private void assertConsultaLivrosResponseComAbreviacaoNull(final ConsultaLivrosResponse response, final ConsultaLivrosBibliaDigitalResponse expected) {
        assertNotNull(response);
        assertNull(response.getAbreviacao());
        assertEquals(response.getAutor(), expected.getAutor());
        assertEquals(response.getNumeroCapitulos(), expected.getNumeroCapitulos());
        assertEquals(response.getGrupo(), expected.getGrupo());
        assertEquals(response.getTestamento(), expected.getTestamento());
        assertEquals(response.getNome(), expected.getNome());
    }

}
