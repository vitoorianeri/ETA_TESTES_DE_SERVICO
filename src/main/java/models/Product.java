package models;

public class Product {

    public String _id;
    public String nome;
    public Integer preco;
    public String descricao;
    public Integer quantidade;
    public String administrador;


    public Product(String nome, Integer preco, String descricao, Integer quantidade){
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public Product(String _id){this._id = _id;}

    public void setProductId(String productId) {
        this._id = productId;
    }
}
