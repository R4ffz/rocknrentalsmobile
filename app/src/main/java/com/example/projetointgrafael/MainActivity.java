package com.example.projetointgrafael;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnExplore, btnCadastro, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExplore = findViewById(R.id.btnExplore);
        btnCadastro = findViewById(R.id.btnCadastro);
        btnLogin = findViewById(R.id.btnLogin);

        btnExplore.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExploreActivity.class);
            startActivity(intent);
        });

        btnCadastro.setOnClickListener(v -> {
            // TODO: abrir tela de cadastro
        });

        btnLogin.setOnClickListener(v -> {
            // TODO: abrir tela de login
        });
    }
}
