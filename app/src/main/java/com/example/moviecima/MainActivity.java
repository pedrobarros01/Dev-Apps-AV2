package com.example.moviecima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviecima.Models.User;
import com.example.moviecima.activitiess.ListaMovies;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText editRA, editNome;
    private Button btnEntrar;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEntrar = findViewById(R.id.buttonEntrar);
        editNome = findViewById(R.id.editTextNome);
        editRA = findViewById(R.id.editTextRA);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                if(editNome.getText().toString().isEmpty() || editRA.getText().toString().isEmpty() || editRA.getText().toString().contains(".")){
                    Toast.makeText(getApplicationContext(), "Campos RA ou NOME nao preenchidos", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setNome(editNome.getText().toString());
                user.setRA(editRA.getText().toString());
                Log.i("DADOS_MOVIE", "NOME="+user.getNome());
                Log.i("DADOS_MOVIE", "RA="+user.getRA());
                reference.child("Usuarios").child(user.getRA()).setValue(user);
                Intent intent = new Intent(getApplicationContext(), ListaMovies.class);
                intent.putExtra("RA", user.getRA());
                intent.putExtra("NOME", user.getNome());
                startActivity(intent);
            }
        });


    }
}