package ru.ws.marketplace.service.scheduled;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.service.impl.CRUDUserServiceImpl;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Service
@EnableScheduling
public class SubscriptionCheck {

    private final CRUDUserServiceImpl crudUserService;

    private final String url = "jdbc:postgresql://localhost:49153/postgres";
    private final String user = "postgres";
    private final String password = "postgres";

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public SubscriptionCheck(CRUDUserServiceImpl crudUserService) {
        this.crudUserService = crudUserService;
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void checkerUser() {

        String query = "select id,end_date from users";

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Date date = resultSet.getDate(2);
                Calendar calendar = new GregorianCalendar();
                if (date.equals(calendar.getTime())) {
                    crudUserService.delete((long) id);
                }
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
