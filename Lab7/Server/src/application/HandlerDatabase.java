package application;

import collection.HumanList;
import elements.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;

public class HandlerDatabase {
    private Connection connection;
    private Context context;

    public HandlerDatabase(Context context) {
        this.context = context;
    }

    public void initialization(String url) throws SQLException, ClassNotFoundException {
        initialization(url, null, null);
    }

    public void initialization(String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        if(user == null || password == null) {
            connection = DriverManager.getConnection(url);
        }
        else {
            connection = DriverManager.getConnection(url, user, password);
        }
        if (connection == null) {
            throw new SQLException();
        }
    }

    public HumanList getHumanList() throws SQLException {
        HumanList humanList = new HumanList(context);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM humans");
        while (resultSet.next()) {
            int idUser = resultSet.getInt(1);
            int id = resultSet.getInt(2);
            String name = resultSet.getString(3);
            float coordinateX = resultSet.getFloat(4);
            float coordinateY = resultSet.getInt(5);
            String creationDate = resultSet.getString(6);
            boolean realHero = resultSet.getBoolean(7);
            boolean hasToothpick = resultSet.getBoolean(8);
            float impactSpeed = resultSet.getFloat(9);
            String weaponType = resultSet.getString(10);
            String mood = resultSet.getString(11);
            String carName = resultSet.getString(12);
            try {
                humanList.addFromDatabase(new HumanBeing(idUser, id, name, new Coordinates(coordinateX, coordinateY),
                        LocalDate.parse(creationDate), realHero, hasToothpick, impactSpeed, WeaponType.valueOf(weaponType),
                        Mood.valueOf(mood), new Car(carName)));
            } catch (Exception e) {
                throw new SQLException();
            }
        }
        return humanList;
    }

    public HumanBeing addHumanBeing(HumanBeing human) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO humans VALUES (?, DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, human.getIdUser());
        preparedStatement.setString(2, human.getName());
        preparedStatement.setFloat(3, human.getCoordinates().getX());
        preparedStatement.setFloat(4, human.getCoordinates().getY());
        preparedStatement.setString(5, human.getCreationDate().toString());
        preparedStatement.setBoolean(6, human.getRealHero());
        preparedStatement.setBoolean(7, human.getHasToothpick());
        preparedStatement.setFloat(8, human.getImpactSpeed());
        preparedStatement.setString(9, human.getWeaponType().toString());
        preparedStatement.setString(10, human.getMood().toString());
        preparedStatement.setString(11, human.getCar().getName());
        preparedStatement.executeUpdate();
        ResultSet set = preparedStatement.getGeneratedKeys();
        if (set.next()) {
            human.setId(set.getInt(set.findColumn("id")));
        }
        else {
            throw new SQLException();
        }
        preparedStatement.close();
        return human;
    }

    public void updateHuman(HumanBeing human) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE humans SET id_user = ?, id = ?, name = ?, coordinates_x = ?, coordinates_y = ?, creationdate = ?, realhero = ?, hastoothpick = ?, impactspeed = ?, weapontype = ?, mood = ?, car_name = ? WHERE id = ?");
        preparedStatement.setInt(1, human.getIdUser());
        preparedStatement.setInt(2, human.getId());
        preparedStatement.setString(3, human.getName());
        preparedStatement.setFloat(4, human.getCoordinates().getX());
        preparedStatement.setFloat(5, human.getCoordinates().getY());
        preparedStatement.setString(6, human.getCreationDate().toString());
        preparedStatement.setBoolean(7, human.getRealHero());
        preparedStatement.setBoolean(8, human.getHasToothpick());
        preparedStatement.setFloat(9, human.getImpactSpeed());
        preparedStatement.setString(10, human.getWeaponType().toString());
        preparedStatement.setString(11, human.getMood().toString());
        preparedStatement.setString(12, human.getCar().getName());
        preparedStatement.setInt(13, human.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void removeHuman(HumanBeing human) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM humans WHERE id = ?");
        preparedStatement.setInt(1, human.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void registrationUser(String login, String password) throws NoSuchAlgorithmException, SQLException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-224");
        byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (DEFAULT , ?, ?)");
        preparedStatement.setString(1, login);
        String hash = new String(bytes, StandardCharsets.UTF_8);
        preparedStatement.setString(2, hash);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public int isExistingUser(String login, String password) throws SQLException, NoSuchAlgorithmException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            int idUser = resultSet.getInt(1);
            String databasePass = resultSet.getString(3);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-224");
            byte[] passwordBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            String inputPass = new String(passwordBytes, StandardCharsets.UTF_8);

            if(inputPass.equals(databasePass)) {
                return idUser;
            }
            else {
                throw new SQLException();
            }
        }
        else {
            throw new SQLException();
        }
    }
}
