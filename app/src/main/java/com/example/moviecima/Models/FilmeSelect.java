package com.example.moviecima.Models;

public class FilmeSelect {
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public FilmeSelect(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    private String id;
    private String nome;

    @Override
    public String toString() {
        return getNome();
    }
}
