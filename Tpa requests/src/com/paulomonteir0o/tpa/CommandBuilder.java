package com.paulomonteir0o.tpa;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.paulomonteir0o.tpa.api.Messages;
	
public class CommandBuilder {

	private String pacote;
	private Plugin main;
	private String version;

	public CommandBuilder(String pacote, Plugin main) {
		this.pacote = pacote;
		this.main = main;
		this.version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}

	public void loadCommands() {
		try {
			ImmutableSet<ClassInfo> classes = ClassPath.from(main.getClass().getClassLoader()).getAllClasses();
			classes.forEach(classe -> {
				if (classe.getPackageName().equalsIgnoreCase(pacote)) {
					Class<?> loaded = classe.load();
					if (loaded.getSuperclass().equals(Command.class)) {
						try {
							String nome = null;
							if (loaded.getFields().length >= 1) {
								for (Field f : loaded.getFields()) {
									if (f.getName().equalsIgnoreCase("nome")) {
										nome = (String) loaded.getDeclaredField("nome").get(loaded);
										break;
									}
								}
							}
							if (nome != null) {

								Class<?> server = Class
										.forName("org.bukkit.craftbukkit." + this.version + ".CraftServer");
								Constructor<?> c = loaded.getConstructor(String.class);
								Field fmap = server.getDeclaredField("commandMap");

								fmap.setAccessible(true);
								Command comando = (Command) c.newInstance(nome);

								((CommandMap) fmap.get(Bukkit.getServer())).register(nome, comando);
								Messages.sendConsoleMessageSucess("Classe: §f" + classe.getName() + " §ecarregada com sucesso.");
							} else {
								Messages.sendConsoleMessageError("Não foi possivel carregar a classe: §f" + classe.getName() + "§c.");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
