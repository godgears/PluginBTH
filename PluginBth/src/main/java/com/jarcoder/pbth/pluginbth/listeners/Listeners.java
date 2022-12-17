package com.jarcoder.pbth.pluginbth.listeners;


import com.jarcoder.pbth.pluginbth.db.Database;
import com.jarcoder.pbth.pluginbth.model.PlayerModel;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class Listeners implements Listener {

    private final Database database;

    public Listeners(Database database) {
        this.database = database;
    }

    public PlayerModel getPlayerModelFromDatabase(Player player) throws SQLException {

        PlayerModel PlayerModel = database.findPlayerModelByUUID(player.getUniqueId().toString());

        if (PlayerModel == null) {
            PlayerModel = new PlayerModel(player.getUniqueId().toString(), 0, 0, player.getDisplayName().toString(), new Date(), new Date());
            database.createPlayerModel(PlayerModel);
        }

        return PlayerModel;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Calendar fecha = Calendar.getInstance();
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        Player p = event.getPlayer();
        try{
            PlayerModel PlayerModel = getPlayerModelFromDatabase(p);
            int diac= PlayerModel.getDias();
            int mesc =PlayerModel.getMes();
            PlayerModel.setLastLogin(new Date());
            database.updatePlayerModel(PlayerModel);
            if(diac==dia&& mesc==mes)
            {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/lp user"+p.getName()+"permission settemp group.cum true 1d");
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Could not update player stats after join.");
        }

    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){

        Player p = e.getPlayer();
        try{
            PlayerModel PlayerModel = getPlayerModelFromDatabase(p);
            PlayerModel.setLastLogout(new Date());
            database.updatePlayerModel(PlayerModel);
        }catch (SQLException e1){
            e1.printStackTrace();
            System.out.println("Could not update player stats after quit.");
        }

    }

}
