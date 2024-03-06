package br.com.luan.barcella.jesus.api.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Message {

    ABREVIACAO_INVALIDA("abreviacao.invalida"),

    CAPITULO_INVALIDO("capitulo.invalido"),

    FALHA_INESPERADA("falha.inesperada"),

    INDEX_INVALIDO("index.invalido"),

    NUMERO_ITENS_INVALIDO("numero-itens.invalido"),

    PALAVRA_INVALIDA("palavra.invalida"),

    REQUEST_INVALIDA("request.invalida"),

    SERVICO_INDISPONIVEL("servico.indisponivel"),

    VERSAO_INVALIDA("versao.invalida");

    private final String message;

}