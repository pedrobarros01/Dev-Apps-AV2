package com.example.moviecima.activitiess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.moviecima.Models.User;
import com.example.moviecima.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListaMovies extends AppCompatActivity {

    private User userLogado = new User();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_movies);
        Bundle dados = getIntent().getExtras();
        this.userLogado.setNome(dados.getString("NOME"));
        this.userLogado.setRA(dados.getString("RA"));
    }
}