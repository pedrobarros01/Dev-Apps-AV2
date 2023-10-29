package com.example.moviecima.activitiess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moviecima.Models.FilmeSelect;
import com.example.moviecima.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DeletarFilme extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private Button btnDeletar;
    private Spinner selectFilmes;
    private String idFilme;
    ImageView imageViewFilme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletar_filme);
        btnDeletar = findViewById(R.id.botaoDeletar2);
        selectFilmes = findViewById(R.id.selectFilme);
        imageViewFilme = findViewById(R.id.imageViewDeleteFilme);

        Bundle dados = getIntent().getExtras();
        String ra = dados.getString("RA").toString();
        DatabaseReference noFilmes = reference
                .child("Playlists")
                .child(ra)
                .child("filmes");
        List<FilmeSelect> filmeSelects = new ArrayList<>();
        noFilmes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                filmeSelects.clear();
                for(DataSnapshot current_film: snapshot.getChildren()){
                    String id = current_film.getKey().toString();
                    String nome = current_film.child("nome").getValue().toString();
                    filmeSelects.add(new FilmeSelect(id, nome));
                }
                ArrayAdapter<FilmeSelect> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, filmeSelects);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                selectFilmes.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        selectFilmes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilmeSelect filmeSelect = filmeSelects.get(position);
                idFilme = filmeSelect.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("Playlists")
                        .child(ra)
                        .child("filmes")
                        .child(idFilme)
                        .removeValue();
                Toast.makeText(getApplicationContext(), "Filme deletado com sucesso", Toast.LENGTH_SHORT).show();
                String urlImagem = "https://http.cat/images/202.jpg";
                Picasso.get().load(urlImagem).into(imageViewFilme);
                imageViewFilme.setVisibility(View.VISIBLE);

            }
        });



    }
}