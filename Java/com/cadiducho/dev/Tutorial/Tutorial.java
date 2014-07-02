
package com.cadiducho.dev.Tutorial;


import java.io.File;
import java.sql.Connection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class Tutorial extends JavaPlugin {
       
        private MySQL mysql = null;
        private Connection conection = null;
        @Override
        public void onEnable() {
            File config = new File(getDataFolder() + File.separator + "config.yml");
            if (!config.exists()){
                getLogger().info("Creando config");
                this.getConfig().addDefault("database", "statsbukkit");
                this.getConfig().addDefault("username", "cadi");
                this.getConfig().addDefault("password", "pass");
                this.getConfig().addDefault("host", "localhost");
                this.getConfig().addDefault("port", "3306");
                this.getConfig().options().copyDefaults(true);
                this.saveConfig();
                
            } else {
                try{
                    mysql = new MySQL(this, this.getConfig().getString("host"), 
                            this.getConfig().getString("port"),
                            this.getConfig().getString("database"),
                            this.getConfig().getString("username"),
                            this.getConfig().getString("password"));
                    conection = mysql.openConnection();
                    MySQL.crearTabla(mysql, conection, this);
                    
                    
                } catch (Exception e){
                  this.getLogger().severe("Fallo al establecer la conexion a MySQL");
                }
            }
        }
        @Override
        public void onDisable(){
            
        }
       
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
           return false;
        }
}