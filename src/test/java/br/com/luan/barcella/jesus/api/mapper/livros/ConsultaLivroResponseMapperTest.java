package br.com.luan.barcella.jesus.api.mapper.livros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivroBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivroResponse;
import br.com.luan.barcella.jesus.api.fixture.Fixture;

public class ConsultaLivroResponseMapperTest {

    private static final ConsultaLivroResponseMapper CONSULTA_LIVRO_RESPONSE_MAPPER = new ConsultaLivroResponseMapper();

    @Test
    public void deveRetornarNullQuandoParametroEntradaForNull() {
        final ConsultaLivroResponse response = CONSULTA_LIVRO_RESPONSE_MAPPER.apply(null);

        assertNull(response);
    }

    @Test
    public void deveMapearConsultaLivroResponseCorretamente() {
        final ConsultaLivroBibliaDigitalResponse consultaLivroBibliaDigitalResponse = Fixture.make(new ConsultaLivroBibliaDigitalResponse());

        final ConsultaLivroResponse response = CONSULTA_LIVRO_RESPONSE_MAPPER.apply(consultaLivroBibliaDigitalResponse);

        assertNotNull(response);
        assertEquals(consultaLivroBibliaDigitalResponse.getAbreviacao().getPortugues(), response.getAbreviacao().getPortugues());
        assertEquals(consultaLivroBibliaDigitalResponse.getAbreviacao().getIngles(), response.getAbreviacao().getIngles());
        assertEquals(consultaLivroBibliaDigitalResponse.getAutor(), response.getAutor());
        assertEquals(consultaLivroBibliaDigitalResponse.getNumeroCapitulos(), response.getNumeroCapitulos());
        assertEquals(consultaLivroBibliaDigitalResponse.getComentario(), response.getComentario());
        assertEquals(consultaLivroBibliaDigitalResponse.getGrupo(), response.getGrupo());
        assertEquals(consultaLivroBibliaDigitalResponse.getNome(), response.getNome());
        assertEquals(consultaLivroBibliaDigitalResponse.getTestamento(), response.getTestamento());
    }

}
