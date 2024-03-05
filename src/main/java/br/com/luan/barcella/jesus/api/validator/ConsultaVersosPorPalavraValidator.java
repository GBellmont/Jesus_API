package br.com.luan.barcella.jesus.api.validator;

import static br.com.luan.barcella.jesus.api.domain.ErrorType.VALIDATION;
import static br.com.luan.barcella.jesus.api.domain.Message.PALAVRA_INVALIDA;
import static br.com.luan.barcella.jesus.api.domain.Message.REQUEST_INVALIDA;
import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import br.com.luan.barcella.jesus.api.dto.request.ConsultaVersosPorPalavraRequest;
import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsultaVersosPorPalavraValidator implements Consumer<ConsultaVersosPorPalavraRequest> {

    private final VersaoValidator versaoValidator;
    private final PaginacaoValidator paginacaoValidator;
    private final MessageService messageService;

    @Override
    public void accept(final ConsultaVersosPorPalavraRequest request) {

        if (isNull(request)) {
            log.warn("Request inválida ou não informada.");
            throw new ClientErrorException(VALIDATION, messageService.get(REQUEST_INVALIDA));
        }

        if (isBlank(request.getPalavra())) {
            log.warn("Palavra inválida ou não informada.");
            throw new ClientErrorException(VALIDATION, messageService.get(PALAVRA_INVALIDA));
        }

        versaoValidator.accept(request.getVersao());
        paginacaoValidator.accept(request.getIndex(),request.getNumeroItens());
    }
}
