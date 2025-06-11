package com.example.projetointgrafael;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PagamentoActivity extends AppCompatActivity {

    RadioGroup rgMetodo;
    RadioButton rbPix, rbCartao;
    View layoutPix, layoutCartao;
    EditText etPixChave, etNumeroCartao, etValidade, etCvv;
    Button btnConfirmarPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        rgMetodo = findViewById(R.id.rgMetodo);
        rbPix = findViewById(R.id.rbPix);
        rbCartao = findViewById(R.id.rbCartao);
        layoutPix = findViewById(R.id.layoutPix);
        layoutCartao = findViewById(R.id.layoutCartao);
        etPixChave = findViewById(R.id.etPixChave);
        etNumeroCartao = findViewById(R.id.etNumeroCartao);
        etValidade = findViewById(R.id.etValidade);
        etCvv = findViewById(R.id.etCvv);
        btnConfirmarPagamento = findViewById(R.id.btnConfirmarPagamento);

        rgMetodo.setOnCheckedChangeListener((group, checkedId) -> {
            layoutPix.setVisibility(checkedId == R.id.rbPix ? View.VISIBLE : View.GONE);
            layoutCartao.setVisibility(checkedId == R.id.rbCartao ? View.VISIBLE : View.GONE);
        });

        btnConfirmarPagamento.setOnClickListener(v -> {
            if (rbPix.isChecked()) {
                String chave = etPixChave.getText().toString().trim();
                if (chave.isEmpty()) {
                    Toast.makeText(this, "Preencha a chave PIX", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "PIX simulado enviado para: " + chave, Toast.LENGTH_LONG).show();

            } else {
                String num = etNumeroCartao.getText().toString().trim();
                String val = etValidade.getText().toString().trim();
                String cvv = etCvv.getText().toString().trim();
                if (num.isEmpty() || val.isEmpty() || cvv.isEmpty()) {
                    Toast.makeText(this, "Preencha todos os dados do cartão", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "Pagamento simulado com cartão final " +
                        num.substring(num.length()-4), Toast.LENGTH_LONG).show();
            }

            finish();
        });
    }
}
