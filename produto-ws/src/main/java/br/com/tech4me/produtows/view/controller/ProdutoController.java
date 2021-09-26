package br.com.tech4me.produtows.view.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech4me.produtows.service.ProdutoService;
import br.com.tech4me.produtows.shared.ProdutoDto;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin
public class ProdutoController {
    @Autowired
    ProdutoService servico;

    @PostMapping
    public ResponseEntity<ProdutoDto> criarProduto(@RequestBody @Valid ProdutoDto produto) { 
        return new ResponseEntity<>(servico.criarProduto(produto), HttpStatus.CREATED); 
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDto>> obterTodosProdutos() {
      return new ResponseEntity<>(servico.obterTodosProdutos(), HttpStatus.ACCEPTED); 
    } 

    @GetMapping(value = "/porta")
    public String verificarPorta(@Value("${local.server.port}") String porta) {
        return String.format("Microsserviço respondeu a partir da porta %s", porta);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoDto> obterProdutoPorId(@PathVariable String id) {
        Optional<ProdutoDto> produto = servico.obterProdutoPorId(id);

        if (produto.isPresent()) {
            return new ResponseEntity<>(produto.get(), HttpStatus.FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoDto> atualizarProdutoPorId(@PathVariable String id, @RequestBody @Valid ProdutoDto produto) {
        Optional<ProdutoDto> produtoAtualizar = servico.atualizarProdutoPorId(id, produto);

        if (produtoAtualizar.isPresent()) {
            return new ResponseEntity<>(produtoAtualizar.get(), HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);        
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluirProdutoPorId(@PathVariable String id) {
        servico.excluirProdutoPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }    
}
