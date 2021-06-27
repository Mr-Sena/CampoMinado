package br.com.sunBoy.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int linha;
	private final int coluna;
	private boolean minado;
	private boolean aberto;
	private boolean marcado;
	
	private List<Campo> vizinhos = new ArrayList();
	private List<ObserverField> observadores = new ArrayList<>();
	
	public Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}


	public void registrarObservadores(ObserverField observador) {
		observadores.add(observador);
	}

	private void notificarObservadores(EventField evento) {

		observadores.stream()
				    .forEach(o -> o.eventoOcorreu(this, evento));

	}
	
	
	public boolean adicionarVizinho(Campo vizinho) {
		
		boolean linhaDiferente = linha != vizinho.linha;
		
		boolean colunaDiferente = coluna != vizinho.coluna;
		
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;  
		} else return false;
		
	}
	
	

	public void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;

			if (marcado) {
				notificarObservadores(EventField.MARCAR);
			} else {
				notificarObservadores(EventField.DESMARCAR);
			}
		}
	}
	
	
	public boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(vizinho -> vizinho.minado);
	}
	
	
	
	public boolean abrir() {
		if (!aberto && !marcado) {

			if(minado) {
				notificarObservadores(EventField.EXPLODIR);
				return true;
			}

			setAberto(true); //Nesse setter method, existe um comando para notificar os observadores o evetno ABRIR

			
			if(vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir() );
			}
			
			return true; 
		}
		
		return false;
	}
	
	
	public void minar() {
		minado = true;
	}
	
	
	
	
	public boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	public int minasNaVizinhanca() {
		
		return (int) vizinhos.stream().filter(v -> v.minado).count();
		
	}
	
	
	public void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObservadores(EventField.REINICIAR);
	}
	

	
	
	
	

	
	
	
	
	
	//Getters and Setters
	
	
	public boolean isMinado() {
		return minado;
	}


	public void setMinado(boolean minado) {
		this.minado = minado;
	}


	public List<Campo> getVizinhos() {
		return vizinhos;
	}


	public void setVizinhos(List<Campo> vizinhos) {
		this.vizinhos = vizinhos;
	}


	public int getLinha() {
		return linha;
	}


	public int getColuna() {
		return coluna;
	}
	
	
	
	
	public boolean isAberto() {
		return aberto;
	}
	public boolean isFechado() {
		return !isAberto();
	}
	
	


	public void setAberto(boolean aberto) {
		this.aberto = aberto;

		if (aberto) {
			notificarObservadores(EventField.ABRIR);
		}
	}


	public boolean isMarcado() {
		return marcado;
	}


	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}


	
	
	
	
	
}
