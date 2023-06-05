package br.com.scheronlini.cadastrofornecedor.service.implementacao;

import br.com.scheronlini.cadastrofornecedor.dto.FornecedorDto;
import br.com.scheronlini.cadastrofornecedor.model.Endereco;
import br.com.scheronlini.cadastrofornecedor.model.Fornecedor;
import br.com.scheronlini.cadastrofornecedor.repository.FornecedorRepository;
import br.com.scheronlini.cadastrofornecedor.service.FornecedorService;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.DataBaseException;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.RegraNegocioException;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FornecedorServiceImplementacao implements FornecedorService {

    private final FornecedorRepository repository;
    private final EnderecoServiceImplementacao enderecoService;


    @Override
    public Fornecedor findyById(Long id) {
        Optional<Fornecedor> fornecedor = repository.findById(id);
        return fornecedor.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<Fornecedor> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Fornecedor insert(Fornecedor fornecedor) {
        try {
            validaCnpj(fornecedor.getCnpj());
            repository.save(fornecedor);
            var fornecedor1 = repository.findById(fornecedor.getId());
            var endereco = insertEndereco(fornecedor1.get());
            fornecedor1.get().setEndereco(endereco);
            return repository.save(fornecedor1.get());

        } catch (RegraNegocioException e){
        throw new DataBaseException(e.getMessage());
    }
    }

    @Override
    public Endereco insertEndereco(Fornecedor fornecedor) {
        return enderecoService.insert(fornecedor.getId());
    }

    public Fornecedor update(Long id, Fornecedor fornecedor) {
        try {
            var fornecedorBD = repository.getReferenceById(id);
            updateData(fornecedorBD, fornecedor);
            return repository.save(fornecedorBD);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public void updateData(Fornecedor fornecedorBD, Fornecedor fornecedor) {
        fornecedorBD.setCelular(fornecedor.getCelular());
        fornecedorBD.setContato(fornecedor.getContato());
        fornecedorBD.setDataAbertura(fornecedor.getDataAbertura());
        fornecedorBD.setEmail(fornecedor.getEmail());
        enderecoService.update(fornecedorBD.getId());
        fornecedorBD.setInscricaoEstadual(fornecedor.getInscricaoEstadual());
        fornecedorBD.setInscricaoMunicipal(fornecedor.getInscricaoMunicipal());
        fornecedorBD.setNomeFantasia(fornecedor.getNomeFantasia());
        fornecedorBD.setObservacao(fornecedor.getObservacao());
        fornecedorBD.setRamoAtividade(fornecedor.getRamoAtividade());
        fornecedorBD.setRazaoSocial(fornecedor.getRazaoSocial());
        fornecedorBD.setSite(fornecedor.getSite());
        fornecedorBD.setTelefone(fornecedor.getTelefone());
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (
                EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (
                DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public void validaCnpj(String cnpj) {
        var existsCnpj = repository.existsByCnpj(cnpj);
        if (existsCnpj) {
            throw new RegraNegocioException("CNPJ já cadastrado");
        }
    }

    @Override
    public Fornecedor converteDtoParaFornecedor(FornecedorDto fornecedorDto) {
        var fornecedor = Fornecedor.builder().dataAbertura(fornecedorDto.getDataAbertura())
                .celular(fornecedorDto.getCelular())
                .cnpj(fornecedorDto.getCnpj()).contato(fornecedorDto.getContato()).email(fornecedorDto.getEmail())
                .endereco(fornecedorDto.getEndereco()).id(fornecedorDto.getId())
                .inscricaoEstadual(fornecedorDto.getInscricaoEstadual())
                .inscricaoMunicipal(fornecedorDto.getInscricaoMunicipal())
                .nomeFantasia(fornecedorDto.getNomeFantasia()).observacao(fornecedorDto.getObservacao())
                .ramoAtividade(fornecedorDto.getRamoAtividade()).razaoSocial(fornecedorDto.getRazaoSocial())
                .site(fornecedorDto.getSite()).telefone(fornecedorDto.getTelefone()).build();
        return fornecedor;
    }
}
