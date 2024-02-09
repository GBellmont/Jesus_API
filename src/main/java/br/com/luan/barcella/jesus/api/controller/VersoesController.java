package br.com.luan.barcella.jesus.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoesResponse;
import br.com.luan.barcella.jesus.api.service.versoes.ConsultaVersoesService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/versoes")
public class VersoesController {

    private final ConsultaVersoesService consultaVersoesService;

    @GetMapping
    public ConsultaVersoesResponse consultarVersoes(){
        return consultaVersoesService.consultarVersoes();
    }

}
