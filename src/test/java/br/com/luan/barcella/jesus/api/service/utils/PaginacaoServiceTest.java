package br.com.luan.barcella.jesus.api.service.utils;

import static br.com.luan.barcella.jesus.api.service.utils.PaginacaoService.paginar;
import static br.com.luan.barcella.jesus.api.service.utils.PaginacaoService.paginarComObjetoResponse;
import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.generateList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.luan.barcella.jesus.api.dto.response.PaginacaoResponse;

public class PaginacaoServiceTest {

    @Test
    public void deveRetornarArrayVazioQuandoIndexForNull() {
        final List<Object> responsePaginado = paginar(null, null, null);

        assertNotNull(responsePaginado);
        assertTrue(responsePaginado.isEmpty());
    }

    @Test
    public void deveRetornarArrayVazioQuandoNumeroItensForNull() {
        final int index = 0;

        final List<Object> responsePaginado = paginar(index, null, null);

        assertNotNull(responsePaginado);
        assertTrue(responsePaginado.isEmpty());
    }

    @Test
    public void deveRetornarArrayVazioQuandoItensForemNull() {
        final int index = 0;
        final int numeroItens = 2;

        final List<Object> responsePaginado = paginar(index, numeroItens, null);

        assertNotNull(responsePaginado);
        assertTrue(responsePaginado.isEmpty());
    }

    @Test
    public void deveRetornarArrayVazioQuandoItensForVazioIndexZero() {
        final int index = 0;
        final int numeroItens = 2;

        final List<Object> responsePaginado = paginar(index, numeroItens, new ArrayList<>());

        assertNotNull(responsePaginado);
        assertTrue(responsePaginado.isEmpty());
    }

    @Test
    public void deveRetornarArrayVazioQuandoItensForVazioIndexDiferenteZero() {
        final int index = 2;
        final int numeroItens = 2;

        final List<Object> responsePaginado = paginar(index, numeroItens, new ArrayList<>());

        assertNotNull(responsePaginado);
        assertTrue(responsePaginado.isEmpty());
    }

    @Test
    public void deveRetornarArrayVazioQuandoItensNaoTiverQuantidadeNecessariaIndexDiferenteZero() {
        final int index = 2;
        final int numeroItens = 2;

        final List<Object> itens = generateList(Object::new, 4, 4);

        final List<Object> responsePaginado = paginar(index, numeroItens, itens);

        assertNotNull(responsePaginado);
        assertTrue(responsePaginado.isEmpty());
    }

    @Test
    public void deveRetornarListaNaPaginaCorretaComMenosItens() {
        final int index = 2;
        final int numeroItens = 2;
        final int tamanhoEsperado = 1;

        final List<Object> itens = generateList(Object::new, 5, 5);

        final List<Object> responsePaginado = paginar(index, numeroItens, itens);

        assertNotNull(responsePaginado);
        assertEquals(tamanhoEsperado, responsePaginado.size());
        assertEquals(itens.get(4), responsePaginado.get(0));
    }

    @Test
    public void deveRetornarListaNaPaginaCorretaComNumeroItensMaximoIndexZero() {
        final int index = 0;
        final int numeroItens = 3;
        final int tamanhoEsperado = 3;

        final List<Object> itens = generateList(Object::new, 5, 5);

        final List<Object> responsePaginado = paginar(index, numeroItens, itens);

        assertNotNull(responsePaginado);
        assertEquals(tamanhoEsperado, responsePaginado.size());
        assertEquals(itens.get(0), responsePaginado.get(0));
        assertEquals(itens.get(1), responsePaginado.get(1));
        assertEquals(itens.get(2), responsePaginado.get(2));
    }

    @Test
    public void deveRetornarListaNaPaginaCorretaComNumeroItensMaximoIndexDiferenteZero() {
        final int index = 2;
        final int numeroItens = 3;
        final int tamanhoEsperado = 3;

        final List<Object> itens = generateList(Object::new, 9, 9);

        final List<Object> responsePaginado = paginar(index, numeroItens, itens);

        assertNotNull(responsePaginado);
        assertEquals(tamanhoEsperado, responsePaginado.size());
        assertEquals(itens.get(6), responsePaginado.get(0));
        assertEquals(itens.get(7), responsePaginado.get(1));
        assertEquals(itens.get(8), responsePaginado.get(2));
    }

    @Test
    public void deveRetornarPaginacaoResponseCorretamentePrimeiraPagina() {
        final int index = 0;
        final int numeroItens = 3;
        final int tamanhoEsperado = 2;

        final List<Object> itens = generateList(Object::new, 2, 2);

        final PaginacaoResponse<Object> responsePaginado = paginarComObjetoResponse(index, numeroItens, itens);

        assertNotNull(responsePaginado);
        assertEquals(index, responsePaginado.getIndex());
        assertEquals(numeroItens,responsePaginado.getNumeroItens());
        assertEquals(tamanhoEsperado, responsePaginado.getItens().size());
        assertEquals(itens.get(0), responsePaginado.getItens().get(0));
        assertEquals(itens.get(1), responsePaginado.getItens().get(1));
        assertTrue(responsePaginado.isPrimeiraPagina());
        assertTrue(responsePaginado.isUltimaPagina());
    }

    @Test
    public void deveRetornarPaginacaoResponseCorretamenteUltimaPagina() {
        final int index = 2;
        final int numeroItens = 3;
        final int tamanhoEsperado = 3;

        final List<Object> itens = generateList(Object::new, 9, 9);

        final PaginacaoResponse<Object> responsePaginado = paginarComObjetoResponse(index, numeroItens, itens);

        assertNotNull(responsePaginado);
        assertEquals(index, responsePaginado.getIndex());
        assertEquals(numeroItens,responsePaginado.getNumeroItens());
        assertEquals(tamanhoEsperado, responsePaginado.getItens().size());
        assertEquals(itens.get(6), responsePaginado.getItens().get(0));
        assertEquals(itens.get(7), responsePaginado.getItens().get(1));
        assertEquals(itens.get(8), responsePaginado.getItens().get(2));
        assertFalse(responsePaginado.isPrimeiraPagina());
        assertTrue(responsePaginado.isUltimaPagina());
    }

}
