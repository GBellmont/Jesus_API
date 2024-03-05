package br.com.luan.barcella.jesus.api.validator;

import static br.com.luan.barcella.jesus.api.domain.ErrorType.VALIDATION;
import static br.com.luan.barcella.jesus.api.domain.Message.VERSAO_INVALIDA;
import static org.apache.logging.log4j.util.Strings.isBlank;

import java.util.Objects;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import br.com.luan.barcella.jesus.api.exception.ClientErrorException;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import br.com.luan.barcella.jesus.api.service.support.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VersaoValidator implements Consumer<String> {

    private final BibliaDigitalRestIntegration bibliaDigitalRestIntegration;
    private final MessageService messageService;

    @Override
    public void accept(final String versao) {

        if (isBlank(versao) || verificaNaoExistenciaVersao(versao)) {
            log.warn("Versão inválida ou não informada.");
            throw new ClientErrorException(VALIDATION, messageService.get(VERSAO_INVALIDA));
        }
    }

    private boolean verificaNaoExistenciaVersao(final String versao) {
        return bibliaDigitalRestIntegration.consultarVersoes()
            .stream()
            .filter(Objects::nonNull)
            .noneMatch((versaoConsultada) -> versao.equals(versaoConsultada.getCodigoVersao()));
    }
}
