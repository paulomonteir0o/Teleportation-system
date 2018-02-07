package com.paulomonteir0o.tpa.manager;

import java.util.ArrayList;
import java.util.List;

import com.paulomonteir0o.tpa.Core;

public class TpaManager {

	private String jogador;
	private String target;
	
	public TpaManager(String jogador, String target) {
		this.setJogador(jogador);
		this.setTarget(target);
		Core.tpa.add(this);
	}

	public void remove() {
		jogador = null;
		target = null;
		
		Core.tpa.remove(this);
	}
	
	public String getJogador() {
		return jogador;
	}

	public void setJogador(String jogador) {
		this.jogador = jogador;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public static TpaManager getLast(String name) {
		List<TpaManager> lista = new ArrayList<>();
		TpaManager toReturn = null;
		for (TpaManager tpas : Core.tpa) {
			if (tpas.getTarget().equals(name)) {
				lista.add(tpas);
				return lista.get(lista.size() - 1);
			}
			
		}
		return toReturn;
	}
	
	
	public static TpaManager get(String name) {
		TpaManager toReturn = null;
		for (TpaManager tpas : Core.tpa) {
			if (tpas.getJogador().equalsIgnoreCase(name)) {
				toReturn = tpas;
			}
		}
		return toReturn;
	}
	
}
