package br.com.luan.barcella.jesus.api.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Testamento {

    NT("Novo Testamento"),
    VT("Velho Testamento");

    private final String nome;
}
