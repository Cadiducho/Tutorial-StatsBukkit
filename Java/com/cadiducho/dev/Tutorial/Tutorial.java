package com.cadiducho.dev.Tutorial;


import java.io.File;
import java.sql.Connection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class Tutorial extends JavaPlugin {
       
        private MySQL mysql = null;
        private Connection connection = null;
        
        @Override
        public void onEnable() {
            File config = new File(getDataFolder() + File.separator + "config.yml");
            if (!config.exists()){
                getLogger().info("Creando config");
                try {
                    getConfig().options().copyDefaults(true);
                    saveConfig();
                    getLogger().info("Se ha generado el config.yml correctamente");
                } catch (Exception e){
                    getLogger().severe("Error al generar el config.yml");
                }
            }
            /* 
             * ABRIR MYSQL
             */
            try {
                mysql = new MySQL(this, this.getConfig().getString("mysql.host"), this.getConfig().getString("mysql.port"),
					this.getConfig().getString("mysql.database"), this.getConfig().getString("mysql.username"),
					this.getConfig().getString("mysql.password"));
                connection = mysql.openConnection();
                //Crear tabla si no existe
                MySQL.crearTabla(mysql, connection, this);
            } catch (Exception e){
                getLogger().severe("No se ha podido abrir la conexion MySQL");
            }
        }
        @Override
        public void onDisable(){
            
        }
       
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
           return false;
        }
        
        public Connection getConnection(){
            return this.connection;
        }
        public MySQL getMySQL(){
            return this.mysql;
        }
}