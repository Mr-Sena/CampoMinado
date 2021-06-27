package br.com.sunBoy.cm.view;

import br.com.sunBoy.cm.modelo.Campo;
import br.com.sunBoy.cm.modelo.EventField;
import br.com.sunBoy.cm.modelo.ObserverField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotaoCampo extends JButton implements ObserverField, MouseListener  {

    private final Color BG_PADRAO = new Color(184, 184, 184);
    private final Color BG_MARCAR = new Color(8, 179, 247);
    private final Color BG_EXPLODIR = new Color(189, 66, 68);
    private final Color TEXTO_VERDE = new Color(0, 100, 0);

    private Campo campo;

    public BotaoCampo(Campo campo) {

        this.campo = campo;
        setBackground(BG_PADRAO);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        campo.registrarObservadores(this);

    }

    @Override
    public void eventoOcorreu(Campo campo, EventField evento) {

        switch(evento) {
            case ABRIR:
                ApplyOpenStyle();
                break;
            case MARCAR:
                applyMarkedStyle();
                break;
            case EXPLODIR:
                applyExplosionStyle();
                break;
            default:
                applyDefaultStyle();
        }

    }

    private void ApplyOpenStyle() {

        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createLineBorder(Color.GRAY ));

        if(campo.isMinado()) {
            setBackground(BG_EXPLODIR);
            return;
        }

        switch(campo.minasNaVizinhanca()) {
            case 1:
                setForeground(TEXTO_VERDE);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
            case 5:
            case 6:
            default:
                setForeground(Color.PINK);
        }

        String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";
        setText(valor);

    }

    private void applyMarkedStyle() {
        setBackground(BG_MARCAR);
        setForeground(Color.BLACK);
        setText("M");

    }

    private void applyExplosionStyle() {

        setBackground(BG_EXPLODIR);
        setForeground(Color.WHITE);
        setText("*");

    }

    private void applyDefaultStyle() {
        setBorder(BorderFactory.createBevelBorder(0));
        setBackground(BG_PADRAO);
        setText("");

    }


    //Interface dos eventos do mouse


    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1) {
            campo.abrir();
        } else {
            campo.alternarMarcacao();
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {  }
}
