package com.example.projetointgrafael;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projetointgrafael.database.DatabaseHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    EditText editLoginEmail, editLoginSenha;
    Button btnEntrar, btnVoltarHomeLogin;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginSenha = findViewById(R.id.editLoginSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnVoltarHomeLogin = findViewById(R.id.btnVoltarHomeLogin);

        dbHelper = new DatabaseHelper(this);

        btnEntrar.setOnClickListener(v -> {
            String email = editLoginEmail.getText().toString().trim();
            String senha = editLoginSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            String senhaCriptografada = criptografarSenha(senha);

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_USUARIO +
                    " WHERE " + DatabaseHelper.COL_EMAIL + " = ? AND " + DatabaseHelper.COL_SENHA + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{email, senhaCriptografada});

            if (cursor.moveToFirst()) {
                SharedPreferences prefs = getSharedPreferences("usuario", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                cursor.close();
                Intent intent = new Intent(LoginActivity.this, AluguelActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show();
            }
        });

        btnVoltarHomeLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private String criptografarSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return senha;
        }
    }
}
