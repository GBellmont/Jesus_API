package br.com.luan.barcella.jesus.api.validator;

import static br.com.luan.barcella.jesus.api.domain.ErrorType.VALIDATION;
import static br.com.luan.barcella.jesus.api.domain.Message.ABREVIACAO_INVALIDA;
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
import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.service.support.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaLivroValidatorTest {

    @InjectMocks
    private ConsultaLivroValidator consultaLivroValidator;

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
    public void deveLanarClientErrorExceptionQuandoAbreviacaoForNull() {
        final ClientErrorException clientErrorException = Assertions.assertThrows(ClientErrorException.class, () -> {
           consultaLivroValidator.accept(null);
        });

        verify(messageService).get(ABREVIACAO_INVALIDA);

        assertNotNull(clientErrorException);
        assertEquals(VALIDATION, clientErrorException.getErrorType());
        assertEquals(message, clientErrorException.getMessage());
    }

    @Test
    public void deveLanarClientErrorExceptionQuandoAbreviacaoForUmaStringVazia() {
        final ClientErrorException clientErrorException = Assertions.assertThrows(ClientErrorException.class, () -> {
            consultaLivroValidator.accept(EMPTY);
        });

        verify(messageService).get(ABREVIACAO_INVALIDA);

        assertNotNull(clientErrorException);
        assertEquals(VALIDATION, clientErrorException.getErrorType());
        assertEquals(message, clientErrorException.getMessage());
    }

    @Test
    public void deveLanarClientErrorExceptionQuandoAbreviacaoForUmaStringEmBranco() {
        final ClientErrorException clientErrorException = Assertions.assertThrows(ClientErrorException.class, () -> {
            consultaLivroValidator.accept(SPACE);
        });

        verify(messageService).get(ABREVIACAO_INVALIDA);

        assertNotNull(clientErrorException);
        assertEquals(VALIDATION, clientErrorException.getErrorType());
        assertEquals(message, clientErrorException.getMessage());
    }

    @Test
    public void validatorOk() {
        final String abreviacao = randomAlphabetic(10);

        consultaLivroValidator.accept(abreviacao);

        verifyNoInteractions(messageService);
    }

}
