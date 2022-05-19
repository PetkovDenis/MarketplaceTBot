package ru.ws.marketplace.service.scheduled;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TAdmin;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.model.TUser;
import ru.ws.marketplace.service.impl.CRUDAdminServiceImpl;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;
import ru.ws.marketplace.service.impl.CRUDUserServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
@EnableScheduling
@AllArgsConstructor
public class CheckerUsers {

    private final CRUDUserServiceImpl crudUserService;
    private final CRUDChannelServiceImpl crudChannelService;
    private final CRUDAdminServiceImpl crudAdminService;

    private final String token = "5277995877:AAFxEZE5tVi1XTcxIGKIV6N7M-2hKuXfhjE";

    // каждые 5 минут
    @Scheduled(cron = "* */10 * * * *")
    public void checkUser() {
        List<TChannel> allChannels = crudChannelService.getAllChannels();
        for (TChannel channel : allChannels) {
            Long channelId = channel.getGroupId();
            String name = channel.getName();
            Integer countUsers = createRequestOnTelegramAPI(token, channelId.toString(), name);
            List<TUser> allByChannelName = crudUserService.getAllByChannelName(name);
            comparisonOfTheNumberUsers(countUsers, allByChannelName);
        }
    }

    @SneakyThrows
    public Integer createRequestOnTelegramAPI(String token, String chatId, String chatName) {

        String url = "https://api.telegram.org/bot" + token + "/getChatMembersCount?chat_id=" + chatId + "=@" + chatName;
        URL obj = new URL(url);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String countUsers = getCountUsers(response.toString());
            return Integer.valueOf(countUsers);
        } else {
            System.out.println("GET request not worked");
            return null;
        }
    }

    public String getCountUsers(String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        return jsonObject.getString("result");
    }

    public void comparisonOfTheNumberUsers(Integer countUsers, List<TUser> userList) {
        if (countUsers > userList.size()) {
            for (TUser tUser : userList) {
                String channelName = tUser.getChannelName();
                TAdmin tAdminByChannelName = crudAdminService.getTAdminByChannelName(channelName);
                createResponseForAdmin(tAdminByChannelName.getChatId().toString(), countUsers, userList.size());
            }
        }
    }

    @SneakyThrows
    public void createResponseForAdmin(String chatId, Integer countUsers, Integer sizeList) {
        String url = "https://api.telegram.org/bot" + token + "/sendMessage?chat_id=" + chatId + "&text=" + "Количество пользователей в группе: " + countUsers +
                "                                                                      Количество людей, которые подписались с помощью бота: " + sizeList;
        URL obj = new URL(url);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            getCountUsers(response.toString());
        }
    }
}
