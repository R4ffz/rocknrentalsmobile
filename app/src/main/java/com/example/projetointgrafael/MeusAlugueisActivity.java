package com.example.projetointgrafael;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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
        layoutAlugueis.removeAllViews();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_ALUGUEIS, null);

        if (cursor.moveToFirst()) {
            do {
                // coleta dados
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
                String item = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ITEM));
                String endereco = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ENDERECO));
                String prazo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRAZO_ALUGUEL));
                String valor = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_VALOR_ALUGUEL));
                long dataMillis = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DATA_ALUGUEL));

                String dataFormatada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(new Date(dataMillis));
                long diff = System.currentTimeMillis() - dataMillis;
                int diasPassados = (int)(diff / (1000L *60*60*24));
                int diasRestantes = Math.max(0, 30 - diasPassados);

                // monta container
                LinearLayout itemLayout = new LinearLayout(this);
                itemLayout.setOrientation(LinearLayout.VERTICAL);
                itemLayout.setPadding(12,12,12,12);

                TextView tv = new TextView(this);
                tv.setText(
                        "Item: " + item + "\n" +
                                "Data: " + dataFormatada + "\n" +
                                "Dias Restantes: " + diasRestantes + "\n" +
                                "Endereço: " + endereco + "\n" +
                                "Valor: " + valor
                );
                tv.setTextColor(getResources().getColor(R.color.white));
                tv.setTypeface(ResourcesCompat.getFont(this, R.font.bebas_neue));
                itemLayout.addView(tv);

                Button btnCancelar = new Button(this);
                btnCancelar.setText("CANCELAR");
                btnCancelar.setAllCaps(true);
                btnCancelar.setBackgroundColor(getResources().getColor(R.color.roxo));
                btnCancelar.setTextColor(getResources().getColor(R.color.white));
                btnCancelar.setOnClickListener(v -> {
                    int rows = db.delete(
                            DatabaseHelper.TABLE_ALUGUEIS,
                            DatabaseHelper.COL_ID + "=?",
                            new String[]{ String.valueOf(id) }
                    );
                    if (rows > 0) {
                        Toast.makeText(this, "Aluguel cancelado", Toast.LENGTH_SHORT).show();
                        layoutAlugueis.removeView(itemLayout);
                    } else {
                        Toast.makeText(this, "Falha no cancelamento", Toast.LENGTH_SHORT).show();
                    }
                });
                itemLayout.addView(btnCancelar);

                layoutAlugueis.addView(itemLayout);

            } while (cursor.moveToNext());
        } else {
            TextView vazio = new TextView(this);
            vazio.setText("Nenhum aluguel registrado.");
            vazio.setTextColor(getResources().getColor(R.color.white));
            vazio.setTypeface(ResourcesCompat.getFont(this, R.font.bebas_neue));
            vazio.setPadding(0,16,0,16);
            layoutAlugueis.addView(vazio);
        }
        cursor.close();
    }
}
