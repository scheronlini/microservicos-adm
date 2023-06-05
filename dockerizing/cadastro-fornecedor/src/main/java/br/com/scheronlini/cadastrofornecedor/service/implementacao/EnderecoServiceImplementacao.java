package br.com.scheronlini.cadastrofornecedor.service.implementacao;

import br.com.scheronlini.cadastrofornecedor.dto.EnderecoDto;
import br.com.scheronlini.cadastrofornecedor.model.Endereco;
import br.com.scheronlini.cadastrofornecedor.repository.EnderecoRepository;
import br.com.scheronlini.cadastrofornecedor.service.EnderecoService;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.DataBaseException;
import br.com.scheronlini.cadastrofornecedor.service.exceptions.ResourceNotFoundException;
import br.com.scheronlini.cadastrofornecedor.service.proxy.EnderecoProxy;
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
public class EnderecoServiceImplementacao implements EnderecoService {

    private final EnderecoRepository repository;

    private final EnderecoProxy enderecoProxy;

    @Override
    public Endereco findyById(Long id) {
        Optional<Endereco> endereco = repository.findById(id);
        return endereco.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<Endereco> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Endereco insert(Long id) {
        var endereco = new Endereco();
        var apiEndereco = enderecoProxy.getEndereco(id);
        endereco.setId(id);
        updateDataApi(endereco, apiEndereco);
        return repository.save(endereco);
    }

    @Override
    public Endereco update(Long id) {
        try {
            var apiEndereco = enderecoProxy.getEndereco(id);
            var enderecoBD = repository.getReferenceById(id);
            updateDataApi(enderecoBD, apiEndereco);
            return repository.save(enderecoBD);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public void updateDataApi(Endereco endereco, Endereco enderecoAPI) {
        endereco.setBairro(enderecoAPI.getBairro());
        endereco.setCep(enderecoAPI.getCep());
        endereco.setLocalidade(enderecoAPI.getLocalidade());
        endereco.setLogradouro(enderecoAPI.getLogradouro());
        endereco.setNumero(enderecoAPI.getNumero());
        endereco.setPontodeReferencia(enderecoAPI.getPontodeReferencia());
        endereco.setUf(enderecoAPI.getUf());
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public Endereco converteDtoParaEndereco(EnderecoDto enderecoDto) {
        var endereco = Endereco.builder().bairro(enderecoDto.getBairro()).cep(enderecoDto.getCep())
                .id(enderecoDto.getId()).localidade(enderecoDto.getLocalidade()).logradouro(enderecoDto.getLogradouro())
                .numero(enderecoDto.getNumero()).pontodeReferencia(enderecoDto.getPontodeReferencia())
                .uf(enderecoDto.getUf()).build();
        return endereco;
    }
}
