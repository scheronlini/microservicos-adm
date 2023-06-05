package br.com.scheronlini.cadastrofornecedor.dto;

import br.com.scheronlini.cadastrofornecedor.model.Endereco;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
@Getter
@Setter
@NoArgsConstructor
public class EnderecoDto {
    private Long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String localidade;
    private String uf;
    private String pontodeReferencia;

    public EnderecoDto(Endereco endereco){
        id = endereco.getId();
        cep = endereco.getCep();
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();
        bairro = endereco.getBairro();
        localidade = endereco.getLocalidade();
        uf = endereco.getUf();
        pontodeReferencia = endereco.getPontodeReferencia();
    }

}
