package com.paulomonteir0o.tpa.api;

import org.bukkit.Bukkit;

public class Messages {

	public static void sendConsoleMessageSucess(String message) {
		Bukkit.getServer().getConsoleSender().sendMessage("�6[MAGMATPA] �e"+message);
	}
	
	public static void sendConsoleMessageError(String message) {
		Bukkit.getServer().getConsoleSender().sendMessage("�4[MAGMATPA] �c"+message);
	}
	
	public static String NO_HAS_PERMISSION(Grupos grupo) {
		return "�cVoc� precisa do grupo "+grupo+" ou superior para fazer isso.";
	}
	
	public static String DEFAULT_CMD(String command){
		return "�cComando correto: " + command + ".";
	}

	public static String INUTILIZABLE_WITH_CONSOLE() {
		return "�cEsse comando n�o est� dispon�vel para console.";
	}
	
	public static String PLAYER_OFFLINE() {
		return "�cEsse(a) jogador(a) n�o foi localizado(a) dentro do servidor.";
	}
	

}
