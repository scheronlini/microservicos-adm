package br.com.scheronlini.cadastrofornecedor.dto;

import br.com.scheronlini.cadastrofornecedor.model.Endereco;
import br.com.scheronlini.cadastrofornecedor.model.Fornecedor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FornecedorDto {

    private Long id;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private Endereco endereco;
    private String telefone;
    private String celular;
    private String site;
    private String email;
    private String contato;
    private String observacao;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private Date dataAbertura;
    private String ramoAtividade;

    public FornecedorDto(Fornecedor fornecedor) {

        id = fornecedor.getId();
        cnpj = fornecedor.getCnpj();
        razaoSocial = fornecedor.getRazaoSocial();
        nomeFantasia = fornecedor.getNomeFantasia();
        endereco = fornecedor.getEndereco();
        telefone = fornecedor.getTelefone();
        celular = fornecedor.getCelular();
        site = fornecedor.getSite();
        email = fornecedor.getEmail();
        contato = fornecedor.getContato();
        observacao = fornecedor.getObservacao();
        inscricaoEstadual = fornecedor.getInscricaoEstadual();
        inscricaoMunicipal = fornecedor.getInscricaoMunicipal();
        dataAbertura = fornecedor.getDataAbertura();
        ramoAtividade = fornecedor.getRamoAtividade();
    }


}