package br.com.luan.barcella.jesus.api.validator;

import static br.com.luan.barcella.jesus.api.domain.ErrorType.VALIDATION;
import static br.com.luan.barcella.jesus.api.domain.Message.ABREVIACAO_INVALIDA;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsultaLivroValidator implements Consumer<String> {

    private final MessageService messageService;

    @Override
    public void accept(final String abreviacao) {
        if (isBlank(abreviacao)) {
            log.warn("Abreviação inválida ou não informada!");
            throw new ClientErrorException(VALIDATION, messageService.get(ABREVIACAO_INVALIDA));
        }
    }
}
