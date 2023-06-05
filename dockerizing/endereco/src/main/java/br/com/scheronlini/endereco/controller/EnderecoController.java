package br.com.scheronlini.endereco.controller;

import br.com.scheronlini.endereco.dto.EnderecoDto;
import br.com.scheronlini.endereco.model.Endereco;
import br.com.scheronlini.endereco.service.EnderecoService;
import br.com.scheronlini.endereco.service.implementacao.EnderecoServiceImplem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Enderecos endpoint")
@RestController
@RequestMapping("enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoServiceImplem enderecoServiceImplem;

    @Operation(summary = "Busca um endereço por CEP")
    @GetMapping("/cep/{cep}")
    public ResponseEntity<EnderecoDto> findyByCep(@PathVariable String cep) {
       var endereco =  enderecoServiceImplem.findByCep(cep);
       var enderecoDto = new EnderecoDto(endereco);
       return ResponseEntity.ok().body(enderecoDto);
    }

    @Operation(summary = "Busca um endereço por ID")
    @GetMapping("/id/{id}")
    public ResponseEntity<EnderecoDto> findById(@PathVariable Long id) {
        var endereco = enderecoServiceImplem.findById(id);
        var enderecoDto = new EnderecoDto(endereco);
        return ResponseEntity.ok().body(enderecoDto);
    }

    @Operation(summary = "Inseri um endereço no banco de dados")
    @PostMapping
    public ResponseEntity<EnderecoDto> insert(@RequestBody EnderecoDto enderecoDto) {
        var endereco = enderecoServiceImplem.converteDtoParaEndereco(enderecoDto);
        enderecoServiceImplem.insert(endereco);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}").buildAndExpand(endereco.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Altera um endereço por ID no banco de dados")
    @PutMapping("/id/{id}")
    public ResponseEntity<EnderecoDto> update(@PathVariable Long id, @RequestBody EnderecoDto enderecoDto){
        var endereco = enderecoServiceImplem.converteDtoParaEndereco(enderecoDto);
        enderecoServiceImplem.update(id,endereco);
        return ResponseEntity.ok().body(enderecoDto);
    }

    @Operation(summary = "deleta um endereço por ID no banco de dados")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        enderecoServiceImplem.delete(id);
        return ResponseEntity.noContent().build();
    }

}
