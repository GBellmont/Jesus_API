package br.com.luan.barcella.jesus.api.validator;

import static br.com.luan.barcella.jesus.api.domain.ErrorType.VALIDATION;
import static br.com.luan.barcella.jesus.api.domain.Message.INDEX_INVALIDO;
import static br.com.luan.barcella.jesus.api.domain.Message.NUMERO_ITENS_INVALIDO;
import static java.util.Objects.isNull;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Component;

import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaginacaoValidator implements BiConsumer<Integer, Integer> {

    private final MessageService messageService;

    @Override
    public void accept(final Integer index, final Integer numeroItens) {
        if (isNull(index)) {
            log.warn("Index inválido ou não informado!");
            throw new ClientErrorException(VALIDATION, messageService.get(INDEX_INVALIDO));
        }

        if (isNull(numeroItens)) {
            log.warn("Número de itens inválido ou não informado!");
            throw new ClientErrorException(VALIDATION, messageService.get(NUMERO_ITENS_INVALIDO));
        }
    }
}
