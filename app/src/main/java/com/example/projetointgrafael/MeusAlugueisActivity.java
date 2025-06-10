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

public class MeusAlugueisActivity extends AppCompatActivity {

    LinearLayout layoutAlugueis;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_alugueis);

        layoutAlugueis = findViewById(R.id.layoutAlugueis);
        Button btnVoltarAluguel = findViewById(R.id.btnVoltarAluguel);
        dbHelper = new DatabaseHelper(this);

        carregarAlugueis();

        btnVoltarAluguel.setOnClickListener(v -> {
            Intent intent = new Intent(MeusAlugueisActivity.this, AluguelActivity.class);
            startActivity(intent);
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
                String prazo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRAZO));
                String valor = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_VALOR));

                TextView txt = new TextView(this);
                txt.setText(
                        "Item: " + item + "\n" +
                                "Endereço: " + endereco + "\n" +
                                "Prazo: " + prazo + "\n" +
                                "Valor: " + valor
                );
                txt.setTextColor(getResources().getColor(R.color.white));
                txt.setTextSize(16);
                txt.setPadding(0, 16, 0, 16);
                txt.setTypeface(ResourcesCompat.getFont(this, R.font.bebas_neue));
                layoutAlugueis.addView(txt);
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
