package br.com.luan.barcella.jesus.api.service.capitulos;

import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaCapituloBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaCapituloResponse;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.validator.ConsultaCapituloValidator;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaCapituloServiceTest {

    @InjectMocks
    private ConsultaCapituloService consultaCapituloService;

    @Mock
    private ConsultaCapituloValidator consultaCapituloValidator;

    @Mock
    private BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    @Test
    public void deveConsultarCapituloCorretamente() {
        final String versao = randomAlphabetic(10);
        final String abreviacao = randomAlphabetic(10);
        final Integer capitulo = nextInt();

        final ConsultaCapituloBibliaDigitalResponse bibliaDigitalResponse = make(new ConsultaCapituloBibliaDigitalResponse());

        when(bibliaDigitalRestIntegration.consultarCapitulo(versao, abreviacao, capitulo))
            .thenReturn(bibliaDigitalResponse);

        final ConsultaCapituloResponse response = consultaCapituloService.consultarCapitulo(versao, abreviacao, capitulo);

        verify(consultaCapituloValidator).accept(versao, abreviacao, capitulo);
        verify(bibliaDigitalRestIntegration).consultarCapitulo(versao, abreviacao, capitulo);

        assertNotNull(response);
    }

}
