package com.jarcoder.pbth.pluginbth.model;

import java.util.Date;

public class PlayerModel {

    private String playerUUID;

    //random stats on each player
    private int dia;
    private int mes;
    private String username;
    //last login and logout times
    private Date lastLogin;
    private Date lastLogout;

    public PlayerModel(String playerUUID, int dia, int mes, String username, Date lastLogin, Date lastLogout) {
        this.playerUUID = playerUUID;
        this.dia = dia;
        this.mes = mes;
        this.username = username;
        this.lastLogin = lastLogin;
        this.lastLogout = lastLogout;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public int getDias() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastLogout() {
        return lastLogout;
    }

    public void setLastLogout(Date lastLogout) {
        this.lastLogout = lastLogout;
    }
}
