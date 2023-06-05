package br.com.scheronlini.cadastrofornecedor.service.proxy;

import br.com.scheronlini.cadastrofornecedor.model.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "enderecos" )
public interface EnderecoProxy {

    @GetMapping(value = "/enderecos/id/{id}")
    public Endereco getEndereco(@PathVariable("id") Long id);
}
