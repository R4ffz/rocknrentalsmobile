package com.example.projetointgrafael;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnExplore, btnCadastro, btnLogin, btnMeusAlugueisMain, btnAlugarInstrumentoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExplore = findViewById(R.id.btnExplore);
        btnCadastro = findViewById(R.id.btnCadastro);
        btnLogin = findViewById(R.id.btnLogin);
        btnMeusAlugueisMain = findViewById(R.id.btnMeusAlugueisMain);
        btnAlugarInstrumentoMain = findViewById(R.id.btnAlugarInstrumentoMain);

        btnExplore.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExploreActivity.class);
            startActivity(intent);
        });

        btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnMeusAlugueisMain.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("usuario", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

            if (isLoggedIn) {
                startActivity(new Intent(MainActivity.this, MeusAlugueisActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btnAlugarInstrumentoMain.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("usuario", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

            if (isLoggedIn) {
                startActivity(new Intent(MainActivity.this, AluguelActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
