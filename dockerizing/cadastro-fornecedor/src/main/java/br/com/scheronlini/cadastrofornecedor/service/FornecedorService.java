package br.com.scheronlini.cadastrofornecedor.service;

import br.com.scheronlini.cadastrofornecedor.dto.FornecedorDto;
import br.com.scheronlini.cadastrofornecedor.model.Endereco;
import br.com.scheronlini.cadastrofornecedor.model.Fornecedor;

import java.util.List;


public interface FornecedorService {

    public Fornecedor findyById(Long id);

    public List<Fornecedor> findAll();

    public Fornecedor insert(Fornecedor fornecedor);

    public Endereco insertEndereco(Fornecedor fornecedor);

    public Fornecedor update(Long id, Fornecedor fornecedor);

    public void updateData(Fornecedor fornecedorBD, Fornecedor fornecedor);

    public void delete(Long id);

    public void validaCnpj(String cnpj);

    public Fornecedor converteDtoParaFornecedor(FornecedorDto fornecedorDto);
}
