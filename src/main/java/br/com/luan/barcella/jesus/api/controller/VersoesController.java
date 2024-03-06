package br.com.luan.barcella.jesus.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luan.barcella.jesus.api.dto.response.VersaoResponse;
import br.com.luan.barcella.jesus.api.dto.response.PaginacaoResponse;
import br.com.luan.barcella.jesus.api.service.versoes.ConsultaVersoesService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/versoes")
public class VersoesController {

    private final ConsultaVersoesService consultaVersoesService;

    @GetMapping("/{index}/{numeroItens}")
    public PaginacaoResponse<VersaoResponse> consultarVersoes(
        @PathVariable(name = "index", required = false) final Integer index,
        @PathVariable(name = "numeroItens", required = false) final Integer numeroItens){

        return consultaVersoesService.consultarVersoes(index, numeroItens);
    }

}
