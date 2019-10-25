package com.macis.dadodeletras;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class PreferenciasActivity extends AppCompatActivity {
    private EditText cuadroListaLetras;
    private SharedPreferences preferencias;
    private RadioGroup grupoBotones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        cuadroListaLetras = findViewById(R.id.listaLetras);
        grupoBotones = findViewById(R.id.grupoRBotones);

        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        RadioButton rbPulsado = findViewById(preferencias.getInt("rbotonPulsado", R.id.rbTodas));
        rbPulsado.setChecked(true);
        cuadroListaLetras.setVisibility(preferencias.getInt("editTextVisible", View.INVISIBLE));
        cuadroListaLetras.setText(preferencias.getString("opcionLetras", ""));

    }

    public void mostrarListaLetras(View view) {
        cuadroListaLetras.setVisibility(View.VISIBLE);
    }

    public void ocultarListaLetras(View view) {
        cuadroListaLetras.setVisibility(View.INVISIBLE);
    }

    public void guardarPreferencias(View view) {
        SharedPreferences.Editor editorPreferencias = preferencias.edit();
        String letrasJugables;

        switch (grupoBotones.getCheckedRadioButtonId()){
            case R.id.rbTodas:
                letrasJugables = Dado.TODAS_LAS_LETRAS;
                break;
            case R.id.rbScattergories:
                letrasJugables = Dado.LETRAS_SCATTERGORIES;
                break;
            case R.id.rbPerso:
                letrasJugables = cuadroListaLetras.getText().toString().toLowerCase();
                break;
            default:
                letrasJugables = "";
                break;
        }


        editorPreferencias.putString("opcionLetras", letrasJugables);
        editorPreferencias.putInt("rbotonPulsado", grupoBotones.getCheckedRadioButtonId());
        editorPreferencias.putInt("editTextVisible", cuadroListaLetras.getVisibility());
        editorPreferencias.commit();
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}
