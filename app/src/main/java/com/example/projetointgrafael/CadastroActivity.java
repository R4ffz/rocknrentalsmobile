package com.example.projetointgrafael;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projetointgrafael.database.DatabaseHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CadastroActivity extends AppCompatActivity {

    EditText editNome, editEmail, editSenha, editNascimento;
    Button btnCadastrar, btnVoltarHome;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        editNascimento = findViewById(R.id.editNascimento);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVoltarHome = findViewById(R.id.btnVoltarHome);

        dbHelper = new DatabaseHelper(this);

        btnCadastrar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String senha = editSenha.getText().toString().trim();
            String nascimento = editNascimento.getText().toString().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || nascimento.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            String senhaCriptografada = criptografarSenha(senha);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COL_NOME, nome);
            values.put(DatabaseHelper.COL_EMAIL, email);
            values.put(DatabaseHelper.COL_SENHA, senhaCriptografada);
            values.put(DatabaseHelper.COL_NASCIMENTO, nascimento);

            long result = db.insert(DatabaseHelper.TABLE_USUARIO, null, values);

            if (result != -1) {
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Erro ao cadastrar. E-mail já existe?", Toast.LENGTH_SHORT).show();
            }
        });

        btnVoltarHome.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
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
