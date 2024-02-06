package br.com.luan.barcella.jesus.api.service.rest.livros;

import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.generateList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivrosBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivrosResponse;
import br.com.luan.barcella.jesus.api.fixture.Fixture;
import br.com.luan.barcella.jesus.api.service.livros.ConsultaLivrosService;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaLivrosServiceTest {

    @InjectMocks
    private ConsultaLivrosService consultaLivrosService;

    @Mock
    private BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    @Test
    public void consultarLivrosSucesso() {
        final List<ConsultaLivrosBibliaDigitalResponse> livrosBibliaDigitalResponses = generateList(
            () -> Fixture.make(new ConsultaLivrosBibliaDigitalResponse()), 1, 10);

        when(bibliaDigitalRestIntegration.consultarLivros())
            .thenReturn(livrosBibliaDigitalResponses);

        final List<ConsultaLivrosResponse> response = consultaLivrosService.consultarLivros();

        verify(bibliaDigitalRestIntegration).consultarLivros();

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

}
