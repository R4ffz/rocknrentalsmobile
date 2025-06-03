package com.example.projetointgrafael;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ExploreActivity extends AppCompatActivity {

    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        btnVoltar = findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(ExploreActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
