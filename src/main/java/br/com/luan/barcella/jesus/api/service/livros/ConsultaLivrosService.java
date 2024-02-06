package br.com.luan.barcella.jesus.api.service.livros;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaLivrosBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivrosResponse;
import br.com.luan.barcella.jesus.api.mapper.livros.ConsultaLivrosResponseMapper;
import br.com.luan.barcella.jesus.api.service.rest.integration.BibliaDigitalRestIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaLivrosService {

    private static final ConsultaLivrosResponseMapper CONSULTA_LIVROS_RESPONSE_MAPPER = new ConsultaLivrosResponseMapper();

    private final BibliaDigitalRestIntegration bibliaDigitalRestIntegration;

    public List<ConsultaLivrosResponse> consultarLivros() {
        log.info("Iniciando o proceso de consulta dos livros da bíblia.");

        final List<ConsultaLivrosBibliaDigitalResponse> livros = bibliaDigitalRestIntegration.consultarLivros();

        return CONSULTA_LIVROS_RESPONSE_MAPPER.apply(livros);
    }
}
