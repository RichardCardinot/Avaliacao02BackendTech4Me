package br.com.tech4me.vendaws.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import br.com.tech4me.vendaws.shared.Produto;
import br.com.tech4me.vendaws.shared.VendaDto;
import br.com.tech4me.vendaws.view.model.VendaModeloRequest;

public interface VendaService {
    Optional<VendaDto> criarVenda(VendaModeloRequest vendaRequest);
    List<VendaDto> obterTodasVendas();
    List<VendaDto> obterVendasPorPeriodo(Calendar dtInicial, Calendar dtFinal);
    Optional<VendaDto> obterVendaPorId(String id);
    Produto obterProdutoPorId(String id);
    Optional<VendaDto> atualizarVendaPorId(String id, VendaDto venda);    
    void removerVenda(String id);  
}
