package br.com.luan.barcella.jesus.api.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Message {

    FALHA_INESPERADA("falha.inesperada"),
    SERVICO_INDISPONIVEL("servico.indisponivel");

    private final String message;

}