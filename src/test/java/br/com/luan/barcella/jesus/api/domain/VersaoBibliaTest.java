package br.com.luan.barcella.jesus.api.domain;

import static br.com.luan.barcella.jesus.api.domain.VersaoBiblia.fromCodigoBibliaDigital;
import static br.com.luan.barcella.jesus.api.utils.RandomCollectionUtils.pickRandom;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;

public class VersaoBibliaTest {

    @Test
    public void deveRetornarVersaoBibliaCorretamente() {
        final VersaoBiblia versaoBiblia = pickRandom(VersaoBiblia.values());

        final VersaoBiblia versaoResponse = fromCodigoBibliaDigital(versaoBiblia.getCodigoBibliaDigital());

        assertNotNull(versaoResponse);
        assertEquals(versaoBiblia.getNome(), versaoResponse.getNome());
        assertEquals(versaoBiblia.getDescricao(), versaoResponse.getDescricao());
    }

    @Test
    public void deveRetornarNullQuandoCodigoForInvalido() {
        final VersaoBiblia versaoResponse = fromCodigoBibliaDigital(randomAlphanumeric(10));

        assertNull(versaoResponse);
    }

}
