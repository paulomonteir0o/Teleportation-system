package com.paulomonteir0o.tpa;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

import com.paulomonteir0o.tpa.manager.TpaManager;

public class Core extends JavaPlugin{

	public static JavaPlugin plugin;
	public static ArrayList<TpaManager> tpa = new ArrayList<>();
	
	
	@Override
	public void onEnable() {
		plugin = this;
	
	    File f = new File("plugins/MagmaTpa");
	    if (!f.exists()) {
	      f.mkdirs();
	    }
	    
		new CommandBuilder("com.paulomonteir0o.tpa.comandos", this).loadCommands();;
	}

}
