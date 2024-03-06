package br.com.luan.barcella.jesus.api.validator;

import static br.com.luan.barcella.jesus.api.domain.ErrorType.VALIDATION;
import static br.com.luan.barcella.jesus.api.domain.Message.PALAVRA_INVALIDA;
import static br.com.luan.barcella.jesus.api.domain.Message.REQUEST_INVALIDA;
import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.luan.barcella.jesus.api.domain.Message;
import br.com.luan.barcella.jesus.api.dto.request.ConsultaVersosPorPalavraRequest;
import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.service.support.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaVersosPorPalavraValidatorTest {

    @InjectMocks
    private ConsultaVersosPorPalavraValidator consultaVersosPorPalavraValidator;

    @Mock
    private VersaoValidator versaoValidator;

    @Mock
    private PaginacaoValidator paginacaoValidator;

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
    public void deveLancarClientErrorExceptionQuandoRequestForNull() {
        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
           consultaVersosPorPalavraValidator.accept(null);
        });

        verify(messageService).get(REQUEST_INVALIDA);
        verifyNoInteractions(versaoValidator);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoPalavraForNull() {
        final ConsultaVersosPorPalavraRequest request = make(ConsultaVersosPorPalavraRequest.builder())
            .palavra(null)
            .build();

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            consultaVersosPorPalavraValidator.accept(request);
        });

        verify(messageService).get(PALAVRA_INVALIDA);
        verifyNoInteractions(versaoValidator);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoPalavraForStringEmpty() {
        final ConsultaVersosPorPalavraRequest request = make(ConsultaVersosPorPalavraRequest.builder())
            .palavra(EMPTY)
            .build();

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            consultaVersosPorPalavraValidator.accept(request);
        });

        verify(messageService).get(PALAVRA_INVALIDA);
        verifyNoInteractions(versaoValidator);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoPalavraForStringBlank() {
        final ConsultaVersosPorPalavraRequest request = make(ConsultaVersosPorPalavraRequest.builder())
            .palavra(SPACE)
            .build();

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            consultaVersosPorPalavraValidator.accept(request);
        });

        verify(messageService).get(PALAVRA_INVALIDA);
        verifyNoInteractions(versaoValidator);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void validatorOk() {
        final ConsultaVersosPorPalavraRequest request = make(ConsultaVersosPorPalavraRequest.builder())
            .build();

        consultaVersosPorPalavraValidator.accept(request);

        verify(versaoValidator).accept(request.getVersao());
        verify(paginacaoValidator).accept(request.getIndex(), request.getNumeroItens());
        verifyNoInteractions(messageService);
    }

}
