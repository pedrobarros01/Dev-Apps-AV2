package com.example.moviecima.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecima.Models.Filme;
import com.example.moviecima.Models.Playlist;
import com.example.moviecima.R;

import java.util.List;

public class AdapterPlaylist extends RecyclerView.Adapter<AdapterPlaylist.MyViewHolder> {

    public AdapterPlaylist(List<Playlist> listPlaylists) {
        this.listPlaylists = listPlaylists;
    }

    private List<Playlist> listPlaylists;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_playlist_lista, parent ,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Playlist play = listPlaylists.get(position);
        holder.txtTitulo.setText("Playlist do RA: "+play.getRA());
        holder.txtCurtidas.setText("O numero de curtidas é: "+play.getCurtidas());
        String txtFilmes = "";
        for(Filme filme: play.getFilmes()){
            txtFilmes += filme + "\n";
        }
        holder.txtFilmes.setText(txtFilmes);

    }

    @Override
    public int getItemCount() {
        return listPlaylists.size();
    }

    public  class MyViewHolder extends  RecyclerView.ViewHolder{

        private TextView txtTitulo, txtFilmes, txtCurtidas;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.tituloPlaylist);
            txtFilmes = itemView.findViewById(R.id.textFilmes);
            txtCurtidas = itemView.findViewById(R.id.txtCurtidas);

        }
    }

}
