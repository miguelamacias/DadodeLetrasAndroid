package com.macis.dadodeletras;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    //Crear el dado
    Dado dado = new Dado();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tirarDado(View view) {
        TextView resultado = (TextView) findViewById(R.id.resultado);
        ImageView cuadroImagen = (ImageView) findViewById(R.id.imagenLetra);

        if (dado.getCantidadLetrasJugadas() < dado.getCantidadLetrasTotal()) {

            String letra = String.format("%c", dado.siguienteLetra());
            int imagen = this.getResources().getIdentifier(letra, "drawable", this.getPackageName());
            cuadroImagen.setImageResource(imagen);
            resultado.setText(getString(R.string.vacio));

        } else {
            resultado.setText(getString(R.string.finLetras));
            cuadroImagen.setImageResource(R.drawable.abecedario);
            dado.restablecerLetrasUsadas();

            Vibrator mVibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            mVibrate.vibrate(500);
        }
    }


}
