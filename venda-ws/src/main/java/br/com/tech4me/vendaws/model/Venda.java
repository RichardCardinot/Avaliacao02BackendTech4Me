package br.com.tech4me.vendaws.model;

import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Venda {
    @Id
    private String id;
    private String idProduto;
    private Integer quantidade;
    private Double valor;
    private Calendar dataVenda;

    //#region
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIdProduto() {
        return idProduto;
    }
    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public Calendar getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(Calendar dataVenda) {
        this.dataVenda = dataVenda;
    }
    //#endregion  
}
