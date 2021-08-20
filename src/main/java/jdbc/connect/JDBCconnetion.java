package jdbc.connect;

import ru.ufagkb21.Person;

import java.sql.*;
import java.util.logging.Logger;

import static jdbc.connect.JdbcSetings.*;

public class JDBCconnetion {

    private static final Logger LOGGER = Logger.getLogger( JDBCconnetion.class.getName() );



//    public void findPeople (String ) {
//
//    }
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM Mh_users");
//            while (resultSet.next()) {
//                System.out.print(resultSet.getInt(1) + " ");
//                System.out.print(resultSet.getString(2) + " ");
//                System.out.print(resultSet.getString(3) + " ");
//                System.out.print(resultSet.getString(5) + " ");
//                System.out.println();
//            }

    public void appendPeople (Person person) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("INSERT INTO savepeople (fullName, dateBirth, passport, dateResult, numberReport, numberProduction) VALUES ('"
                    + person.getPatient() + "','" + person.getOnlyDateOfBirth() + "','"
                    + person.getOnlyPasNumber() + "','" + person.getOnlyDateResult() + "','"
                    + person.getOnlyReportNumber() + "','" + person.getOnlyProdNumber() + "');");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void createTable (String nameTable) throws ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE "+ nameTable + " (" +
                    "id MEDIUMINT NOT NULL AUTO_INCREMENT," +
                    "lastname VARCHAR(20) NOT NULL," +
                    "name VARCHAR(20) NOT NULL," +
                    "date_birth DATE NOT NULL," +
                    "passport INT NOT NULL," +
                    "date_result DATE NOT NULL," +
                    "number_report INT NOT NULL," +
                    "number_production MEDIUMINT NOT NULL," +
                    "constraint savepeople_pk PRIMARY KEY (id));");

            System.out.println("Создана база данных с именем " + nameTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
