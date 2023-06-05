package br.com.scheronlini.cadastrofornecedor.repository;

import br.com.scheronlini.cadastrofornecedor.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    boolean existsByCnpj(String cnpj);
}
