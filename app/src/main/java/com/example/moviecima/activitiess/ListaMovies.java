package com.example.moviecima.activitiess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moviecima.Adapter.AdapterPlaylist;
import com.example.moviecima.Models.Filme;
import com.example.moviecima.Models.Playlist;
import com.example.moviecima.Models.User;
import com.example.moviecima.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaMovies extends AppCompatActivity {

    private TextView txtBemvindo;
    private Button btnCadastroFilme;
    private RecyclerView recPlaylist;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_movies);
        Bundle dados = getIntent().getExtras();
        String nome = dados.getString("NOME");
        String ra = dados.getString("RA");

        txtBemvindo = findViewById(R.id.textUsuario);
        txtBemvindo.setText("Bem vindo usuário: " + nome);
        recPlaylist = findViewById(R.id.listPlaylistRecView);
        btnCadastroFilme = findViewById(R.id.botaoCadastro);

        btnCadastroFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroFilme.class);
                intent.putExtra("RA", ra);
                startActivity(intent);
            }
        });
        CarregarPlayListFireBase();


    }

    public void CarregarPlayListFireBase(){
        DatabaseReference noPlaylistsGeral = reference.child("Playlists");
        List<Playlist> playlists = new ArrayList<Playlist>();

        noPlaylistsGeral.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                playlists.clear();
                for (DataSnapshot current_playlist: snapshot.getChildren()){
                    String ra = current_playlist.child("ra").getValue().toString();
                    List<Filme> filmes = new ArrayList<>();
                    int  curtidas = Integer.parseInt(current_playlist.child("curtidas").getValue().toString());

                    for(DataSnapshot current_film: current_playlist.child("filmes").getChildren()){
                        //Log.i("DADOS_DEBUG_MOVIE", "filme: "+current_film.child("nome").getValue().toString());
                        String nome = current_film.child("nome").getValue().toString();
                        String ano = current_film.child("ano").getValue().toString();
                        filmes.add(new Filme(nome, ano));
                    }
                    Playlist play = new Playlist();
                    play.setRA(ra);

                    play.setCurtidas(curtidas);
                    play.setFilmes(filmes);
                    playlists.add(play);


                }
                CarregarLista(playlists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Log.i("DADOS_DEBUG_MOVIE", "conjunto: "+ playlists.get(1).toString());

    }

    public  void CarregarLista(List<Playlist> listPlaylist){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recPlaylist.setLayoutManager(layoutManager);
        recPlaylist.addItemDecoration(new DividerItemDecoration(
                getApplicationContext(),
                LinearLayout.VERTICAL
        ));
        recPlaylist.setHasFixedSize(true);


        AdapterPlaylist adapter = new AdapterPlaylist(listPlaylist);
        recPlaylist.setAdapter(adapter);
    }
}