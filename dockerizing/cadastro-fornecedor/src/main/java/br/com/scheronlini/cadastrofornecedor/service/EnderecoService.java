package br.com.scheronlini.cadastrofornecedor.service;

import br.com.scheronlini.cadastrofornecedor.dto.EnderecoDto;
import br.com.scheronlini.cadastrofornecedor.model.Endereco;

import java.util.List;

public interface EnderecoService {

    public Endereco findyById(Long id);
    public List<Endereco> findAll( );
    public Endereco insert(Long id);
    public Endereco update(Long id);
    public void updateDataApi (Endereco endereco, Endereco enderecoApi);
    public void delete(Long id);
    public Endereco converteDtoParaEndereco(EnderecoDto enderecoDto);
}
