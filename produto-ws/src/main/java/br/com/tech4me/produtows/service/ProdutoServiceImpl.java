package br.com.tech4me.produtows.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tech4me.produtows.model.Produto;
import br.com.tech4me.produtows.repository.ProdutoRepository;
import br.com.tech4me.produtows.shared.ProdutoDto;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    ModelMapper mapper = new ModelMapper();

    @Autowired
    ProdutoRepository repositorio;

    @Override
    public ProdutoDto criarProduto(ProdutoDto produto) {
        return mapper.map(repositorio.save(mapper.map(produto, Produto.class)), ProdutoDto.class);  
    }

    @Override
    public List<ProdutoDto> obterTodosProdutos() {
        List<Produto> produtos = repositorio.findAll();

        return produtos.stream()
            .map(p -> mapper.map(p, ProdutoDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<ProdutoDto> obterProdutoPorId(String id) {
        Optional<Produto> produto = repositorio.findById(id);

        if (produto.isPresent()) {
            return Optional.of(mapper.map(produto.get(), ProdutoDto.class));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProdutoDto> atualizarProdutoPorId(String id, ProdutoDto produtoDto) {
        Optional<Produto> produto = repositorio.findById(id);
        Produto produtoParaSalvar = mapper.map(produtoDto, Produto.class);

        if (produto.isPresent()) {
            produtoParaSalvar.setId(id);
            produtoParaSalvar = repositorio.save(produtoParaSalvar);
            return Optional.of(mapper.map(produtoParaSalvar, ProdutoDto.class));
        }
        
        return Optional.empty();
    }

    @Override
    public void excluirProdutoPorId(String id) {
        repositorio.deleteById(id);    
    }
    
}
