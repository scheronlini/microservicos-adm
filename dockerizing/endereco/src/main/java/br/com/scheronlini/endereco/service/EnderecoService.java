package br.com.scheronlini.endereco.service;

import br.com.scheronlini.endereco.dto.EnderecoDto;
import br.com.scheronlini.endereco.model.Endereco;

public interface EnderecoService {
    public Endereco findByCep(String cep);
    public Endereco findById(Long id);
    public Endereco insert(Endereco endereco);
    public Endereco update(Long id, Endereco endereco);
    public void updateData(Endereco enderecoBD, Endereco endereco);
    public void updateViaCep (Endereco endereco);
    public void delete(Long id);
    public Endereco converteDtoParaEndereco(EnderecoDto enderecoDto);



}
