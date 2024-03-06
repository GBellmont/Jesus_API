package br.com.luan.barcella.jesus.api.validator;

import static br.com.luan.barcella.jesus.api.domain.ErrorType.VALIDATION;
import static br.com.luan.barcella.jesus.api.domain.Message.VERSAO_INVALIDA;
import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.generateList;
import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.pickRandom;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.luan.barcella.jesus.api.domain.Message;
import br.com.luan.barcella.jesus.api.domain.VersaoBiblia;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoesBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.service.support.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class VersaoValidatorTest {

    @InjectMocks
    private VersaoValidator versaoValidator;

    @Mock
    private BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    @Mock
    private MessageService messageService;

    private String message;

    @Before
    public void setup() {
        message = randomAlphabetic(10);

        when(messageService.get(any(Message.class)))
            .thenReturn(message);
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoVersaoForNull() {
        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            versaoValidator.accept(null);
        });

        verify(messageService).get(VERSAO_INVALIDA);
        verifyNoInteractions(bibliaDigitalRestIntegration);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoVersaoForStringEmpty() {
        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            versaoValidator.accept(EMPTY);
        });

        verify(messageService).get(VERSAO_INVALIDA);
        verifyNoInteractions(bibliaDigitalRestIntegration);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoVersaoForStringBlank() {
        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            versaoValidator.accept(SPACE);
        });

        verify(messageService).get(VERSAO_INVALIDA);
        verifyNoInteractions(bibliaDigitalRestIntegration);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoVersaoNaoExistir() {
        final String versao = randomAlphabetic(10);

        final List<ConsultaVersoesBibliaDigitalResponse> versoesResponse = gerarListaVersoesDiferentes(versao);

        when(bibliaDigitalRestIntegration.consultarVersoes())
            .thenReturn(versoesResponse);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            versaoValidator.accept(versao);
        });

        verify(bibliaDigitalRestIntegration).consultarVersoes();
        verify(messageService).get(VERSAO_INVALIDA);
        verifyNoMoreInteractions(bibliaDigitalRestIntegration);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void validatorOk() {
        final String versao = randomAlphabetic(10);

        final List<ConsultaVersoesBibliaDigitalResponse> versoesResponse = gerarListaVersoesComVersaoIgual(versao);

        when(bibliaDigitalRestIntegration.consultarVersoes())
            .thenReturn(versoesResponse);

        versaoValidator.accept(versao);

        verify(bibliaDigitalRestIntegration).consultarVersoes();
        verifyNoInteractions(messageService);
        verifyNoMoreInteractions(bibliaDigitalRestIntegration);
    }

    private List<ConsultaVersoesBibliaDigitalResponse> gerarListaVersoesDiferentes(final String versao) {
        return generateList(() -> {
            final VersaoBiblia versaoBiblia = pickRandom(VersaoBiblia.values());

            final ConsultaVersoesBibliaDigitalResponse response = make(new ConsultaVersoesBibliaDigitalResponse());
            response.setCodigoVersao(versaoBiblia.getCodigoBibliaDigital());
            response.setCodigoVersao(randomAlphabetic(versao.length() + 3));

            return response;
        }, 1, 4);
    }

    private List<ConsultaVersoesBibliaDigitalResponse> gerarListaVersoesComVersaoIgual(final String versao) {
        return generateList(() -> {
            final VersaoBiblia versaoBiblia = pickRandom(VersaoBiblia.values());

            final ConsultaVersoesBibliaDigitalResponse response = make(new ConsultaVersoesBibliaDigitalResponse());
            response.setCodigoVersao(versaoBiblia.getCodigoBibliaDigital());
            response.setCodigoVersao(versao);

            return response;
        }, 1, 4);
    }

}
