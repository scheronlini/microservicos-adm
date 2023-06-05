package br.com.scheronlini.endereco.service.implementacao;

import br.com.scheronlini.endereco.dto.EnderecoDto;
import br.com.scheronlini.endereco.model.Endereco;
import br.com.scheronlini.endereco.repository.EnderecoRepository;
import br.com.scheronlini.endereco.service.EnderecoService;
import br.com.scheronlini.endereco.service.ViaCepService;
import br.com.scheronlini.endereco.service.exceptions.DataBaseException;
import br.com.scheronlini.endereco.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImplem implements EnderecoService {

    private final ViaCepService viaCepService;

    private final EnderecoRepository enderecoRepository;

    @Override
    public Endereco findByCep(String cep) {
        var endereco = viaCepService.consultarCep(cep);
        return endereco;
    }

    @Override
    public Endereco findById(Long id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return endereco.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Endereco insert(Endereco endereco) {
        endereco.setNumero(endereco.getNumero());
        endereco.setPontodeReferencia(endereco.getPontodeReferencia());
        updateViaCep(endereco);
        return enderecoRepository.save(endereco);
    }

    @Override
    public Endereco update(Long id, Endereco endereco) {
       try {
           Endereco enderecoBD = enderecoRepository.getReferenceById(id);
           updateData(enderecoBD, endereco);
           return enderecoRepository.save(enderecoBD);
       } catch (EntityNotFoundException e){
           throw new ResourceNotFoundException(id);
       }
    }

    @Override
    public void updateData(Endereco enderecoBD, Endereco endereco) {
        enderecoBD.setCep(endereco.getCep());
        enderecoBD.setNumero(endereco.getNumero());
        enderecoBD.setPontodeReferencia(endereco.getPontodeReferencia());
        updateViaCep(enderecoBD);
    }

    @Override
    public void updateViaCep(Endereco endereco) {
        String cep = endereco.getCep();
        Endereco enderecoViaCep = viaCepService.consultarCep(cep);
        endereco.setBairro(enderecoViaCep.getBairro());
        endereco.setLocalidade(enderecoViaCep.getLocalidade());
        endereco.setLogradouro(enderecoViaCep.getLogradouro());
        endereco.setUf(enderecoViaCep.getUf());
    }

    @Override
    public void delete(Long id) {
       try {
           enderecoRepository.deleteById(id);
       } catch (EmptyResultDataAccessException e){
           throw new ResourceNotFoundException(id);
       }catch (DataIntegrityViolationException e){
           throw new DataBaseException(e.getMessage());
       }
    }

    @Override
    public Endereco converteDtoParaEndereco(EnderecoDto enderecoDto) {
        var endereco = Endereco.builder().bairro(enderecoDto.getBairro()).cep(enderecoDto.getCep()).id(enderecoDto.getId())
                .localidade(enderecoDto.getLocalidade()).logradouro(enderecoDto.getLogradouro()).numero(enderecoDto.getNumero())
                .pontodeReferencia(enderecoDto.getPontodeReferencia()).uf(enderecoDto.getUf()).build();

        return endereco;
    }
}
