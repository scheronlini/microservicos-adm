package br.com.scheronlini.cadastrofornecedor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity(name = "fornecedor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CNPJ
    @NotBlank(message = "Campo obrigatório")
    @Column(name = "cnpj")
    private String cnpj;

    @NotBlank(message = "Campo obrigatório")
    @Column(name = "razao_Social")
    private String razaoSocial;

    @NotBlank(message = "Campo obrigatório")
    @Column(name = "nome_Fantasia")
    private String nomeFantasia;
    @OneToOne
    private Endereco endereco;

    @Column(name = "telefone")
    private String telefone;
    @Column(name = "celular")
    private String celular;
    @Column(name = "site")
    private String site;

    @Email
    @Column(name = "email")
    private String email;
    @Column(name = "contato")
    private String contato;
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "inscricao_Estadual")
    private String inscricaoEstadual;
    @Column(name = "inscricao_Municipal")
    private String inscricaoMunicipal;

    @Column(name = "data_Abertura")
    @Temporal(value = TemporalType.DATE)
    private Date dataAbertura;

    @NotBlank(message = "Campo obrigatório")
    @Column(name = "ramo_Atividade")
    private String ramoAtividade;
}