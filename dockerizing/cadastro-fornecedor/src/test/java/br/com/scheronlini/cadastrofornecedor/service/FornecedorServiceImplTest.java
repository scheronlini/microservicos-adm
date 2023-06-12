package br.com.scheronlini.cadastrofornecedor.service;

import br.com.scheronlini.cadastrofornecedor.dto.FornecedorDto;
import br.com.scheronlini.cadastrofornecedor.model.Endereco;
import br.com.scheronlini.cadastrofornecedor.model.Fornecedor;
import br.com.scheronlini.cadastrofornecedor.repository.FornecedorRepository;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.DataBaseException;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.RegraNegocioException;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.ResourceNotFoundException;
import br.com.scheronlini.cadastrofornecedor.service.implementacao.FornecedorServiceImplementacao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class FornecedorServiceImplTest {

    @InjectMocks
    private FornecedorServiceImplementacao service;
    @Mock
    private FornecedorRepository repository;


    private Fornecedor fornecedor;

    private FornecedorDto fornecedorDto;

    private Optional<Fornecedor> optionalFornecedor;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startFornecedor();
    }


    @Test(expected = Test.None.class)
    public void deveBuscarUmFornecedorPorId() {
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
    public void deveValidarCnpj() {
        Mockito.when(repository.existsByCnpj(Mockito.anyString())).thenReturn(false);
        service.validaCnpj("53902394000130");
    }


    @Test(expected = RegraNegocioException.class)
    public void naoDeveValidarCnpj() {

        Mockito.when(repository.existsByCnpj(Mockito.anyString())).thenReturn(true);

        service.validaCnpj("53902394000130");
    }

    @Test(expected = Test.None.class)
    public void deveInserirFornecedor(){

        Mockito.doNothing().when(service).validaCnpj(Mockito.anyString());

        var fornecedor = Fornecedor.builder().id(1l).cnpj("16612533000164").celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build();
        var endereco= Endereco.builder()
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

        Assertions.assertThat(fornecedorSalvo).isNotNull();

    }

    @Test(expected = DataBaseException.class)
    public void naoDeveInserirFornecedorcomCnpjCadastrado() {

        Mockito.doNothing().when(service).validaCnpj(Mockito.anyString());

        String cnpj = "16612533000164";
        var fornecedor = Fornecedor.builder().id(1l).cnpj(cnpj).celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build();
        var endereco= Endereco.builder()
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


        var endereco2= Endereco.builder()
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

    private void startFornecedor(){

        fornecedor = Fornecedor.builder().id(7l).cnpj("16612533000164").celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build();

        fornecedorDto = new FornecedorDto(new Fornecedor(7l,"16612533000164",
                "47997277704","casaredo",null,"4734351724",
                "47997277704","wwww.casaredo.com","casaredo@casaredo.com","camila"
                ,"  ","123465","215115",null,
                "alimentos"));
        optionalFornecedor = Optional.of(Fornecedor.builder().id(7l).cnpj("16612533000164").celular("47997277704")
                .nomeFantasia("casaredo").razaoSocial("casa verde").ramoAtividade("madereira").build());
    }

}

