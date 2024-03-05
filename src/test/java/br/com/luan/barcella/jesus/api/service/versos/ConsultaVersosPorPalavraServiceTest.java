package br.com.luan.barcella.jesus.api.service.versos;

import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.request.ConsultaVersosPorPalavraBibliaDigitalRequest;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersosPorPalavraBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.request.ConsultaVersosPorPalavraRequest;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersosPorPalavraResponse;
import br.com.luan.barcella.jesus.api.dto.response.PaginacaoResponse;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.validator.ConsultaVersosPorPalavraValidator;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaVersosPorPalavraServiceTest {

    @InjectMocks
    private ConsultaVersosPorPalavraService consultaVersosPorPalavraService;

    @Mock
    private ConsultaVersosPorPalavraValidator consultaVersosPorPalavraValidator;

    @Mock
    private BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    @Captor
    private ArgumentCaptor<ConsultaVersosPorPalavraBibliaDigitalRequest> consultaVersosPorPalavraBibliaDigitalRequestArgumentCaptor;

    @Test
    public void deveConsultarVersosPorPalavraCorretamente() {
        final ConsultaVersosPorPalavraRequest request = make(ConsultaVersosPorPalavraRequest.builder())
            .index(0)
            .numeroItens(2)
            .build();

        final ConsultaVersosPorPalavraBibliaDigitalResponse bibliaDigitalResponse = make(new ConsultaVersosPorPalavraBibliaDigitalResponse());

        when(bibliaDigitalRestIntegration.consultarVersosPorPalavra(any(ConsultaVersosPorPalavraBibliaDigitalRequest.class)))
            .thenReturn(bibliaDigitalResponse);

        final PaginacaoResponse<ConsultaVersosPorPalavraResponse> response = consultaVersosPorPalavraService.consultarVersosPorPalavra(request);

        verify(consultaVersosPorPalavraValidator).accept(request);
        verify(bibliaDigitalRestIntegration).consultarVersosPorPalavra(consultaVersosPorPalavraBibliaDigitalRequestArgumentCaptor.capture());

        final ConsultaVersosPorPalavraBibliaDigitalRequest requestCaptured = consultaVersosPorPalavraBibliaDigitalRequestArgumentCaptor.getValue();

        assertNotNull(requestCaptured);
        assertEquals(request.getVersao(), requestCaptured.getVersao());
        assertEquals(request.getPalavra(), requestCaptured.getPalavra());

        assertNotNull(response);
    }
}
