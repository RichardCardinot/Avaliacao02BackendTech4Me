package br.com.tech4me.vendaws.feignClient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.tech4me.vendaws.shared.Produto;

@FeignClient(name = "produto-ws", fallback = ProdutoFeignClientFallback.class)
public interface ProdutoFeignClient {
    @PutMapping(path = "/api/produto/{id}")
    Produto atualizarProduto(@PathVariable String id, @RequestBody Produto produto); 

    @GetMapping(path = "/api/produto/")
    List<Produto> obterProdutos();   
}

@Component
class ProdutoFeignClientFallback implements ProdutoFeignClient {
    
    @Override
    // Somente consegui obter o produto a partir da lista de todos os produtos.
    public List<Produto> obterProdutos() {     
        return new ArrayList<>();
    }

    @Override
    public Produto atualizarProduto(String id, Produto produto) {
        return new Produto();
    }
}
    
