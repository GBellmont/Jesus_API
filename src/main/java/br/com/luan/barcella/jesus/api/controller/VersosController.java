package br.com.luan.barcella.jesus.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoAleatorioResponse;
import br.com.luan.barcella.jesus.api.service.versos.ConsultaVersoAleatorioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/versos")
public class VersosController {

    private final ConsultaVersoAleatorioService consultaVersoAleatorioService;

    @GetMapping("/{versao}/aleatorio")
    public ConsultaVersoAleatorioResponse consultarVersoAleatorio(
        @PathVariable(name = "versao", required = false) final String versao){

        return consultaVersoAleatorioService.consultarVersoAleatorio(versao);
    }
}
