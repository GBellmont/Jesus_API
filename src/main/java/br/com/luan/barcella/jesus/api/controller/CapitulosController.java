package br.com.luan.barcella.jesus.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luan.barcella.jesus.api.dto.response.ConsultaCapituloResponse;
import br.com.luan.barcella.jesus.api.service.capitulos.ConsultaCapituloService;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/capitulos")
public class CapitulosController {

    private final ConsultaCapituloService consultaCapituloService;

    @GetMapping("/{versao}/{abreviacao}/{capitulo}")
    public ConsultaCapituloResponse consultarCapitulo(
        @PathVariable(name = "versao", required = false) final String versao,
        @PathVariable(name = "abreviacao", required = false) final String abreviacao,
        @PathVariable(name = "capitulo", required = false) final Integer capitulo){

        return consultaCapituloService.consultarCapitulo(versao, abreviacao, capitulo);
    }
}