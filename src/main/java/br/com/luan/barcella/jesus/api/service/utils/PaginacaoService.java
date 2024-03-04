package br.com.luan.barcella.jesus.api.service.utils;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import br.com.luan.barcella.jesus.api.dto.response.PaginacaoResponse;

public class PaginacaoService {

    private static final int INDEX_ZERO = 0;
    private static final int ITENS_MINIMOS_INDEX_0 = 1;

    public static <T, R> PaginacaoResponse<T> paginarComObjetoResponseMapper(final Integer index, final Integer numeroItens,
        final List<R> itens, final Function<List<R>, List<T>> mapper) {

        final PaginacaoResponse<R> paginacaoSemMapear = paginarComObjetoResponse(index, numeroItens, itens);

        return PaginacaoResponse.<T>builder()
            .index(paginacaoSemMapear.getIndex())
            .numeroItens(paginacaoSemMapear.getNumeroItens())
            .itens(mapper.apply(paginacaoSemMapear.getItens()))
            .primeiraPagina(paginacaoSemMapear.isPrimeiraPagina())
            .ultimaPagina(paginacaoSemMapear.isUltimaPagina())
            .build();
    }

    public static <T> PaginacaoResponse<T> paginarComObjetoResponse(final Integer index, final Integer numeroItens, final List<T> itens){
        final int indexMaximo = calcularIndexMaximo(calcularIndexMinimo(index, numeroItens), numeroItens, itens);
        final int tamanhoLista = ofNullable(itens).map(List::size).orElseGet(() -> null);

        return PaginacaoResponse.<T>builder()
            .index(index)
            .numeroItens(numeroItens)
            .itens(paginar(index, numeroItens, itens))
            .primeiraPagina(index == INDEX_ZERO)
            .ultimaPagina(indexMaximo == tamanhoLista)
            .build();
    }

    public static <T> List<T> paginar(final Integer index, final Integer numeroItens, final List<T> itens) {
        if (isNull(index) || isNull(numeroItens) || isNull(itens)) {
            return new ArrayList<>();
        }

        if (contemNumerosInsuficientes(index, numeroItens, itens)) {
            return new ArrayList<>();
        }

        final int indexMinimo = calcularIndexMinimo(index, numeroItens);
        final int indexMaximo = calcularIndexMaximo(indexMinimo, numeroItens, itens);

        return itens.subList(indexMinimo, indexMaximo);
    }

    private static <T> boolean contemNumerosInsuficientes(final Integer index, final Integer numeroItens, final List<T> itens) {
        final int itensMinimos = (index == INDEX_ZERO) ? ITENS_MINIMOS_INDEX_0 : (index * numeroItens) + 1;

        return itens.isEmpty() || itens.size() < itensMinimos;
    }

    private static int calcularIndexMinimo(final Integer index, final Integer numeroItens) {
        return (index == INDEX_ZERO) ? INDEX_ZERO : index * numeroItens;
    }

    private static <T> int calcularIndexMaximo(final Integer indexMinimo, final Integer numeroItens, final List<T> itens) {
        final int indexMaximoDesejado = indexMinimo + numeroItens;
        final int posicaoMaximaLista = itens.size() - 1;

        return posicaoMaximaLista < (indexMaximoDesejado - 1) ? itens.size() : indexMaximoDesejado;
    }
}
