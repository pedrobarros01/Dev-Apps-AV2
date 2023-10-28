package com.example.moviecima.Models;

public class Filme {
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "nome='" + nome + '\'' +
                ", ano='" + ano + '\'' +
                "},";
    }

    public Filme(String nome, String ano) {
        this.nome = nome;
        this.ano = ano;
    }

    private String nome;
    private  String ano;
}
