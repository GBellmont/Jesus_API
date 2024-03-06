package br.com.luan.barcella.jesus.api.mapper.versos;

import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.generateList;
import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersosPorPalavraBibliaDigitalResponse.ConsultaVersosPorPalavraVersoBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersosPorPalavraResponse;

public class ConsultaVersosPorPalavraResponseMapperTest {

    private static final ConsultaVersosPorPalavraResponseMapper CONSULTA_VERSOS_POR_PALAVRA_RESPONSE_MAPPER = new ConsultaVersosPorPalavraResponseMapper();

    @Test
    public void deveRetonarNullQuandoVersosResponseForNull() {
        final List<ConsultaVersosPorPalavraResponse> responses = CONSULTA_VERSOS_POR_PALAVRA_RESPONSE_MAPPER
            .apply(null);

        assertNull(responses);
    }

    @Test
    public void deveRetonarNullQuandoVersosResponseForUmaListaVazia() {
        final List<ConsultaVersosPorPalavraResponse> responses = CONSULTA_VERSOS_POR_PALAVRA_RESPONSE_MAPPER
            .apply(new ArrayList<>());

        assertNull(responses);
    }

    @Test
    public void deveMapearListaConsultaVersosPorPalavraResponseCorretamente() {
        final List<ConsultaVersosPorPalavraVersoBibliaDigitalResponse> bibliaDigitalResponses = generateList(
            () -> make(new ConsultaVersosPorPalavraVersoBibliaDigitalResponse()), 1, 10);

        final List<ConsultaVersosPorPalavraResponse> responses = CONSULTA_VERSOS_POR_PALAVRA_RESPONSE_MAPPER
            .apply(bibliaDigitalResponses);

        assertNotNull(responses);
        assertConsultaVersosPorPalavraResponse(bibliaDigitalResponses, responses);
    }

    @Test
    public void deveMapearListaConsultaVersosPorPalavraResponseCorretamenteComLivroNull() {
        final List<ConsultaVersosPorPalavraVersoBibliaDigitalResponse> bibliaDigitalResponses = generateList(
            () -> {
                final ConsultaVersosPorPalavraVersoBibliaDigitalResponse response = make(new ConsultaVersosPorPalavraVersoBibliaDigitalResponse());
                response.setLivro(null);

                return response;
            }, 1, 10);

        final List<ConsultaVersosPorPalavraResponse> responses = CONSULTA_VERSOS_POR_PALAVRA_RESPONSE_MAPPER
            .apply(bibliaDigitalResponses);

        assertNotNull(responses);
        assertTrue(responses.stream().allMatch((response) -> isNull(response.getLivro())));
    }

    @Test
    public void deveMapearListaConsultaVersosPorPalavraResponseCorretamenteComAbreviacaoNull() {
        final List<ConsultaVersosPorPalavraVersoBibliaDigitalResponse> bibliaDigitalResponses = generateList(
            () -> {
                final ConsultaVersosPorPalavraVersoBibliaDigitalResponse response = make(new ConsultaVersosPorPalavraVersoBibliaDigitalResponse());
                response.getLivro().setAbreviacao(null);

                return response;
            }, 1, 10);

        final List<ConsultaVersosPorPalavraResponse> responses = CONSULTA_VERSOS_POR_PALAVRA_RESPONSE_MAPPER
            .apply(bibliaDigitalResponses);

        assertNotNull(responses);
        assertTrue(responses.stream().allMatch((response) -> isNull(response.getLivro().getAbreviacao())));
    }

    private void assertConsultaVersosPorPalavraResponse(
        final List<ConsultaVersosPorPalavraVersoBibliaDigitalResponse> expecteds,
        final List<ConsultaVersosPorPalavraResponse> responses) {

        assertNotNull(responses);
        assertFalse(responses.isEmpty());

        for (int i = 0; i < expecteds.size(); i++) {
            assertNotNull(responses.get(i));
            assertNotNull(responses.get(i).getLivro());
            assertNotNull(responses.get(i).getLivro().getAbreviacao());
            assertEquals(expecteds.get(i).getLivro().getAbreviacao().getPortugues(), responses.get(i).getLivro().getAbreviacao().getPortugues());
            assertEquals(expecteds.get(i).getLivro().getAbreviacao().getIngles(), responses.get(i).getLivro().getAbreviacao().getIngles());
            assertEquals(expecteds.get(i).getLivro().getAutor(), responses.get(i).getLivro().getAutor());
            assertEquals(expecteds.get(i).getLivro().getNumeroCapitulos(), responses.get(i).getLivro().getNumeroCapitulos());
            assertEquals(expecteds.get(i).getLivro().getGrupo(), responses.get(i).getLivro().getGrupo());
            assertEquals(expecteds.get(i).getLivro().getNome(), responses.get(i).getLivro().getNome());
            assertEquals(expecteds.get(i).getLivro().getTestamento(), responses.get(i).getLivro().getTestamento());
            assertEquals(expecteds.get(i).getNumeroCapitulo(), responses.get(i).getNumeroCapitulo());
            assertEquals(expecteds.get(i).getNumeroVerso(), responses.get(i).getNumeroVerso());
            assertEquals(expecteds.get(i).getTexto(), responses.get(i).getTexto());
        }
    }
}
