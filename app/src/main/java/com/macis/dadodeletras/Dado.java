package com.macis.dadodeletras;

import java.security.SecureRandom;

public class Dado {
    public static final String TODAS_LAS_LETRAS = "abcdefghijklmnopqrstuvwxyz";
    public static final String LETRAS_SCATTERGORIES = "abcdefghijklmnoprstw";

    //Variables de instancia
    private SecureRandom dado;
    private char[] letras;
    private boolean[] letrasUsadas;
    private int contadorLetras;
    private String letraActual;

    /**
     * Constructor sin parametros que inicializa todas las variables de instancia
     * necesarias para el funcionamiento de la clase.
     */
    public Dado(String letrasJugables) {
        dado = new SecureRandom();
        letras = letrasJugables.toCharArray();
        letrasUsadas = new boolean[letras.length];
        restablecerLetrasUsadas();
        contadorLetras = 0;
    }

    /**
     * Metodo que simula una tirada del dado y devuelve la letra obtenida.
     * Si la letra es repetida se descarta y se vuelve a tirar el dado.
     * @return <code>char</code> Una letra del abecedario no devuelta anteriormente.
     */
    public char siguienteLetra () {
        int tirada = dado.nextInt(letras.length);
        char resultado = letras[tirada];

        if (!letrasUsadas[tirada]) {
            letrasUsadas[tirada] = true;
            contadorLetras++;
            letraActual = ""+resultado;
            return resultado;

        } else {
            return siguienteLetra();
        }
    }

    /**
     * Limpia la lista de letras usadas para empezar de nuevo.
     */
    public void restablecerLetrasUsadas() {
        for (int i = 0; i < letrasUsadas.length; i++) {
            letrasUsadas[i] = false;
        }
        contadorLetras = 0;
    }

    /**
     * Informa de la cantidad de letras totales disponibles.
     * @return <code>int</code> El numero de letras diferentes disponibles
     */
    public int getCantidadLetrasTotal() {
        return letras.length;
    }

    /**
     * Informa de cuántas letras han sido jugadas hasta el momento.
     * @return <code>int</code> El numero de letras diferentes disponibles
     */
    public int getCantidadLetrasJugadas() {
        return contadorLetras;
    }
    /**
     * Devuelve la letra actual en juego
     * @return <code>String</code> La letra actual.
     */
    public String getLetraActual() {
        return letraActual;
    }
}
