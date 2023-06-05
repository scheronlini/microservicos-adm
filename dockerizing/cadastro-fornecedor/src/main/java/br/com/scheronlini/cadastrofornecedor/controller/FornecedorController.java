package br.com.scheronlini.cadastrofornecedor.controller;

import br.com.scheronlini.cadastrofornecedor.dto.FornecedorDto;
import br.com.scheronlini.cadastrofornecedor.model.Fornecedor;
import br.com.scheronlini.cadastrofornecedor.service.implementacao.FornecedorServiceImplementacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Fornecedores endpoint")
@RestController
@RequestMapping(value = "fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorServiceImplementacao fornecedorService;

    @Operation(summary = "Busca todos os fornecedores")
    @GetMapping
    public ResponseEntity <List<FornecedorDto>> findAll(){
        List<Fornecedor> fornecedores = fornecedorService.findAll();
        List<FornecedorDto> fornecedorDtos = fornecedores.stream().map(x -> new FornecedorDto(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(fornecedorDtos);
    }

    @Operation(summary = "Busca um fornecedor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDto> findById(@PathVariable Long id){
        var fornecedor = fornecedorService.findyById(id);
        return ResponseEntity.ok().body(new FornecedorDto(fornecedor)) ;
    }

    @Operation(summary = "Inseri um fornecedor no Banco de Dados")
    @PostMapping
    public ResponseEntity<FornecedorDto> insert(@RequestBody FornecedorDto fornecedorDto){
        var fornecedor = fornecedorService.converteDtoParaFornecedor(fornecedorDto);
        fornecedorService.insert(fornecedor);
        fornecedorService.insertEndereco(fornecedor);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fornecedor.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Altera um fornecedor por ID")
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorDto> update(@PathVariable Long id, @RequestBody FornecedorDto fornecedorDto){
        var fornecedor = fornecedorService.converteDtoParaFornecedor(fornecedorDto);
        fornecedor = fornecedorService.update(id,fornecedor);
        return ResponseEntity.ok().body(fornecedorDto);
    }

    @Operation(summary = "Deleta um fornecedor por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        fornecedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
