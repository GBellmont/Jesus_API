package br.com.luan.barcella.jesus.api.fixture;

import org.jeasy.random.EasyRandom;

/**
 * Classe utilitária usada para a geração de Fixtures (Objetos preenchidos com dados aleatórios).
 *
 * Exemplos de uso: Para retornar um objeto preenchido que tenha padrão Builder: Response =
 * Fixture.make(Response.builder().build());
 *
 * Para retornar um objeto preenchido que tenha padrão de Setter/Construtor vazio: Response =
 * Fixture.make(newResponse());
 *
 * Também é possível retornar um builder:
 *
 * Response.ResponseBuilder = Fixture.make(Response.builder());
 *
 * Este builder retorna com todos os atributos preenchidos, e é possível sobreescrever os atributos que quiser antes de
 * dar o .build();
 */
public class Fixture {

    private static final EasyRandom easyRandom = new EasyRandom();

    @SuppressWarnings("unchecked")
    public static <T> T make(final T mockClass) {
        return (T) easyRandom.nextObject(mockClass.getClass());
    }
}
