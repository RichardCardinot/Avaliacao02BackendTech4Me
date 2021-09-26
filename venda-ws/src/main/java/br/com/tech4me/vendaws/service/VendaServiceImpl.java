package br.com.tech4me.vendaws.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import br.com.tech4me.vendaws.feignClient.ProdutoFeignClient;
import br.com.tech4me.vendaws.model.Venda;
import br.com.tech4me.vendaws.repository.VendaRepository;
import br.com.tech4me.vendaws.shared.Produto;
import br.com.tech4me.vendaws.shared.VendaDto;
import br.com.tech4me.vendaws.view.model.VendaModeloRequest;

@Service
public class VendaServiceImpl implements VendaService {
    ModelMapper mapper = new ModelMapper();

    @Autowired
    private VendaRepository repositorio;

    @Autowired
    private ProdutoFeignClient produtoMsClient;

    @Override
/*  Efetua a venda. 
    Consulta a produto a partir no método obterVendasPorPeriodo, apura o valor 
    total e suprime a quantidade de itens vendidos do produto(estoque) */
    public Optional<VendaDto> criarVenda(VendaModeloRequest vendaRequest) {   
        String idProduto = vendaRequest.getIdProduto();
        Integer quantidadeVendida = vendaRequest.getQuantidade();
        Produto produtoRecuperado = obterProdutoPorId(idProduto);

        if (produtoRecuperado.getQuantidade() >= quantidadeVendida) {
            Venda vendaParaSalvar = mapper.map(vendaRequest, Venda.class);
            vendaParaSalvar.setValor(produtoRecuperado.getValor() * quantidadeVendida);
            vendaParaSalvar = repositorio.save(vendaParaSalvar);
    
            produtoRecuperado.setQuantidade(produtoRecuperado.getQuantidade() - quantidadeVendida);
            produtoMsClient.atualizarProduto(idProduto, produtoRecuperado);
    
            return Optional.of(mapper.map(vendaParaSalvar, VendaDto.class));          
        }

        return Optional.empty();
    }

    @Override
    public List<VendaDto> obterTodasVendas() {
        List<Venda> vendas = repositorio.findAll();

        return vendas.stream()
            .map(v -> mapper.map(v, VendaDto.class))
            .collect(Collectors.toList());
    }

    @Override
    // Obtém as vendas ocorridas entre os dos dois parâmetros informados
    public List<VendaDto> obterVendasPorPeriodo(@DateTimeFormat(pattern = "yyyy-MM-dd") Calendar dtInicial, @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar dtFinal) {
        List<Venda> vendas = repositorio.findAll();
        dtInicial.add(Calendar.DAY_OF_MONTH, -1);

        return vendas.stream()
        .filter(v -> dtInicial.before(v.getDataVenda()) && dtFinal.after(v.getDataVenda()))
        .map(v -> mapper.map(v, VendaDto.class))
        .collect(Collectors.toList()); 
    }

    @Override
    public Optional<VendaDto> obterVendaPorId(String id) {
        Optional<Venda> venda = repositorio.findById(id);

        if (venda.isPresent()) {
            return Optional.of(mapper.map(venda.get(), VendaDto.class));
        }

        return Optional.empty();
    }

    @Override
    //Obtém o produto a partir da lista recuperada de produto-ws
    public Produto obterProdutoPorId(String id) {
        List<Produto> produtos = produtoMsClient.obterProdutos();

        List<Produto> encontrado = produtos.stream()
            .filter(p -> p.getId().equals(id))
            .map(p -> mapper.map(p, Produto.class))
            .collect(Collectors.toList());

        return encontrado.get(0);
        }
        
    @Override
    public Optional<VendaDto> atualizarVendaPorId(String id, VendaDto vendaDto) {
        Optional<Venda> venda = repositorio.findById(id);
        Venda vendaParaSalvar = mapper.map(vendaDto, Venda.class);

        if (venda.isPresent()) {
            vendaParaSalvar.setId(id);
            vendaParaSalvar = repositorio.save(vendaParaSalvar);
            return Optional.of(mapper.map(vendaParaSalvar, VendaDto.class));
        }
        
        return Optional.empty();
    }

    @Override
    public void removerVenda(String id) {
        repositorio.deleteById(id);      
    }
}




