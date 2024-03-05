package br.com.luan.barcella.jesus.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luan.barcella.jesus.api.dto.request.ConsultaVersosPorPalavraRequest;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoAleatorioResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersosPorPalavraResponse;
import br.com.luan.barcella.jesus.api.dto.response.PaginacaoResponse;
import br.com.luan.barcella.jesus.api.service.versos.ConsultaVersoAleatorioService;
import br.com.luan.barcella.jesus.api.service.versos.ConsultaVersosPorPalavraService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/versos")
public class VersosController {

    private final ConsultaVersoAleatorioService consultaVersoAleatorioService;
    private final ConsultaVersosPorPalavraService consultaVersosPorPalavraService;

    @GetMapping("/{versao}/aleatorio")
    public ConsultaVersoAleatorioResponse consultarVersoAleatorio(
        @PathVariable(name = "versao", required = false) final String versao){

        return consultaVersoAleatorioService.consultarVersoAleatorio(versao);
    }

    @PostMapping("/pesquisa/{index}/{numeroItens}")
    public PaginacaoResponse<ConsultaVersosPorPalavraResponse> consultarVersosPorPalavra(
        @RequestBody final ConsultaVersosPorPalavraRequest request,
        @PathVariable(name = "index", required = false) final Integer index,
        @PathVariable(name = "numeroItens", required = false) final Integer numeroItens) {

        final ConsultaVersosPorPalavraRequest requestFull = request.toBuilder()
            .index(index)
            .numeroItens(numeroItens)
            .build();

        return consultaVersosPorPalavraService.consultarVersosPorPalavra(requestFull);
    }
}
