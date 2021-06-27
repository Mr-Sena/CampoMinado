package br.com.sunBoy.cm.view;

import br.com.sunBoy.cm.modelo.Tabuleiro;

public class Temp {

    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro(3, 3, 9);

        tabuleiro.registrarObservador( e -> {
            if (e) { // e contain a boolean value - true or false.
                System.out.println("Congratulations :D");
            } else {
                System.out.println("Try again later or whenever like ;)");
            }
        });

        tabuleiro.markedAlternative(0, 0);
        tabuleiro.markedAlternative(0, 1);
        tabuleiro.markedAlternative(0, 2);
        tabuleiro.markedAlternative(1, 0);
        tabuleiro.markedAlternative(1, 1);
        tabuleiro.markedAlternative(1, 2);
        tabuleiro.markedAlternative(2, 0);
        tabuleiro.markedAlternative(2, 1);
        tabuleiro.open(2, 2);

    }

}
