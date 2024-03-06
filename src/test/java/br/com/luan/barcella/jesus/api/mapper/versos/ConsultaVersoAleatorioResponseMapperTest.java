package br.com.luan.barcella.jesus.api.mapper.versos;

import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoAleatorioBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoAleatorioResponse;

public class ConsultaVersoAleatorioResponseMapperTest {

    private static final ConsultaVersoAleatorioResponseMapper CONSULTA_VERSO_ALEATORIO_RESPONSE_MAPPER = new ConsultaVersoAleatorioResponseMapper();

    @Test
    public void deveRetonarNullQuandoResponseForNull() {
        final ConsultaVersoAleatorioResponse response = CONSULTA_VERSO_ALEATORIO_RESPONSE_MAPPER.apply(null);

        assertNull(response);
    }

    @Test
    public void deveMapearConsultaVersoAleatorioResponseCorretamente() {
        final ConsultaVersoAleatorioBibliaDigitalResponse expected = make(new ConsultaVersoAleatorioBibliaDigitalResponse());

        final ConsultaVersoAleatorioResponse response = CONSULTA_VERSO_ALEATORIO_RESPONSE_MAPPER.apply(expected);

        assertNotNull(response);

        assertNotNull(response.getLivro());
        assertNotNull(response.getLivro().getAbreviacao());
        assertEquals(expected.getLivro().getAbreviacao().getPortugues(), response.getLivro().getAbreviacao().getPortugues());
        assertEquals(expected.getLivro().getAbreviacao().getIngles(), response.getLivro().getAbreviacao().getIngles());
        assertEquals(expected.getLivro().getNome(), response.getLivro().getNome());
        assertEquals(expected.getLivro().getAutor(), response.getLivro().getAutor());
        assertEquals(expected.getLivro().getGrupo(), response.getLivro().getGrupo());
        assertEquals(expected.getLivro().getVersao(), response.getLivro().getVersao());

        assertEquals(expected.getNumeroCapitulo(), response.getNumeroCapitulo());
        assertEquals(expected.getNumeroVerso(), response.getNumeroVerso());
        assertEquals(expected.getTexto(), response.getTexto());
    }

    @Test
    public void deveMapearConsultaVersoAleatorioResponseCorretamenteComLivroNull() {
        final ConsultaVersoAleatorioBibliaDigitalResponse expected = make(new ConsultaVersoAleatorioBibliaDigitalResponse());
        expected.setLivro(null);

        final ConsultaVersoAleatorioResponse response = CONSULTA_VERSO_ALEATORIO_RESPONSE_MAPPER.apply(expected);

        assertNotNull(response);

        assertNull(response.getLivro());

        assertEquals(expected.getNumeroCapitulo(), response.getNumeroCapitulo());
        assertEquals(expected.getNumeroVerso(), response.getNumeroVerso());
        assertEquals(expected.getTexto(), response.getTexto());
    }

    @Test
    public void deveMapearConsultaVersoAleatorioResponseCorretamenteComAbreviacaoNull() {
        final ConsultaVersoAleatorioBibliaDigitalResponse expected = make(new ConsultaVersoAleatorioBibliaDigitalResponse());
        expected.getLivro().setAbreviacao(null);

        final ConsultaVersoAleatorioResponse response = CONSULTA_VERSO_ALEATORIO_RESPONSE_MAPPER.apply(expected);

        assertNotNull(response);

        assertNotNull(response.getLivro());
        assertNull(response.getLivro().getAbreviacao());
        assertEquals(expected.getLivro().getNome(), response.getLivro().getNome());
        assertEquals(expected.getLivro().getAutor(), response.getLivro().getAutor());
        assertEquals(expected.getLivro().getGrupo(), response.getLivro().getGrupo());
        assertEquals(expected.getLivro().getVersao(), response.getLivro().getVersao());

        assertEquals(expected.getNumeroCapitulo(), response.getNumeroCapitulo());
        assertEquals(expected.getNumeroVerso(), response.getNumeroVerso());
        assertEquals(expected.getTexto(), response.getTexto());
    }

}
