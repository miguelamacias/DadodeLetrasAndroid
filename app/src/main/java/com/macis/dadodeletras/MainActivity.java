package com.macis.dadodeletras;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    //Crear el dado
    Dado dado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        String letrasJugables = preferencias.getString("opcionLetras", Dado.TODAS_LAS_LETRAS);
        dado = new Dado(letrasJugables);
    }

    public void tirarDado(View view) {
        ImageView cuadroImagen = findViewById(R.id.imagenLetra);

        if (dado.getCantidadLetrasJugadas() < dado.getCantidadLetrasTotal()) {

            String letra = String.format("%c", dado.siguienteLetra());
            int imagen = this.getResources().getIdentifier(letra, "drawable", this.getPackageName());
            cuadroImagen.setImageResource(imagen);

        } else {
            Toast mensaje = Toast.makeText(this, R.string.finLetras, Toast.LENGTH_LONG);
            mensaje.show();

            cuadroImagen.setImageResource(R.drawable.abecedario);
            dado.restablecerLetrasUsadas();

            Vibrator mVibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            mVibrate.vibrate(500);
        }
    }

    public void abrirPreferencias(View view) {
        Intent abrirPreferencias = new Intent(this, PreferenciasActivity.class);
        startActivity(abrirPreferencias);
    }


}
