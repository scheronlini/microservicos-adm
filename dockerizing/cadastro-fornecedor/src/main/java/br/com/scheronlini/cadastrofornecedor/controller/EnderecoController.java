package br.com.scheronlini.cadastrofornecedor.controller;

import br.com.scheronlini.cadastrofornecedor.dto.EnderecoDto;
import br.com.scheronlini.cadastrofornecedor.model.Endereco;
import br.com.scheronlini.cadastrofornecedor.service.implementacao.EnderecoServiceImplementacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoServiceImplementacao enderecoService;

    @GetMapping
    public ResponseEntity<List<EnderecoDto>> findAll() {
        List<Endereco> enderecos = enderecoService.findAll();
        List<EnderecoDto> enderecoDtos = enderecos.stream().map(x -> new EnderecoDto(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(enderecoDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDto> findById(@PathVariable Long id) {
        var endereco = enderecoService.findyById(id);
        var enderecoDto = new EnderecoDto(endereco);
        return ResponseEntity.ok().body(enderecoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDto> update(@PathVariable Long id, @RequestBody EnderecoDto enderecoDto) {
        var endereco = enderecoService.converteDtoParaEndereco(enderecoDto);
       endereco = enderecoService.update(id);
        return ResponseEntity.ok().body(enderecoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
