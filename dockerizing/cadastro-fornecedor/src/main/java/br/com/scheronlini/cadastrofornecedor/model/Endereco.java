package br.com.scheronlini.cadastrofornecedor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

        @Id
        private Long id;
        private String cep;
        private String logradouro;
        private String numero;
        private String bairro;
        private String localidade;
        private String uf;
        private String pontodeReferencia;

    }

