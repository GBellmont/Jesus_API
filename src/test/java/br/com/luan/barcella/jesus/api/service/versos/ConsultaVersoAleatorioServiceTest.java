package br.com.luan.barcella.jesus.api.service.versos;

import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoAleatorioBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoAleatorioResponse;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.validator.VersaoValidator;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaVersoAleatorioServiceTest {

    @InjectMocks
    private ConsultaVersoAleatorioService consultaVersoAleatorioService;

    @Mock
    private VersaoValidator versaoValidator;

    @Mock
    private BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    @Test
    public void deveConsultarVersoAleatorioCorretamente() {
        final String versao = randomAlphabetic(10);

        final ConsultaVersoAleatorioBibliaDigitalResponse bibliaDigitalResponse = make(new ConsultaVersoAleatorioBibliaDigitalResponse());

        when(bibliaDigitalRestIntegration.consultarVersoAleatorio(versao))
            .thenReturn(bibliaDigitalResponse);

        final ConsultaVersoAleatorioResponse response = consultaVersoAleatorioService.consultarVersoAleatorio(versao);

        verify(versaoValidator).accept(versao);
        verify(bibliaDigitalRestIntegration).consultarVersoAleatorio(versao);

        assertNotNull(response);
    }
}
