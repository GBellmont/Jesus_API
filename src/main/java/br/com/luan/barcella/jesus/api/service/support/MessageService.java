package br.com.luan.barcella.jesus.api.service.support;

import static java.util.Objects.isNull;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.luan.barcella.jesus.api.domain.Message;

@Service
public class MessageService {

    @Autowired
    private MessageSource messageSource;

    private final Locale pt_BR = new Locale("pt", "BR");

    /**
     * Obtém mensagem com base nos arquivos de messages.properties da API. Locale pt_BR hardcoded para garantir que este
     * seja o bundle carregado.
     */
    public String get(final Message mensagem, final Object... args) {

        if (isNull(mensagem)) {
            return "";
        }

        return messageSource.getMessage(mensagem.getMessage(), args, pt_BR);
    }
}
