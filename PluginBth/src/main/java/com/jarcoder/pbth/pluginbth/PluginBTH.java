package com.jarcoder.pbth.pluginbth;

import com.jarcoder.pbth.pluginbth.db.Database;
import com.jarcoder.pbth.pluginbth.listeners.Listeners;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class PluginBTH extends JavaPlugin {

    private Database database;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Plugin de cumpleaños Iniciado :)");
        //Config stuff
        saveDefaultConfig();

        //JDBC - Java Database Connectivity API
        this.database = new Database(getConfig().getString("database.host"), getConfig().getString("database.port"), getConfig().getString("database.user"), getConfig().getString("database.password"), getConfig().getString("database.database_name"));
        try {
            this.database.initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No se pudo inicializar base de datos");
        }

        getServer().getPluginManager().registerEvents(new Listeners(database), this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (sender instanceof Player && label.equals("cumple")&& args.length == 2){
            int dia = Integer.parseInt(args[0]);
            int mes = Integer.parseInt(args[1]);
            if(dia <=31 && mes <=12)
            {
                p.sendMessage("Tu Cumpleaños es el dia: " + dia + " del mes: " + mes);
                try {
                    this.database.updateBthPlayer(dia, mes, String.valueOf(p.getUniqueId()));
                    p.sendMessage("se actualizo la fecha de tu cumpleaños");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }else{
                p.sendMessage("Ingresaste valores erroneos. Verifica el formato");
                return false;
            }
        }
        else {
            p.sendMessage("Ingresa tu dia y mes de cumpleaños con numeros ej: [dia 1-31] y [mes 1-12]");
            return false;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Plugin de cumpleaños Apagado :(");
    }
}
