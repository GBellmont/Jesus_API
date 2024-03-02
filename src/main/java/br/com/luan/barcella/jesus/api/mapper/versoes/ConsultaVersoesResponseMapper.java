package br.com.luan.barcella.jesus.api.mapper.versoes;

import static br.com.luan.barcella.jesus.api.domain.VersaoBiblia.fromCodigoBibliaDigital;
import static java.util.Objects.isNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.luan.barcella.jesus.api.domain.VersaoBiblia;
import br.com.luan.barcella.jesus.api.dto.external.biblia.digital.response.ConsultaVersoesBibliaDigitalResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoesResponse;
import br.com.luan.barcella.jesus.api.dto.response.ConsultaVersoesResponse.VersaoResponse;

public class ConsultaVersoesResponseMapper implements Function<List<ConsultaVersoesBibliaDigitalResponse>, ConsultaVersoesResponse> {

    @Override
    public ConsultaVersoesResponse apply(final List<ConsultaVersoesBibliaDigitalResponse> consultaVersoesBibliaDigitalResponseList) {

        if (isNull(consultaVersoesBibliaDigitalResponseList) || consultaVersoesBibliaDigitalResponseList.isEmpty()) {
            return null;
        }

        return ConsultaVersoesResponse.builder()
            .versoes(buildVersoes(consultaVersoesBibliaDigitalResponseList))
            .build();
    }

    private List<VersaoResponse> buildVersoes(final List<ConsultaVersoesBibliaDigitalResponse> consultaVersoesBibliaDigitalResponseList) {
        return consultaVersoesBibliaDigitalResponseList.stream()
            .filter(Objects::nonNull)
            .map(versao -> {
                final VersaoBiblia versaoBiblia = fromCodigoBibliaDigital(versao.getCodigoVersao());

                return VersaoResponse.builder()
                    .codigoVersao(versao.getCodigoVersao())
                    .numeroVersos(versao.getNumeroVersos())
                    .nomeCompleto(versaoBiblia.getNome())
                    .descricao(versaoBiblia.getDescricao())
                    .build();
            })
            .collect(Collectors.toList());
    }
}
