package br.com.scheronlini.endereco.dto;

import br.com.scheronlini.endereco.model.Endereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoDto {

    private Long id;

    private String cep;

    private String logradouro;

    private String bairro;

    private String localidade;

    private String uf;

    private String numero;

    private String pontodeReferencia;

    public EnderecoDto(Endereco endereco) {
        id = endereco.getId();
        cep = endereco.getCep();
        logradouro = endereco.getLogradouro();
        bairro = endereco.getBairro();
        localidade = endereco.getLocalidade();
        uf = endereco.getUf();
        numero = endereco.getNumero();
        pontodeReferencia = endereco.getPontodeReferencia();
    }

}
