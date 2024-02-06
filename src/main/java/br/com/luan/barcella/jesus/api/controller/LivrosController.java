package br.com.luan.barcella.jesus.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivrosResponse;
import br.com.luan.barcella.jesus.api.service.livros.ConsultaLivrosService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/livros")
public class LivrosController {

    private final ConsultaLivrosService consultaLivrosService;

    @GetMapping
    public List<ConsultaLivrosResponse> consultarLivros() {
        return consultaLivrosService.consultarLivros();
    }
}
