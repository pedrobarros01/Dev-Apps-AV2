package com.example.moviecima.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecima.Models.Filme;
import com.example.moviecima.Models.Playlist;
import com.example.moviecima.R;

import java.util.List;

public class AdapterPlaylist extends RecyclerView.Adapter<AdapterPlaylist.MyViewHolder> {
    public interface  RecycleViewListenerClass{
        void onItemClicked(Playlist play);
    }

    public AdapterPlaylist(List<Playlist> listPlaylists, RecycleViewListenerClass listener) {
        this.listPlaylists = listPlaylists;
        this.listener = listener;
    }
    private RecycleViewListenerClass  listener;
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
        holder.btnCurtir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(listPlaylists.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listPlaylists.size();
    }

    public  class MyViewHolder extends  RecyclerView.ViewHolder{

        private TextView txtTitulo, txtFilmes, txtCurtidas;
        private Button btnCurtir;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.tituloPlaylist);
            txtFilmes = itemView.findViewById(R.id.textFilmes);
            txtCurtidas = itemView.findViewById(R.id.txtCurtidas);
            btnCurtir = itemView.findViewById(R.id.buttonCurtida);

        }
    }

}
