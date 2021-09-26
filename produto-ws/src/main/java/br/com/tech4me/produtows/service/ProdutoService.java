package br.com.tech4me.produtows.service;

import java.util.List;
import java.util.Optional;

import br.com.tech4me.produtows.shared.ProdutoDto;

public interface ProdutoService {
    ProdutoDto criarProduto(ProdutoDto produto);
    List<ProdutoDto> obterTodosProdutos();
    Optional<ProdutoDto> obterProdutoPorId(String id);
    Optional<ProdutoDto> atualizarProdutoPorId(String id, ProdutoDto produto);
    void excluirProdutoPorId(String id);
}
