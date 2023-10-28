package com.example.moviecima.Models;

import java.util.List;

public class Playlist {
    public String getRA() {
        return RA;
    }

    public void setRA(String RA) {
        this.RA = RA;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }
    public String printarFilmes(){
        String filmess = "";
        for(Filme film: getFilmes()){
            filmess += film + "\n";
        }
        return  filmess;
    }

    private String RA;
   private List<Filme> filmes;
   private int curtidas;

    @Override
    public String toString() {
        String filmess = "";
        for(Filme film: getFilmes()){
            filmess += film + "\n";
        }
        return "Playlist{" +
                "RA='" + RA + '\'' +
                ", filmes=" + filmess +
                ", curtidas=" + curtidas +
                '}';
    }
}
