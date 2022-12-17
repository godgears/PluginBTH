package com.jarcoder.pbth.pluginbth.db;


import com.jarcoder.pbth.pluginbth.model.PlayerModel;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;



public class Database {

    private final String HOST;
    private final String PORT;
    private final String USER;
    private final String PASSWORD;
    private final String DATABASE_NAME;

    public Database(String HOST, String PORT, String USER, String PASSWORD, String DATABASE_NAME) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.DATABASE_NAME = DATABASE_NAME;
    }

    private Connection connection;

    public Connection getConnection() throws SQLException {

        if(connection != null){
            return connection;
        }

        //Try to connect to my MySQL database running locally
        String url = "jdbc:mysql://"+this.HOST+"/"+this.DATABASE_NAME;
        Connection connection = DriverManager.getConnection(url, this.USER, this.PASSWORD);

        this.connection = connection;

        System.out.println("Connected to database.");

        return connection;
    }

    public void initializeDatabase() throws SQLException {

        Statement statement = getConnection().createStatement();

        //Create the player_stats table
        String sql = "CREATE TABLE IF NOT EXISTS player_cum (uuid varchar(36) primary key, dia int, mes int, username varchar(36), last_login DATE, last_logout DATE)";
        statement.execute(sql);
        statement.close();

    }

    public PlayerModel findPlayerModelByUUID(String uuid) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM player_cum WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        PlayerModel PlayerModel;

        if(resultSet.next()){

            PlayerModel = new PlayerModel(resultSet.getString("uuid"), resultSet.getInt("dia"), resultSet.getInt("mes"), resultSet.getString("username"), resultSet.getDate("last_login"), resultSet.getDate("last_logout"));

            statement.close();

            return PlayerModel;
        }

        statement.close();

        return null;
    }

    public void createPlayerModel(PlayerModel PlayerModel) throws SQLException {

        PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO player_cum(uuid, dia, mes, username, last_login, last_logout) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setString(1, PlayerModel.getPlayerUUID());
        statement.setInt(2, PlayerModel.getDias());
        statement.setInt(3, PlayerModel.getMes());
        statement.setString(4, PlayerModel.getUsername());
        statement.setDate(5, new Date(PlayerModel.getLastLogin().getTime()));
        statement.setDate(6, new Date(PlayerModel.getLastLogout().getTime()));
        statement.executeUpdate();

        statement.close();

    }

    public void updatePlayerModel(PlayerModel PlayerModel) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("UPDATE player_cum SET dia = ?, mes = ?, username = ?, last_login = ?, last_logout = ? WHERE uuid = ?");
        statement.setInt(1, PlayerModel.getDias());
        statement.setInt(2, PlayerModel.getMes());
        statement.setString(3, PlayerModel.getUsername());
        statement.setDate(4, new Date(PlayerModel.getLastLogin().getTime()));
        statement.setDate(5, new Date(PlayerModel.getLastLogout().getTime()));
        statement.setString(6, PlayerModel.getPlayerUUID());

        statement.executeUpdate();

        statement.close();

    }
    public void updateBthPlayer(int dia, int mes, String uuid) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("UPDATE player_cum SET dia = ?, mes = ? WHERE uuid = ?");
        statement.setInt(1, dia);
        statement.setInt(2, mes);
        statement.setString(3, uuid);
        statement.executeUpdate();
        statement.close();
    }
}
