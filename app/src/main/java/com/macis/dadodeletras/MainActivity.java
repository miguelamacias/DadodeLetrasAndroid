package com.macis.dadodeletras;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends Activity {
    //Crear el dado
    Dado dado;
    Button btnGirarDado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Publicidad
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Cargar las preferencias de letras jugables
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        String letrasJugables = preferencias.getString("opcionLetras", Dado.TODAS_LAS_LETRAS);
        dado = new Dado(letrasJugables);

        //Cargar el boton
        btnGirarDado = findViewById(R.id.btnTirarDado);
    }
    public void tirarDado(View view) {
        ImageView cuadroImagen = findViewById(R.id.imagenLetra);
        TextView etLetrasJgadas = findViewById(R.id.letrasJugadas);

        if (dado.getCantidadLetrasJugadas() < dado.getCantidadLetrasTotal()) {
            //Obtención de la nueva letra.
            String letra = String.format("%c", dado.siguienteLetra());
            int imagen = this.getResources().getIdentifier(letra, "drawable", this.getPackageName());

            //animacion y cambio de imagen del dado
            btnGirarDado.setEnabled(false); //desactiva el boton para evitar bugs
            cuadroImagen.animate().rotationXBy(720).setDuration(1200);
            cuadroImagen.animate().rotationYBy(-720).setDuration(1200)
                    .withEndAction(() -> btnGirarDado.setEnabled(true)); //vuelve a activar el boton al terminar de girar
            btnGirarDado.animate().rotationYBy(0).setDuration(600) //cambia de imagen a mitad de la animación para un efecto más natural
                    .withEndAction(() -> cuadroImagen.setImageResource(imagen));

            cuadroImagen.setContentDescription(dado.getLetraActual());

            //añadir la letra a la lista de letras jugadas
            etLetrasJgadas.append(dado.getLetraActual().toUpperCase());

        } else {
            Toast mensaje = Toast.makeText(this, R.string.finLetras, Toast.LENGTH_LONG);
            mensaje.show();

            cuadroImagen.setImageResource(R.drawable.abecedario);
            dado.restablecerLetrasUsadas();
            etLetrasJgadas.setText(R.string.vacio);

            Vibrator mVibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            mVibrate.vibrate(500);
        }
    }

    public void abrirPreferencias(View view) {
        Intent abrirPreferencias = new Intent(this, PreferenciasActivity.class);
        //Añadir un efectillo al abrir la activity nueva
        ActivityOptionsCompat opciones = ActivityOptionsCompat.
                makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight());

        startActivity(abrirPreferencias, opciones.toBundle());
    }


}
