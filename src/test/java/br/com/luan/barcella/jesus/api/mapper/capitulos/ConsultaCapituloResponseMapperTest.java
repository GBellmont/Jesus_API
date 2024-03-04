package br.com.luan.barcella.jesus.api.mapper.capitulos;

import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaCapituloBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaCapituloBibliaDigitalResponse.ConsultaCapituloVersoBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaCapituloResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaCapituloResponse.ConsultaCapituloVersoResponse;

public class ConsultaCapituloResponseMapperTest {

    private static final ConsultaCapituloResponseMapper CONSULTA_CAPITULO_RESPONSE_MAPPER = new ConsultaCapituloResponseMapper();

    @Test
    public void deveRetornarNullQuandoCapituloResponseForNull() {
        final ConsultaCapituloResponse response = CONSULTA_CAPITULO_RESPONSE_MAPPER.apply(null);

        assertNull(response);
    }

    @Test
    public void deveMapearConsultaCapituloResponseCorretamente() {
        final ConsultaCapituloBibliaDigitalResponse expected = make(new ConsultaCapituloBibliaDigitalResponse());

        final ConsultaCapituloResponse response = CONSULTA_CAPITULO_RESPONSE_MAPPER.apply(expected);

        assertNotNull(response);

        assertNotNull(response.getLivro());
        assertNotNull(response.getLivro().getAbreviacao());
        assertEquals(expected.getLivro().getAbreviacao().getPortugues(), response.getLivro().getAbreviacao().getPortugues());
        assertEquals(expected.getLivro().getAbreviacao().getIngles(), response.getLivro().getAbreviacao().getIngles());
        assertEquals(expected.getLivro().getNome(), response.getLivro().getNome());
        assertEquals(expected.getLivro().getAutor(), response.getLivro().getAutor());
        assertEquals(expected.getLivro().getGrupo(), response.getLivro().getGrupo());
        assertEquals(expected.getLivro().getVersao(), response.getLivro().getVersao());

        assertNotNull(response.getCapitulo());
        assertEquals(expected.getCapitulo().getNumero(), response.getCapitulo().getNumero());
        assertEquals(expected.getCapitulo().getQuantidadeVersos(), response.getCapitulo().getQuantidadeVersos());

        assertNotNull(response.getVersos());
        assertFalse(response.getVersos().isEmpty());
        assertVersos(expected.getVersos(), response.getVersos());
    }

    @Test
    public void deveMapearConsultaCapituloResponseCorretamenteComCamposNullos() {
        final ConsultaCapituloBibliaDigitalResponse expected = make(new ConsultaCapituloBibliaDigitalResponse());
        expected.setLivro(null);
        expected.setCapitulo(null);
        expected.setVersos(null);

        final ConsultaCapituloResponse response = CONSULTA_CAPITULO_RESPONSE_MAPPER.apply(expected);

        assertNotNull(response);

        assertNull(response.getLivro());
        assertNull(response.getCapitulo());
        assertNotNull(response.getVersos());
        assertTrue(response.getVersos().isEmpty());
    }

    @Test
    public void deveMapearConsultaCapituloResponseCorretamenteComVersosVazios() {
        final ConsultaCapituloBibliaDigitalResponse expected = make(new ConsultaCapituloBibliaDigitalResponse());
        expected.getLivro().setAbreviacao(null);
        expected.setCapitulo(null);
        expected.setVersos(new ArrayList<>());

        final ConsultaCapituloResponse response = CONSULTA_CAPITULO_RESPONSE_MAPPER.apply(expected);

        assertNotNull(response);

        assertNotNull(response.getLivro());
        assertNull(response.getLivro().getAbreviacao());
        assertNull(response.getCapitulo());
        assertNotNull(response.getVersos());
        assertTrue(response.getVersos().isEmpty());
    }

    private void assertVersos(final List<ConsultaCapituloVersoBibliaDigitalResponse> expected, final List<ConsultaCapituloVersoResponse> response) {
        for (int i = 0; i < expected.size(); i++) {
            assertNotNull(response.get(i));
            assertEquals(expected.get(i).getNumero(), response.get(i).getNumero());
            assertEquals(expected.get(i).getTexto(), response.get(i).getTexto());
        }
    }

}
