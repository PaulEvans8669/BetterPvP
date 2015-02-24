package me.amatokus8669.plugin.betterpvp;



import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


// START OF CLASS =========================================================================================================================================
public class Events implements Listener {
	
	

	
	
	
	// GETTING USE OF PLUGIN.
		JavaPlugin plugin;
		public Events(BetterPvP plugin) {
			this.plugin = plugin;
		}
	// GETTING USE OF PLUGIN.
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeathEvent(PlayerDeathEvent event) {

		
		// EXP,MONEY,HONOR, ITEM TYPES
				
		String killmoneytype = plugin.getConfig().getString("onkill.money.type");
		String killexptype = plugin.getConfig().getString("onkill.experience.type");
		String killexpoption = plugin.getConfig().getString("onkill.experience.option");
		String killhonortype = plugin.getConfig().getString("onkill.honor.type");
		Integer killexp = plugin.getConfig().getInt("onkill.experience.experience");
		Double killmoney = plugin.getConfig().getDouble("onkill.money.money");
		Double killhonor = plugin.getConfig().getDouble("onkill.honor.honor");
		
		String deathhonortype = plugin.getConfig().getString("ondeath.honor.type");
		Double deathhonor = plugin.getConfig().getDouble("ondeath.honor.honor");
		
		Integer amount = plugin.getConfig().getInt("onkill.items.amount");
		String material = plugin.getConfig().getString("onkill.items.material");
		ItemStack item = new ItemStack(Material.getMaterial(material), amount);
		// EXP,MONEY,HONOR,ITEM TYPES
		
		
		
		//PLAYER TYPE
		if(!(event.getEntity().getKiller() instanceof Player)){return;}
		Player killed = event.getEntity().getPlayer();
		UUID Skilled = killed.getUniqueId();
		Player killer = event.getEntity().getPlayer().getKiller();
		UUID Skiller = killer.getUniqueId();
		//PLAYER TYPE
		
		UserConfigClass uccer = new UserConfigClass(Skiller);
		UserConfigClass ucced = new UserConfigClass(Skilled);
		
//=============================================HONNOR================================================================
		Double DHonor = (new UserConfigClass(event.getEntity().getKiller().getUniqueId())).getConfig().getDouble("Honor");
//=============================================HONNOR================================================================
		
		
	// ONKILL MESSAGE
		String onkillfullinventorypremessage = plugin.getConfig().getString("onkill.items.messages.fullinventory");
		String onkillfullinventorypostmessage = onkillfullinventorypremessage.replace("%Killed", killed.getName());
		String onkillfullinventorypostpostmessage = onkillfullinventorypostmessage.replace("%Killer", killer.getName());
		String onkillfullinventorypostpostpostmessage = onkillfullinventorypostpostmessage.replace("%Item", plugin.getConfig().getString("onkill.items.material"));
		String onkillfullinventorypostpostpostpostmessage = onkillfullinventorypostpostpostmessage.replace("%Amount", Integer.toString(amount));
		String onkillfullinventoryfinalmessage = ChatColor.translateAlternateColorCodes('&', onkillfullinventorypostpostpostpostmessage);
		
		String onkillitemrewardpremessage = plugin.getConfig().getString("onkill.items.messages.congratulations");
		String onkillitemrewardpostmessage = onkillitemrewardpremessage.replace("%Killed", killed.getName());
		String onkillitemrewardpostpostmessage = onkillitemrewardpostmessage.replace("%Killer", killer.getName());
		String onkillitemrewardpostpostpostmessage = onkillitemrewardpostpostmessage.replace("%Item", plugin.getConfig().getString("onkill.items.material"));
		String onkillitemrewardpostpostpostpostmessage = onkillitemrewardpostpostpostmessage.replace("%Amount", Integer.toString(amount));
		String onkillitemrewardfinalmessage = ChatColor.translateAlternateColorCodes('&', onkillitemrewardpostpostpostpostmessage);
	// ONKILL MESSAGE
		
		
	// ONDEATH MESSAGE
		String ondeathpremessage = plugin.getConfig().getString("ondeath.message");
		String ondeathpostmessage = ondeathpremessage.replace("%Killed", killed.getName());
		String ondeathpostpostmessage = ondeathpostmessage.replace("%Killer", killer.getName());
		String ondeathpostpostpostmessage = ondeathpostpostmessage.replace("%Honor", Double.toString(deathhonor));
		String ondeathpostpostpostpostmessage = ondeathpostpostpostmessage.replace("%TotalHonor", Double.toString(DHonor));
		String ondeathfinalmessage = ChatColor.translateAlternateColorCodes('&', ondeathpostpostpostpostmessage);
	// ONDEATH MESSAGE
		
		
//ENABLE OPTIONS
		Boolean onkillexp = plugin.getConfig().getBoolean("enable.experience");
		Boolean eco = plugin.getConfig().getBoolean("enable.money");
		Boolean hon = plugin.getConfig().getBoolean("enable.honor");
		Boolean ite = plugin.getConfig().getBoolean("enable.items");
		String moneyform = plugin.getConfig().getString("onkill.money.form");
//ENABLE OPTIONS
		
				
		
		
// KILLER - PLAYER ===================================================================
		

		
		// MONEY - START
		Double percmoney = (BetterPvP.econ.getBalance(killed.getName())*(1+plugin.getConfig().getDouble("onkill.money.percentage")/100))-BetterPvP.econ.getBalance(killed.getName());
		if(eco == true){
			if(moneyform.equals("brute")){
				if(killmoneytype.equals("add")){
					BetterPvP.econ.depositPlayer(killer.getName(),killmoney);
				}
				else if(killmoneytype.equals("remove")){
					BetterPvP.econ.withdrawPlayer(killer.getName(),killmoney);
				}
		}
			
			if(moneyform.equals("percent")){
				if(killmoneytype.equals("add")){
										
					BetterPvP.econ.depositPlayer(killer.getName(), percmoney);
				}
				else if(killmoneytype.equals("remove")){
					BetterPvP.econ.withdrawPlayer(killer.getName(),killmoney);
				}
			}
		}
	    // MONEY - END
		
		// ITEM - START
		if(ite == true){
			HashMap<Integer, ItemStack> leftOver = new HashMap<Integer, ItemStack>();
        leftOver.putAll((killer.getInventory().addItem(new ItemStack(item.getType(), amount))));
        if (leftOver.isEmpty()){killer.sendMessage(onkillitemrewardfinalmessage);}
        if (!leftOver.isEmpty()) {
            Location loc = killer.getLocation();
            killer.getWorld().dropItem(loc, new ItemStack(Material.getMaterial(leftOver.get(0).getTypeId()), leftOver.get(0).getAmount()));
            killer.sendMessage(onkillfullinventoryfinalmessage);
        }
			

		//ITEM - END
		
	    // EXP - START
	    if(onkillexp == true){
	    	if(killexptype.equals("add")){
	    		if(killexpoption.equals("expbar")){
	    		killer.setExp(killer.getExp()+((float)0.01*killexp));}
	    		else if(killexpoption.equals("explevel")){
	    		killer.setLevel(killer.getLevel()+killexp);}
	    	}
	    	else if(killexptype.equals("remove")){
	    		if(killexpoption.equals("expbar")){
		    	killer.setExp(killer.getExp()-((float)0.01*killexp));}
	    		else if(killexpoption.equals("explevel")){
	    		killer.setLevel(killer.getLevel()-killexp);}
	    		
	    	}
	    }
	    // EXP - END

		// HONOR - START
		if(hon == true){
			if(killhonortype.equals("add")){
				uccer.getConfig().getDouble("Honor");
				uccer.getConfig().set("Honor", uccer.getConfig().getDouble("Honor")+killhonor);
				uccer.save();
			}
			else if(killhonortype.equals("remove")){
				uccer.getConfig().getDouble("Honor");
				uccer.getConfig().set("Honor", uccer.getConfig().getDouble("Honor")-killhonor);
				uccer.save();
			}
		}
		// HONOR - END
		
	    // MESSAGE - START
		String onkillpremessage = plugin.getConfig().getString("onkill.message");
		String onkillpostmessage = onkillpremessage.replace("%Killed", killed.getName());
		String onkillpostpostmessage = onkillpostmessage.replace("%Killer", killer.getName());
		String onkillpostpostpostmessage = onkillpostpostmessage.replace("%Honor", Double.toString(killhonor));
		if(plugin.getConfig().getString("onkill.money.form").equals("brute")){
		String onkillpostpostpostpostmessage = onkillpostpostpostmessage.replace("%Money", Double.toString(killmoney));
		String onkillpostpostpostpostpostmessage = onkillpostpostpostpostmessage.replace("%TotalHonor", Double.toString(DHonor));
		String onkillpostpostpostpostpostpostmessage = onkillpostpostpostpostpostmessage.replace("%Experience", Integer.toString(killexp));
		String onkillfinalmessage = ChatColor.translateAlternateColorCodes('&', onkillpostpostpostpostpostpostmessage);
		killer.sendMessage(onkillfinalmessage);
		}
		if(plugin.getConfig().getString("onkill.money.form").equals("percent")){
		String onkillpostpostpostpostmessage = onkillpostpostpostmessage.replace("%Money", Double.toString(percmoney));
		String onkillpostpostpostpostpostmessage = onkillpostpostpostpostmessage.replace("%TotalHonor", Double.toString(DHonor));
		String onkillpostpostpostpostpostpostmessage = onkillpostpostpostpostpostmessage.replace("%Experience", Integer.toString(killexp));
		String onkillfinalmessage = ChatColor.translateAlternateColorCodes('&', onkillpostpostpostpostpostpostmessage);
		killer.sendMessage(onkillfinalmessage);
		}
	    // MESSAGE - END
	    
// KILLER - PLAYER ===================================================================
	    
	    
// KILLED - PLAYER ===================================================================

	    
	    
		// HONOR - START
	    if(hon == true){

	    	if(deathhonortype.equals("add")){
	    		ucced.getConfig().getInt("Honor");
	    		ucced.getConfig().set("Honor", ucced.getConfig().getInt("Honor")+deathhonor);
	    		ucced.save();
	    	}
	    	else if(deathhonortype.equals("remove")){
	    		ucced.getConfig().getInt("Honor");
	    		ucced.getConfig().set("Honor", ucced.getConfig().getInt("Honor")-deathhonor);
	    		ucced.save();
	    	}
	    }
		// HONOR - END
	    
	    // MESSAGE - START
	    killed.sendMessage(ondeathfinalmessage);
	    // MESSAGE - END
	    
	    
	    

	    
// KILLED - PLAYER ===================================================================
	    


	    }
    }
}	


// END OF CLASS ===========================================================================================================================================
