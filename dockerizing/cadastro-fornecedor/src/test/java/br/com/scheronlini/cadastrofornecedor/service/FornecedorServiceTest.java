package br.com.scheronlini.cadastrofornecedor.service;

import br.com.scheronlini.cadastrofornecedor.model.Endereco;
import br.com.scheronlini.cadastrofornecedor.model.Fornecedor;
import br.com.scheronlini.cadastrofornecedor.repository.FornecedorRepository;
import br.com.scheronlini.cadastrofornecedor.service.FornecedorService;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.RegraNegocioException;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.ResourceNotFoundException;
import br.com.scheronlini.cadastrofornecedor.service.implementacao.EnderecoServiceImplementacao;
import br.com.scheronlini.cadastrofornecedor.service.implementacao.FornecedorServiceImplementacao;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class FornecedorServiceTest {

    @SpyBean
    FornecedorServiceImplementacao service;
    @MockBean
    FornecedorRepository repository;


    @Test(expected = Test.None.class)
    public void deveBuscarUmFornecedorPorId(){
        var fornecedor = Fornecedor.builder().id(7l).cnpj("16612533000164").celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build();

        Mockito.when(repository.findById(fornecedor.getId())).thenReturn(Optional.of(fornecedor));

        service.findyById(fornecedor.getId());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void naoDeveEncontrarUmFornecedorComIdIneexistente() {

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        service.findyById(Mockito.anyLong());

    }

    @Test(expected = Test.None.class)
    public void deveValidarCnpj(){
        Mockito.when(repository.existsByCnpj(Mockito.anyString())).thenReturn(false);
        service.validaCnpj("53902394000130");
    }


    @Test(expected = RegraNegocioException.class)
    public void naoDeveValidarCnpj(){

        Mockito.when(repository.existsByCnpj(Mockito.anyString())).thenReturn(true);

        service.validaCnpj("53902394000130");
    }

    @Test(expected = Test.None.class)
    public void deveInserirFornecedor(){

        Mockito.doNothing().when(service).validaCnpj(Mockito.anyString());

        var fornecedor = Fornecedor.builder().id(7l).cnpj("16612533000164").celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build();
        var endereco= Endereco.builder()
                .id(7l)
                .bairro("ggg")
                .cep("22222")
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

        Assertions.assertThat(fornecedorSalvo).isNotNull();

    }

    @Test(expected = RegraNegocioException.class)
    public void naoDeveInserirFornecedorcomCnpjCadastrado(){

        String cnpj = "16612533000164";
        var fornecedor = Fornecedor.builder().cnpj(cnpj).celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build();

       Mockito.doThrow(RegraNegocioException.class).when(service).validaCnpj(cnpj);

       service.insert(fornecedor);

       Mockito.verify(repository, Mockito.never()).save(fornecedor);

    }



}
