package me.amatokus8669.plugin.betterpvp;

import java.io.File;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class UserConfigClass {
	 
    private static Plugin plugin;
    public static void setPlugin(Plugin plugin){UserConfigClass.plugin = plugin;}
 
    private FileConfiguration config;
    public FileConfiguration getConfig(){return config;}
 
    private File settingsFile;
    public File getPlayerFile(){return settingsFile;}
 
    public boolean save(){try{config.save(settingsFile);System.out.println("Saved File ");return true;}catch(Exception e){System.out.println("Couldn't Save File");return false;}}
 
    public UserConfigClass(UUID uuid){
        try{
            settingsFile = new File(plugin.getDataFolder()+File.separator+"Users"+File.separator+uuid+".yml");
            settingsFile.getParentFile().mkdirs();
            config = YamlConfiguration.loadConfiguration(settingsFile);
            config.options().copyDefaults(true);
            if(!config.contains("Honor")){config.addDefault("Honor", 0);}
            config.save(settingsFile);
        }catch(Exception e){e.printStackTrace();System.out.println("Really messed up");}
 
    }
}
