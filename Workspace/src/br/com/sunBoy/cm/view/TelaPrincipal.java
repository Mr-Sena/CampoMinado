package br.com.sunBoy.cm.view;

import br.com.sunBoy.cm.modelo.Tabuleiro;
import br.com.sunBoy.cm.modelo.Tabuleiro;

import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal(){

        Tabuleiro tabuleiro = new Tabuleiro(16,30, 20);

        add(new PainelTabuleiro(tabuleiro));

        setTitle("    Campo Minado    ");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {

        new TelaPrincipal();

    }

}
