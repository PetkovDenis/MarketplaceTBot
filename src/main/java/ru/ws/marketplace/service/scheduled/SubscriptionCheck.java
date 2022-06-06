package ru.ws.marketplace.service.scheduled;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.model.TUser;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;
import ru.ws.marketplace.service.impl.CRUDUserServiceImpl;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Service
@EnableScheduling
public class SubscriptionCheck {

    private final CRUDUserServiceImpl crudUserService;
    private final CRUDChannelServiceImpl crudChannelService;
    private final CheckerUsers checkerUsers;

    @Value("${postgreSQL.url}")
    private String url;
    private final String user = "postgres";
    private final String password = "postgres";

    @Value("${bot.token}")
    private String botToken;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public SubscriptionCheck(CRUDUserServiceImpl crudUserService, CRUDChannelServiceImpl crudChannelService, CheckerUsers checkerUsers) {
        this.crudUserService = crudUserService;
        this.crudChannelService = crudChannelService;
        this.checkerUsers = checkerUsers;
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void checkerUser() {

        String query = "select id,end_date,chat_id,channel_name, from users";

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Long userId = resultSet.getLong(1);
                Date date = resultSet.getDate(2);
                Long id = resultSet.getLong(3);
                String channelName = resultSet.getString(4);
                Calendar calendar = new GregorianCalendar();

                if (date.equals(calendar.getTime())) {
                    TChannel channel = crudChannelService.findByName(channelName);
                    Long groupId = channel.getGroupId();
                    deleteUsers(id, groupId);
                    TUser user = crudUserService.get(userId);
                    user.setStatus("blocked");
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

    public void deleteUsers(Long id, Long groupId) {
        String url = "https://api.telegram.org/bot" + botToken + "/banChatMember?chat_id=" + groupId + "&user_id=" + id;
        checkerUsers.createRequest(url);
    }
}

