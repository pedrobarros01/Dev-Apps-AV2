package com.example.moviecima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviecima.Models.Playlist;
import com.example.moviecima.Models.User;
import com.example.moviecima.activitiess.ListaMovies;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editRA, editNome;
    private Button btnEntrar;
    //private Boolean check;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check = false;
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
                verificarSeJaTemPlaylist(user.getRA())
                        .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                            @Override
                            public void onComplete(@NonNull Task<Boolean> task) {
                                if (task.isSuccessful()) {
                                    boolean playlistEncontrada = task.getResult();
                                    if (playlistEncontrada) {
                                        reference.child("Usuarios").child(user.getRA()).setValue(user);
                                    } else {
                                        reference.child("Usuarios").child(user.getRA()).setValue(user);
                                        Playlist play = new Playlist();
                                        play.setRA(user.getRA());
                                        play.setCurtidas(0);
                                        play.setFilmes(null);
                                        reference.child("Playlists").child(user.getRA()).setValue(play);
                                    }
                                } else {
                                    // Lidar com o erro, se necessário
                                }
                            }
                        });


                Intent intent = new Intent(getApplicationContext(), ListaMovies.class);
                intent.putExtra("RA", user.getRA());
                intent.putExtra("NOME", user.getNome());
                startActivity(intent);
            }
        });


    }
    public Task<Boolean> verificarSeJaTemPlaylist(String ra) {
        DatabaseReference noUserPlaylist = reference.child("Playlists");

        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        noUserPlaylist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot current_playlist : snapshot.getChildren()) {
                    String raList = current_playlist.getKey();
                    if (raList != null && raList.equals(ra)) {
                        taskCompletionSource.setResult(true);
                        return;
                    }
                }
                taskCompletionSource.setResult(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                taskCompletionSource.setResult(false); // Em caso de erro, considere como não encontrada
            }
        });

        return taskCompletionSource.getTask();
    }
}