package br.com.scheronlini.cadastrofornecedor.service.implementacao;

import br.com.scheronlini.cadastrofornecedor.model.Endereco;
import br.com.scheronlini.cadastrofornecedor.model.Fornecedor;
import br.com.scheronlini.cadastrofornecedor.repository.FornecedorRepository;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.DataBaseException;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.RegraNegocioException;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class FornecedorServiceImplementacaoTest {

    @SpyBean
    private FornecedorServiceImplementacao service;
    @MockBean
    private FornecedorRepository repository;

    //Teste FindById
    @Test(expected = Test.None.class)
    public void deveBuscarUmFornecedorPorId() {

        var fornecedor = Fornecedor.builder().id(1l).cnpj("16612533000164").celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build();

        var optionalFornecedor = Optional.of(fornecedor);

        repository.save(fornecedor);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(optionalFornecedor);

        Fornecedor response = service.findyById(fornecedor.getId());

        assertEquals(Fornecedor.class, response.getClass());
        assertNotNull(response);
        assertEquals("16612533000164", response.getCnpj());
        assertEquals("casaredo", response.getNomeFantasia());
    }
    //Teste FindById
    @Test(expected = ResourceNotFoundException.class)
    public void naoDeveEncontrarUmFornecedorComIdIneexistente() {

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        service.findyById(Mockito.anyLong());

    }

    //Teste FindAll
    @Test(expected = Test.None.class)
    public void deveBuscarTodosOsFornecedores() {
        var fornecedor = Fornecedor.builder().id(1l).cnpj("16612533000164").celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build();

        List<Fornecedor> listaFornecedores = List.of(fornecedor);
        Mockito.when(repository.findAll()).thenReturn(listaFornecedores);

        service.findAll();

        org.junit.jupiter.api.Assertions.assertNotNull(listaFornecedores);
        org.junit.jupiter.api.Assertions.assertEquals(1, listaFornecedores.size());
        org.junit.jupiter.api.Assertions.assertEquals(Fornecedor.class, listaFornecedores.get(0).getClass());
    }

    //Teste insert
    @Test(expected = Test.None.class)
    public void deveInserirFornecedor() {

        var fornecedor = Fornecedor.builder().id(1l).cnpj("16612533000164").celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build();
        var endereco = Endereco.builder()
                .id(1l)
                .bairro("ggg")
                .cep("89224021")
                .localidade("8888")
                .logradouro("fgfd")
                .pontodeReferencia("gdgdfg")
                .numero("dfgdg")
                .uf("fd")
                .build();

        Mockito.when(repository.save(Mockito.any(Fornecedor.class))).thenReturn(fornecedor);
        Mockito.when(repository.findById(fornecedor.getId())).thenReturn(Optional.of(fornecedor));
        Mockito.when(service.insertEndereco(fornecedor)).thenReturn(endereco);

        Fornecedor fornecedorSalvo = service.insert(fornecedor);

        org.assertj.core.api.Assertions.assertThat(fornecedorSalvo).isNotNull();

    }
    //Teste insert
    @Test(expected = DataBaseException.class)
    public void naoDeveInserirFornecedorcomCnpjCadastrado() {

        Mockito.when(repository.existsByCnpj(Mockito.anyString())).thenReturn(false);

        String cnpj = "16612533000164";
        var fornecedor = Fornecedor.builder().id(1l).cnpj(cnpj).celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build();
        var endereco = Endereco.builder()
                .id(1l)
                .bairro("ggg")
                .cep("89224021")
                .localidade("8888")
                .logradouro("fgfd")
                .pontodeReferencia("gdgdfg")
                .numero("dfgdg")
                .uf("fd")
                .build();

        Mockito.when(repository.save(Mockito.any(Fornecedor.class))).thenReturn(fornecedor);
        Mockito.when(repository.findById(fornecedor.getId())).thenReturn(Optional.of(fornecedor));
        Mockito.when(service.insertEndereco(fornecedor)).thenReturn(endereco);

        Fornecedor fornecedorSalvo = service.insert(fornecedor);

        Mockito.doThrow(RegraNegocioException.class).when(service).validaCnpj(cnpj);

        var fornecedor2 = Fornecedor.builder().id(1l).cnpj(cnpj).celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build();

        var endereco2 = Endereco.builder()
                .id(1l)
                .bairro("ggg")
                .cep("89224021")
                .localidade("8888")
                .logradouro("fgfd")
                .pontodeReferencia("gdgdfg")
                .numero("dfgdg")
                .uf("fd")
                .build();

        Mockito.when(repository.save(Mockito.any(Fornecedor.class))).thenReturn(fornecedor2);
        Mockito.when(repository.findById(fornecedor.getId())).thenReturn(Optional.of(fornecedor2));
        Mockito.when(service.insertEndereco(fornecedor2)).thenReturn(endereco2);

        Fornecedor fornecedorSalvo2 = service.insert(fornecedor2);

        Mockito.verify(repository, Mockito.never()).save(fornecedorSalvo2);

    }

    //Teste validaCNPJ
    @Test(expected = Test.None.class)
    public void deveValidarCnpj() {
        Mockito.when(repository.existsByCnpj(Mockito.anyString())).thenReturn(false);
        service.validaCnpj("16612533000164");
    }

    //Teste validaCNPJ
    @Test(expected = RegraNegocioException.class)
    public void naoDeveValidarCnpj() {

        Mockito.when(repository.existsByCnpj(Mockito.anyString())).thenReturn(true);

        service.validaCnpj("16612533000164");
    }




}