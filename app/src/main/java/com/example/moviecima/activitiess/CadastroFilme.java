package com.example.moviecima.activitiess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moviecima.Models.Filme;
import com.example.moviecima.Models.Playlist;
import com.example.moviecima.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CadastroFilme extends AppCompatActivity {
    EditText editTextNomeFilme, editTextAnoFilme, editTextUrlImagem;
    Button btnSalvar;
    ImageView imageViewFilme;
    private int curtida;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_filme);
        editTextNomeFilme = findViewById(R.id.editTextNomeFilme);
        editTextAnoFilme = findViewById(R.id.editTextAnoFilme);
        editTextUrlImagem = findViewById(R.id.editTextUrlImagem);
        btnSalvar = findViewById(R.id.btnSalvar);
        imageViewFilme = findViewById(R.id.imageViewFilme);
        Bundle dados = getIntent().getExtras();
        String idPlaylist = dados.getString("RA");
        DatabaseReference noPlaylists = reference.child("Playlists").child(idPlaylist);
        noPlaylists.child("ra").setValue(idPlaylist);

        noPlaylists.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String curtidasAux = snapshot.child("curtidas").getValue().toString();
                if(!curtidasAux.isEmpty()){
                    curtida = Integer.parseInt(curtidasAux);
                }else{
                    curtida = 0;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        noPlaylists.child("curtidas").setValue(curtida);
        DatabaseReference noFilmes= reference.child("Playlists").child(idPlaylist).child("filmes");
        List<Filme> filmes = new ArrayList<>();
        noFilmes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                filmes.clear();
                for (DataSnapshot current_film: snapshot.getChildren()){
                    String nome = current_film.child("nome").getValue().toString();
                    String ano = current_film.child("ano").getValue().toString();
                    filmes.add(new Filme(nome, ano));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeFilme = editTextNomeFilme.getText().toString();
                String anoFilme = editTextAnoFilme.getText().toString();
                String urlImagem = editTextUrlImagem.getText().toString();
                if(filmes.size() < 4){
                    filmes.add(new Filme(nomeFilme, anoFilme));
                    noFilmes.setValue(filmes);
                    Toast.makeText(getApplicationContext(), "Gravado com sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Lista cheia", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Filme filme = new Filme(nomeFilme, anoFilme);


                // Carregar e exibir a imagem do URL inserido
                if (!urlImagem.isEmpty()) {
                    //urlImagem = "https://img.elo7.com.br/product/original/264FCC6/big-poster-filme-batman-o-cavaleiro-das-trevas-lo02-90x60-cm-batman.jpg";
                    Picasso.get().load(urlImagem).into(imageViewFilme);
                    imageViewFilme.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}