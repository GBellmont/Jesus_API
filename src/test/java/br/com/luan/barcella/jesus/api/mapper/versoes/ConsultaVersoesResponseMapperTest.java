package br.com.luan.barcella.jesus.api.mapper.versoes;

import static br.com.luan.barcella.jesus.api.domain.VersaoBiblia.fromCodigoBibliaDigital;
import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.generateList;
import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.pickRandom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.luan.barcella.jesus.api.domain.VersaoBiblia;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoesBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoesResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoesResponse.VersaoResponse;

public class ConsultaVersoesResponseMapperTest {

    private static final ConsultaVersoesResponseMapper CONSULTA_VERSOES_RESPONSE_MAPPER = new ConsultaVersoesResponseMapper();

    @Test
    public void deveRetornarNullQuandoConsultaVersoesBibliaDigitalResponseListForNull() {
        final ConsultaVersoesResponse response = CONSULTA_VERSOES_RESPONSE_MAPPER.apply(null);

        assertNull(response);
    }

    @Test
    public void deveRetornarNullQuandoConsultaVersoesBibliaDigitalResponseListForVazia() {
        final ConsultaVersoesResponse response = CONSULTA_VERSOES_RESPONSE_MAPPER.apply(new ArrayList<>());

        assertNull(response);
    }

    @Test
    public void deveMapearConsultaVersoesResponseCorretamente() {
        final List<ConsultaVersoesBibliaDigitalResponse> bibliaDigitalResponses = generateList(() -> {
            final VersaoBiblia versaoBiblia = pickRandom(VersaoBiblia.values());

            final ConsultaVersoesBibliaDigitalResponse response = make(new ConsultaVersoesBibliaDigitalResponse());
            response.setCodigoVersao(versaoBiblia.getCodigoBibliaDigital());

            return response;
        }, 1, 4);

        final ConsultaVersoesResponse response = CONSULTA_VERSOES_RESPONSE_MAPPER.apply(bibliaDigitalResponses);

        assertNotNull(response);
        assertNotNull(response.getVersoes());
        assertEquals(bibliaDigitalResponses.size(), response.getVersoes().size());
        assertVersaoResponse(response.getVersoes(), bibliaDigitalResponses);
    }

    private void assertVersaoResponse(final List<VersaoResponse> responses, final List<ConsultaVersoesBibliaDigitalResponse> expected) {
        for (int i = 0; i < expected.size(); i++) {
            final VersaoBiblia versao = fromCodigoBibliaDigital(expected.get(i).getCodigoVersao());

            assertNotNull(responses);
            assertEquals(expected.get(i).getCodigoVersao(), responses.get(i).getCodigoVersao());
            assertEquals(expected.get(i).getNumeroVersos(), responses.get(i).getNumeroVersos());
            assertEquals(versao.getNome(), responses.get(i).getNomeCompleto());
            assertEquals(versao.getDescricao(), responses.get(i).getDescricao());
        }
    }

}
