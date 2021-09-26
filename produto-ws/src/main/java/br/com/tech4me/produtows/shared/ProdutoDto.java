package br.com.tech4me.produtows.shared;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class ProdutoDto {
    private String id;
    @NotEmpty(message = "O nome deve ser preenchido")
    @NotBlank(message = "Nome não pode ter apenas espaços em branco")
    private String nome;
    @Positive(message = "Quantidade deve ser maior que zero")
    private Integer quantidade;
    @Positive(message = "O valor deve ser maior que zero")
    private Double valor;

    //#region
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
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
    //#endregion   
    
}
