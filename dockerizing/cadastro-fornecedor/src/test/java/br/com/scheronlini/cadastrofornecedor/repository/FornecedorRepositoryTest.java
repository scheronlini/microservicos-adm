package br.com.scheronlini.cadastrofornecedor.repository;

import br.com.scheronlini.cadastrofornecedor.model.Fornecedor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)


public class FornecedorRepositoryTest {

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveVerificarAExistenciaDeCnpj() {

        var fornecedor = criarFornecedor();
        entityManager.persist(fornecedor);
        var result = fornecedorRepository.existsByCnpj("53902394000130");
        org.junit.jupiter.api.Assertions.assertTrue(result);
    }

    @Test
    public void nãoDeveRetornarFalsoQuandoNaoHouverCnpjCadastrado() {

        var result = fornecedorRepository.existsByCnpj("53902394000130");
        Assertions.assertThat(result).isFalse();
    }

    public static Fornecedor criarFornecedor(){
         return Fornecedor.builder().cnpj("53902394000130").celular("47997277704").nomeFantasia("casaredo")
                 .razaoSocial("casa verde").ramoAtividade("madereira").build();
    }

}
