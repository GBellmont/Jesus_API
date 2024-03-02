package br.com.luan.barcella.jesus.api.service.versoes;

import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.generateList;
import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.pickRandom;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.luan.barcella.jesus.api.domain.VersaoBiblia;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoesBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoesResponse;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaVersoesServiceTest {

    @InjectMocks
    private ConsultaVersoesService consultaVersoesService;

    @Mock
    private BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    @Test
    public void deveConsultarVersoesCorretamente() {
        final List<ConsultaVersoesBibliaDigitalResponse> bibliaDigitalResponses = generateList(() -> {
            final VersaoBiblia versaoBiblia = pickRandom(VersaoBiblia.values());

            final ConsultaVersoesBibliaDigitalResponse response = make(new ConsultaVersoesBibliaDigitalResponse());
            response.setCodigoVersao(versaoBiblia.getCodigoBibliaDigital());

            return response;
        }, 1, 4);

        when(bibliaDigitalRestIntegration.consultarVersoes())
            .thenReturn(bibliaDigitalResponses);

        final ConsultaVersoesResponse response = consultaVersoesService.consultarVersoes();

        verify(bibliaDigitalRestIntegration).consultarVersoes();

        assertNotNull(response);
    }

}