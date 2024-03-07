package br.com.luan.barcella.jesus.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivroResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaLivrosResponse;
import br.com.luan.barcella.jesus.api.dto.response.PaginacaoResponse;
import br.com.luan.barcella.jesus.api.service.livros.ConsultaLivroService;
import br.com.luan.barcella.jesus.api.service.livros.ConsultaLivrosService;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/livros")
public class LivrosController {

    private final ConsultaLivrosService consultaLivrosService;
    private final ConsultaLivroService consultaLivroService;

    @GetMapping("/{index}/{numeroItens}")
    public PaginacaoResponse<ConsultaLivrosResponse> consultarLivros(
        @PathVariable(name = "index", required = false) final Integer index,
        @PathVariable(name = "numeroItens", required = false) final Integer numeroItens){

        return consultaLivrosService.consultarLivros(index, numeroItens);
    }

    @GetMapping("/{abreviacao}")
    public ConsultaLivroResponse consultarLivro(
        @PathVariable(name = "abreviacao", required = false) final String abreviacao){

        return consultaLivroService.consultarLivro(abreviacao);
    }
}
