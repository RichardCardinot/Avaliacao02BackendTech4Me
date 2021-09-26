package br.com.tech4me.vendaws.view.model;

import java.util.Calendar;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

public class VendaModeloRequest {
    private String id;
    @NotEmpty(message = "O id do produto deve ser preenchido")
    @NotBlank(message = "O id do produto não pode ter apenas espaços em branco")
    private String idProduto;
    @Positive(message = "A quantidade deve ser maior que zero")
    private Integer quantidade;
    private @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar dataVenda;

    //#region
    public String getIdProduto() {
        return idProduto;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public Calendar getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(Calendar dataVenda) {
        this.dataVenda = dataVenda;
    }
    //#endregion   
}

