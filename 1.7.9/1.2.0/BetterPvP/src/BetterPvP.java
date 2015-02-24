package me.amatokus8669.plugin.betterpvp;

import java.util.UUID;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterPvP extends JavaPlugin {

	private static final Logger log = Logger.getLogger("Minecraft");
	public static Economy econ = null;
	private boolean ecoworks = false;

 
	
	@Override
	public void onEnable() {

		this.saveDefaultConfig();
		UserConfigClass.setPlugin(this);

		if (!setupEconomy()) {
			log.severe(String
					.format("[%s] - Economy Disabled due to no Vault dependency found!",
							getDescription().getName()));
			return;
		} else {
			ecoworks = true;
		}

		Listener eventsListener = new Events(this);
		Bukkit.getServer().getPluginManager().registerEvents(eventsListener, this);
		getLogger().info("Enabling the BetterPvP Plugin");
		getLogger().info("Check bukkit DevPage for updates!");
	}



	@Override
	public void onDisable() {
		log.info(String.format("[%s] Disabled Version %s", getDescription()
				.getName(), getDescription().getVersion()));
	}


	
	
	
	
	// Vault economy soft-depend
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	// Vault economy soft-depend - END
	
	// COMMANDS - START ===================================================================

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("honor")) {
			if(sender instanceof Player){
			if(args.length == 0){
				sender.sendMessage(ChatColor.RED+"You"+ChatColor.GREEN+" "+"actualy have"+" "+ChatColor.RED+""+(new UserConfigClass(((Player) sender).getPlayer().getUniqueId())).getConfig().getDouble("Honor")+ChatColor.GREEN+" "+"honor!");
			
			} 
			else {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					sender.sendMessage("The player you pointed at is not online.");
				}else{
					sender.sendMessage(ChatColor.RED+args[0]+" "+ChatColor.GREEN+"actualy"+" "+"has"+" "+ChatColor.RED+""+(new UserConfigClass(target.getUniqueId())).getConfig().getDouble("Honor")+" "+ChatColor.GREEN+"honor!");
				}
			}
			
		
		}
		
		if(sender instanceof ConsoleCommandSender){
			if(args.length == 0){
				sender.sendMessage("Please point at a player using honor (player)");
			
			}
			else {
				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					sender.sendMessage("The player you pointed at is not online.");
				}else{
					sender.sendMessage(args[0]+" actualy has "+(new UserConfigClass(getServer().getPlayer(args[0]).getUniqueId())).getConfig().getDouble("Honor")+" honor!");
				}
		}
		}
		}
		return false;
}

// COMMANDS - END ===================================================================	
	
	
	

}
