package com.example.projetointgrafael;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projetointgrafael.database.DatabaseHelper;

public class AluguelActivity extends AppCompatActivity {

    EditText editEndereco;
    LinearLayout formEndereco;
    TextView itemSelecionado;
    String itemAtual = "";

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluguel);

        editEndereco = findViewById(R.id.editEndereco);
        formEndereco = findViewById(R.id.aluguelForm);
        itemSelecionado = findViewById(R.id.itemSelecionado);

        Button btnConfirmar = findViewById(R.id.btnConfirmarAluguel);
        Button btnMeusAlugueis = findViewById(R.id.btnMeusAlugueis);

        dbHelper = new DatabaseHelper(this);

        configurarBotao(R.id.btnAlugarFender, "Guitarra Fender Stratocaster");
        configurarBotao(R.id.btnAlugarGibson, "Guitarra Gibson Les Paul");
        configurarBotao(R.id.btnAlugarIbanez, "Guitarra Ibanez RG");
        configurarBotao(R.id.btnAlugarJackson, "Guitarra Jackson");

        configurarBotao(R.id.btnAlugarAmpFender, "Amplificador Fender");
        configurarBotao(R.id.btnAlugarAmpMarshall, "Amplificador Marshall");
        configurarBotao(R.id.btnAlugarAmpPeavey, "Amplificador Peavey");
        configurarBotao(R.id.btnAlugarAmpOrange, "Amplificador Orange");

        configurarBotao(R.id.btnAlugarPedalBoss, "Pedal Boss DS-1");
        configurarBotao(R.id.btnAlugarPedalIbanez, "Pedal Tube Screamer");
        configurarBotao(R.id.btnAlugarPedalFuzz, "Pedal Fuzz");

        configurarBotao(R.id.btnAlugarZoomG1XFour, "Pedaleira Zoom G1X Four");
        configurarBotao(R.id.btnAlugarQuadCortex, "Pedaleira Quad Cortex");

        btnConfirmar.setOnClickListener(v -> {
            String endereco = editEndereco.getText().toString().trim();
            if (endereco.isEmpty()) {
                Toast.makeText(this, "Digite o endereço", Toast.LENGTH_SHORT).show();
            } else {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COL_ITEM, itemAtual);
                values.put(DatabaseHelper.COL_ENDERECO, endereco);
                values.put(DatabaseHelper.COL_PRAZO, "30 dias");
                values.put(DatabaseHelper.COL_VALOR, calcularValor(itemAtual));
                long resultado = db.insert(DatabaseHelper.TABLE_ALUGUEIS, null, values);

                if (resultado != -1) {
                    Toast.makeText(this, "Aluguel salvo com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Erro ao salvar aluguel!", Toast.LENGTH_SHORT).show();
                }

                editEndereco.setText("");
                formEndereco.setVisibility(View.GONE);
            }
        });

        btnMeusAlugueis.setOnClickListener(v -> {
            Intent intent = new Intent(AluguelActivity.this, MeusAlugueisActivity.class);
            startActivity(intent);
        });
    }

    private void configurarBotao(int botaoId, String itemTexto) {
        Button btn = findViewById(botaoId);
        btn.setOnClickListener(v -> {
            itemAtual = itemTexto;
            itemSelecionado.setText(itemAtual);
            formEndereco.setVisibility(View.VISIBLE);
        });
    }

    private String calcularValor(String item) {
        if (item.contains("Guitarra")) return "R$ 300";
        if (item.contains("Pedaleira")) return "R$ 250";
        if (item.contains("Pedal") || item.contains("Amplificador")) return "R$ 200";
        return "R$ 0";
    }
}
