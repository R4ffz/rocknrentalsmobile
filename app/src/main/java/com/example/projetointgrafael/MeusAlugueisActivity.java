package com.example.projetointgrafael;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import com.example.projetointgrafael.database.DatabaseHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MeusAlugueisActivity extends AppCompatActivity {

    private LinearLayout layoutAlugueis;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_alugueis);

        layoutAlugueis = findViewById(R.id.layoutAlugueis);
        Button btnVoltarAluguel = findViewById(R.id.btnVoltarAluguel);
        dbHelper = new DatabaseHelper(this);

        carregarAlugueis();

        btnVoltarAluguel.setOnClickListener(v -> {
            startActivity(new Intent(MeusAlugueisActivity.this, AluguelActivity.class));
            finish();
        });
    }

    private void carregarAlugueis() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_ALUGUEIS, null);

        if (cursor.moveToFirst()) {
            do {
                String item = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ITEM));
                String endereco = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ENDERECO));
                String prazo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRAZO_ALUGUEL));
                String valor = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_VALOR_ALUGUEL));
                long dataMillis = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DATA_ALUGUEL));

                // formata a data
                String dataFormatada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(new Date(dataMillis));

                // calcula dias restantes
                long diff = System.currentTimeMillis() - dataMillis;
                int diasPassados = (int) (diff / (1000L * 60 * 60 * 24));
                int diasRestantes = Math.max(0, 30 - diasPassados);

                // monta o bloco de texto
                TextView linha = new TextView(this);
                String texto =
                        "Item: " + item + "\n" +
                                "Data: " + dataFormatada + "\n" +
                                "Dias Restantes: " + diasRestantes + "\n" +
                                "Endereço: " + endereco + "\n" +
                                "Valor: " + valor;
                linha.setText(texto);
                linha.setTextColor(getResources().getColor(R.color.white));
                linha.setTextSize(16);
                linha.setPadding(0, 16, 0, 16);
                linha.setTypeface(ResourcesCompat.getFont(this, R.font.bebas_neue));

                layoutAlugueis.addView(linha);

            } while (cursor.moveToNext());
        } else {
            TextView vazio = new TextView(this);
            vazio.setText("Nenhum aluguel registrado.");
            vazio.setTextColor(getResources().getColor(R.color.white));
            vazio.setTextSize(16);
            vazio.setPadding(0, 16, 0, 16);
            vazio.setTypeface(ResourcesCompat.getFont(this, R.font.bebas_neue));
            layoutAlugueis.addView(vazio);
        }
        cursor.close();
    }
}
