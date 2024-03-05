package br.com.luan.barcella.jesus.api.validator;

import static br.com.luan.barcella.jesus.api.domain.ErrorType.VALIDATION;
import static br.com.luan.barcella.jesus.api.domain.Message.ABREVIACAO_INVALIDA;
import static br.com.luan.barcella.jesus.api.domain.Message.CAPITULO_INVALIDO;
import static br.com.luan.barcella.jesus.api.fixture.Fixture.make;
import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.generateList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
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
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.AbreviacaoBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivrosBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.service.support.MessageService;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaCapituloValidatorTest {

    @InjectMocks
    private ConsultaCapituloValidator consultaCapituloValidator;

    @Mock
    private BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    @Mock
    private VersaoValidator versaoValidator;

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
    public void deveLancarClientErrorExceptionQuandoAbreviacaoForNull() {
        final String versao = randomAlphabetic(10);

        final List<ConsultaLivrosBibliaDigitalResponse> livrosResponse = generateList(
            () -> make(new ConsultaLivrosBibliaDigitalResponse()), 10, 10);

        when(bibliaDigitalRestIntegration.consultarLivros())
            .thenReturn(livrosResponse);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            consultaCapituloValidator.accept(versao, null, null);
        });

        verify(versaoValidator).accept(versao);
        verify(bibliaDigitalRestIntegration).consultarLivros();
        verify(messageService).get(ABREVIACAO_INVALIDA);
        verifyNoMoreInteractions(bibliaDigitalRestIntegration);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoAbreviacaoForStringEmpty() {
        final String versao = randomAlphabetic(10);

        final List<ConsultaLivrosBibliaDigitalResponse> livrosResponse = generateList(
            () -> make(new ConsultaLivrosBibliaDigitalResponse()), 10, 10);

        when(bibliaDigitalRestIntegration.consultarLivros())
            .thenReturn(livrosResponse);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            consultaCapituloValidator.accept(versao, EMPTY, null);
        });

        verify(versaoValidator).accept(versao);
        verify(bibliaDigitalRestIntegration).consultarLivros();
        verify(messageService).get(ABREVIACAO_INVALIDA);
        verifyNoMoreInteractions(bibliaDigitalRestIntegration);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoAbreviacaoForStringBlank() {
        final String versao = randomAlphabetic(10);

        final List<ConsultaLivrosBibliaDigitalResponse> livrosResponse = generateList(() -> make(new ConsultaLivrosBibliaDigitalResponse()), 10, 10);

        when(bibliaDigitalRestIntegration.consultarLivros())
            .thenReturn(livrosResponse);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            consultaCapituloValidator.accept(versao, SPACE, null);
        });

        verify(versaoValidator).accept(versao);
        verify(bibliaDigitalRestIntegration).consultarLivros();
        verify(messageService).get(ABREVIACAO_INVALIDA);
        verifyNoMoreInteractions(bibliaDigitalRestIntegration);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoNaoExistirLivroParaAbreviacao() {
        final String versao = randomAlphabetic(10);
        final String abreviacao = randomAlphabetic(10);

        final List<ConsultaLivrosBibliaDigitalResponse> livrosResponse = gerarListaLivrosDiferentes(abreviacao);

        when(bibliaDigitalRestIntegration.consultarLivros())
            .thenReturn(livrosResponse);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            consultaCapituloValidator.accept(versao, abreviacao, null);
        });

        verify(versaoValidator).accept(versao);
        verify(bibliaDigitalRestIntegration).consultarLivros();
        verify(messageService).get(ABREVIACAO_INVALIDA);
        verifyNoMoreInteractions(bibliaDigitalRestIntegration);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoCapituloForNull() {
        final String versao = randomAlphabetic(10);
        final String abreviacao = randomAlphabetic(10);

        final List<ConsultaLivrosBibliaDigitalResponse> livrosResponse = gerarListaLivrosComLivroIgualEhNumeroCapitulosMenor(abreviacao, nextInt());

        when(bibliaDigitalRestIntegration.consultarLivros())
            .thenReturn(livrosResponse);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            consultaCapituloValidator.accept(versao, abreviacao, null);
        });

        verify(versaoValidator).accept(versao);
        verify(bibliaDigitalRestIntegration).consultarLivros();
        verify(messageService).get(CAPITULO_INVALIDO);
        verifyNoMoreInteractions(bibliaDigitalRestIntegration);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void deveLancarClientErrorExceptionQuandoCapituloNaoExistirNoLivro() {
        final String versao = randomAlphabetic(10);
        final String abreviacao = randomAlphabetic(10);
        final Integer capitulo = nextInt();

        final List<ConsultaLivrosBibliaDigitalResponse> livrosResponse = gerarListaLivrosComLivroIgualEhNumeroCapitulosMenor(abreviacao, capitulo);

        when(bibliaDigitalRestIntegration.consultarLivros())
            .thenReturn(livrosResponse);

        final ClientErrorException exception = Assertions.assertThrows(ClientErrorException.class, () -> {
            consultaCapituloValidator.accept(versao, abreviacao, capitulo);
        });

        verify(versaoValidator).accept(versao);
        verify(bibliaDigitalRestIntegration).consultarLivros();
        verify(messageService).get(CAPITULO_INVALIDO);
        verifyNoMoreInteractions(bibliaDigitalRestIntegration);

        assertNotNull(exception);
        assertEquals(VALIDATION, exception.getErrorType());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void validatorOk() {
        final String versao = randomAlphabetic(10);
        final String abreviacao = randomAlphabetic(10);
        final Integer capitulo = nextInt();

        final List<ConsultaLivrosBibliaDigitalResponse> livrosResponse = gerarListaLivrosComLivroIgualEhNumeroCapitulosMaior(abreviacao, capitulo);

        when(bibliaDigitalRestIntegration.consultarLivros())
            .thenReturn(livrosResponse);

        consultaCapituloValidator.accept(versao, abreviacao, capitulo);

        verify(versaoValidator).accept(versao);
        verify(bibliaDigitalRestIntegration).consultarLivros();
        verifyNoMoreInteractions(bibliaDigitalRestIntegration);
        verifyNoInteractions(messageService);
    }

    private List<ConsultaLivrosBibliaDigitalResponse> gerarListaLivrosDiferentes(final String abreviacao) {
        return generateList(() -> {
            final AbreviacaoBibliaDigitalResponse abreviacaoResponse = new AbreviacaoBibliaDigitalResponse();
            abreviacaoResponse.setPortugues(randomAlphabetic(abreviacao.length() + 2));

            final ConsultaLivrosBibliaDigitalResponse livro = make(new ConsultaLivrosBibliaDigitalResponse());
            livro.setAbreviacao(abreviacaoResponse);

            return livro;
        }, 10, 10);
    }

    private List<ConsultaLivrosBibliaDigitalResponse> gerarListaLivrosComLivroIgualEhNumeroCapitulosMenor(final String abreviacao, final Integer capitulo) {
        return generateList(() -> {
            final AbreviacaoBibliaDigitalResponse abreviacaoResponse = new AbreviacaoBibliaDigitalResponse();
            abreviacaoResponse.setPortugues(abreviacao);

            final ConsultaLivrosBibliaDigitalResponse livro = make(new ConsultaLivrosBibliaDigitalResponse());
            livro.setAbreviacao(abreviacaoResponse);
            livro.setNumeroCapitulos(capitulo - 2);

            return livro;
        }, 10, 10);
    }

    private List<ConsultaLivrosBibliaDigitalResponse> gerarListaLivrosComLivroIgualEhNumeroCapitulosMaior(final String abreviacao, final Integer capitulo) {
        return generateList(() -> {
            final AbreviacaoBibliaDigitalResponse abreviacaoResponse = new AbreviacaoBibliaDigitalResponse();
            abreviacaoResponse.setPortugues(abreviacao);

            final ConsultaLivrosBibliaDigitalResponse livro = make(new ConsultaLivrosBibliaDigitalResponse());
            livro.setAbreviacao(abreviacaoResponse);
            livro.setNumeroCapitulos(capitulo + 2);

            return livro;
        }, 10, 10);
    }
}
