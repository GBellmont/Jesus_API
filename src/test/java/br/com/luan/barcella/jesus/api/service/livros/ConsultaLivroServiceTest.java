package br.com.luan.barcella.jesus.api.service.livros;

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

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivroBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivroResponse;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.validator.ConsultaLivroValidator;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaLivroServiceTest {

    @InjectMocks
    private ConsultaLivroService consultaLivroService;

    @Mock
    private ConsultaLivroValidator consultaLivroValidator;

    @Mock
    private BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    @Test
    public void deveConsultarLivroCorretamente() {
        final String abreviacao = randomAlphabetic(10);
        final ConsultaLivroBibliaDigitalResponse livroBibliaDigitalResponse = make(new ConsultaLivroBibliaDigitalResponse());

        when(bibliaDigitalRestIntegration.consultarLivro(abreviacao))
            .thenReturn(livroBibliaDigitalResponse);

        final ConsultaLivroResponse response = consultaLivroService.consultarLivro(abreviacao);

        verify(consultaLivroValidator).accept(abreviacao);
        verify(bibliaDigitalRestIntegration).consultarLivro(abreviacao);

        assertNotNull(response);
    }

}
