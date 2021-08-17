package jdbc.connect;

import ru.ufagkb21.Person;

import java.sql.*;

public class JDBCconnetion {
    private String userName = "root";
    private String password = "#)1180Aidar";
    private String connectionUrl = "jdbc:mysql://localhost:3306/qrcode";


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
            System.out.println("have a connect");
            System.out.println(person.getPatient() );
            System.out.println(person.getOnlyDateOfBirth());
            System.out.println(person.getOnlyPasNumber());
            System.out.println(person.getOnlyDateResult());
            System.out.println(person.getOnlyReportNumber());
            System.out.println(person.getOnlyProdNumber());

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
               System.out.println("we're connected");
            System.out.println("Создана база данных с именем " + nameTable);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
