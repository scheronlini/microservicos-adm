package br.com.scheronlini.endereco.repository;

import br.com.scheronlini.endereco.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository <Endereco, Long> {
}
