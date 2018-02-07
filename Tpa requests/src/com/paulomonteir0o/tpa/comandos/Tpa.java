package com.paulomonteir0o.tpa.comandos;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.paulomonteir0o.tpa.Core;
import com.paulomonteir0o.tpa.api.Messages;
import com.paulomonteir0o.tpa.manager.TpaManager;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Tpa extends Command {

	public static String nome = "tpa";

	public Tpa(String name) {
		super(name);
		this.setAliases(Arrays.asList("call"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender sender, String arg, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.INUTILIZABLE_WITH_CONSOLE());
			return true;
		}

		Player p = (Player) sender;

		if (args.length == 0) {
			sender.sendMessage(Messages.DEFAULT_CMD("/tpa <aceitar/negar/player> [player]"));
			return true;
		} else if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("cancelar")) {
					if (TpaManager.get(p.getName()) != null) {
						TpaManager.get(p.getName()).remove();
						p.sendMessage("§cPedido de teletransporte foi cancelado.");
					}else {
						p.sendMessage("§cVocê não tem nenhum pedido de teletransporte.");
					}
				return true;
			}
			if(args[0].equalsIgnoreCase("negar")) {
				if(args.length == 1) {
					if(TpaManager.getLast(p.getName()) != null) {
						Player PLAYER_SELECTED = Bukkit.getPlayer(TpaManager.getLast(p.getName()).getJogador());
						TpaManager.getLast(p.getName()).remove();
						p.sendMessage("§cVocê negou o pedido de teletransporte de(a) " + PLAYER_SELECTED.getName() + ".");
						PLAYER_SELECTED.sendMessage("§c" + p.getName() + " negou o seu pedido de teletransporte.");
						return true;
					}else{
						p.sendMessage("§cVocê não tem nenhum pedido de teletransporte.");
					return true;
					}
				}else if(args.length == 2){
					Player PLAYER_SELECTED = Bukkit.getPlayer(args[1]);
					if(PLAYER_SELECTED == null) {
						p.sendMessage(Messages.PLAYER_OFFLINE());
						return true;
					}
					
					if(PLAYER_SELECTED == p) {
						p.sendMessage("§cVocê não pode negar pedido de teletransporte de você mesmo.");
						return true;
					}
					
					if (TpaManager.get(PLAYER_SELECTED.getName()) == null
							|| !TpaManager.get(PLAYER_SELECTED.getName()).getTarget().equals(p.getName())) {
						p.sendMessage("§cEsse jogador não fez nenhum pedido de teletransporte para você.");
						return true;
					}
					
					TpaManager.get(PLAYER_SELECTED.getName()).remove();
					p.sendMessage("§cPedido de teletransporte de(a) " + PLAYER_SELECTED.getName() + " foi negado.");
					PLAYER_SELECTED.sendMessage("§c" + p.getName() + "§c negou seu pedido de teletransporte.");
					return true;
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("aceitar")) {
				if (args.length == 1) {
					if(TpaManager.getLast(p.getName()) != null) {
						Player PLAYER_SELECTED = Bukkit.getPlayer(TpaManager.getLast(p.getName()).getJogador());
						TpaManager.getLast(p.getName()).remove();
						if (PLAYER_SELECTED.hasPermission("tpa.delay") || PLAYER_SELECTED.isOp() == true) {
							p.sendMessage("§aPedido de teletransporte do(a) jogador(a) " + PLAYER_SELECTED.getName()
									+ " foi aceito.");
							PLAYER_SELECTED
									.sendMessage("§aVocê será teletransportado para o(a) jogador(a) " + p.getName() + ".");
							PLAYER_SELECTED
									.sendMessage("§aVocê foi teletransportado até o(a) jogador(a) " + p.getName() + "§a.");
							p.sendMessage("§a" + PLAYER_SELECTED.getName() + " §afoi teletransportado até você.");
							PLAYER_SELECTED.teleport(p.getLocation());
							return true;
						} else {
							p.sendMessage("§aPedido de teletransporte do(a) jogador(a) " + PLAYER_SELECTED.getName()
									+ " foi aceito.");
							PLAYER_SELECTED.sendMessage("§aVocê será teletransportado para o(a) jogador(a) " + p.getName()
									+ " em 3 segundos...");

							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Core.plugin, new BukkitRunnable() {

								@Override
								public void run() {
									PLAYER_SELECTED.teleport(p.getLocation());
									PLAYER_SELECTED.sendMessage(
											"§aVocê foi teletransportado até o(a) jogador(a) " + p.getName() + "§a.");
									p.sendMessage("§a" + PLAYER_SELECTED.getName() + " §afoi teletransportado até você.");
								}
							}, 20l * 3);
						}
					}else{
						p.sendMessage("§cVocê não tem nenhum pedido de teletransporte.");
					}
					
				} else if (args.length == 2) {
					Player PLAYER_SELECTED = Bukkit.getPlayer(args[1]);

					if (PLAYER_SELECTED == null) {
						p.sendMessage(Messages.PLAYER_OFFLINE());
						return true;
					}

					if (TpaManager.get(PLAYER_SELECTED.getName()) == null
							|| !TpaManager.get(PLAYER_SELECTED.getName()).getTarget().equals(p.getName())) {
						p.sendMessage("§cEsse jogador não fez nenhum pedido de teletransporte para você.");
						return true;
					}

					TpaManager.get(PLAYER_SELECTED.getName()).remove();

					if (PLAYER_SELECTED.hasPermission("tpa.delay") || PLAYER_SELECTED.isOp() == true) {
						p.sendMessage("§aPedido de teletransporte do(a) jogador(a) " + PLAYER_SELECTED.getName()
								+ " foi aceito.");
						PLAYER_SELECTED
								.sendMessage("§aVocê será teletransportado para o(a) jogador(a) " + p.getName() + ".");
						PLAYER_SELECTED
								.sendMessage("§aVocê foi teletransportado até o(a) jogador(a) " + p.getName() + "§a.");
						p.sendMessage("§a" + PLAYER_SELECTED.getName() + " §afoi teletransportado até você.");
						PLAYER_SELECTED.teleport(p.getLocation());
						return true;
					} else {
						p.sendMessage("§aPedido de teletransporte do(a) jogador(a) " + PLAYER_SELECTED.getName()
								+ " foi aceito.");
						PLAYER_SELECTED.sendMessage("§aVocê será teletransportado para o(a) jogador(a) " + p.getName()
								+ " em 3 segundos...");

						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Core.plugin, new BukkitRunnable() {

							@Override
							public void run() {
								PLAYER_SELECTED.teleport(p.getLocation());
								PLAYER_SELECTED.sendMessage(
										"§aVocê foi teletransportado até o(a) jogador(a) " + p.getName() + "§a.");
								p.sendMessage("§a" + PLAYER_SELECTED.getName() + " §afoi teletransportado até você.");
							}
						}, 20l * 3);
					}
					return true;
				}
				return true;
			}

		}

		Player PLAYER_SELECTED = Bukkit.getPlayer(args[0]);
		if (PLAYER_SELECTED == null) {
			p.sendMessage(Messages.PLAYER_OFFLINE());
			return true;
		}

		if (PLAYER_SELECTED == p) {
			p.sendMessage("§cVocê não pode fazer um pedido de teletransporte para você mesmo.");
			return true;
		}
		
		if (TpaManager.get(p.getName()) != null) {
			p.sendMessage("§cVocê já enviou um pedido de teletransporte. Aguarde para enviar novamente!");
			return true;
		}
		new TpaManager(p.getName(), PLAYER_SELECTED.getName());

		PLAYER_SELECTED.sendMessage(" ");
		PLAYER_SELECTED.sendMessage("§7" + p.getName() + " §eestá pedindo para teletransportar até você.");
		TextComponent a = new TextComponent("§eclique");
		a.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ""));
		TextComponent b = new TextComponent(" §a§lAQUI§r");
		b.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa aceitar " + p.getName()));
		TextComponent c = new TextComponent("§e para aceitar e ");
		c.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ""));
		TextComponent d = new TextComponent("§c§lAQUI§r");
		d.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa negar " + p.getName()));
		TextComponent e = new TextComponent("§e para negar.");
		e.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ""));
		a.addExtra(b);
		b.addExtra(c);
		c.addExtra(d);
		d.addExtra(e);
		PLAYER_SELECTED.spigot().sendMessage(a);
		PLAYER_SELECTED.sendMessage(" ");

		TextComponent g = new TextComponent("§eClique ");
		TextComponent f = new TextComponent("§c§lAQUI ");
		TextComponent h = new TextComponent("§epara cancelar o teletransporte.");
		f.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa cancelar"));
		g.addExtra(f);
		f.addExtra(h);
		p.sendMessage("");
		p.sendMessage("§ePedido de teletransporte enviado para §7" + PLAYER_SELECTED.getName());
		p.spigot().sendMessage(g);
		p.sendMessage("");

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Core.plugin, new BukkitRunnable() {

			@Override
			public void run() {
				if (TpaManager.get(PLAYER_SELECTED.getName()) == null
						|| !TpaManager.get(PLAYER_SELECTED.getName()).getTarget().equals(p.getName()))
					return;

				p.sendMessage(
						"§cPedido de teletransporte para o(a) jogador(a) " + PLAYER_SELECTED.getName() + " §cexpirou.");
				PLAYER_SELECTED
						.sendMessage("§cPedido de teletransporte do(a) jogador(a) " + p.getName() + " §cexpirou.");
				TpaManager.get(p.getName()).remove();
			}
		}, 20 * 60);

		return false;
	}

}