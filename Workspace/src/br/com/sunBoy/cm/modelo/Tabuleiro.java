package br.com.sunBoy.cm.modelo;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements ObserverField {

    private final int linhas;
    private final int colunas;
    private final int minas;

    private final List<Campo> campos = new ArrayList<>();

    public final List<Consumer<Boolean>> observadores = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void paraCadaCampo(Consumer<Campo> funcao) {
        campos.forEach(funcao);
    }

    public void registrarObservador(Consumer<Boolean> observador) {
        observadores.add(observador);
    }


    private void notificarObservadores(boolean resultado) {
        observadores.stream()
                .forEach(o -> o.accept(resultado));
    }


    public void open(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.abrir());
    }




    public void markedAlternative(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcacao());
    }


    private void gerarCampos() {
        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                Campo campo = new Campo(linha, coluna);
                campo.registrarObservadores(this);
                campos.add(campo);
            }
        }

    }


    private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }


    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = c -> c.isMinado();


        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while (minasArmadas < minas);
    }

    //Quando o objetivo tiver sido alcançado
    public boolean tabuleiroRevelado() {
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reset() {
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }


    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    //IMPLEMENTO: efetivação de método da interface implementada
    @Override
    public void eventoOcorreu(Campo campo, EventField evento) {
        SwingUtilities.invokeLater(() -> {
            if (evento == EventField.EXPLODIR) {
                revelarMinas();
                JOptionPane.showMessageDialog(null, "GAME OVER");
                notificarObservadores(false);
                reset();
            } else if (tabuleiroRevelado()) {
                JOptionPane.showMessageDialog(null, "You are winner!");
                notificarObservadores(true);
                reset();
            }
        });
    }


    private void revelarMinas() {
        campos.stream()
                .filter(c -> c.isMinado())
                .forEach(c -> c.setAberto(true));

    }

}
