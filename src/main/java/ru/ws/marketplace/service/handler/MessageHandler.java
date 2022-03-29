package ru.ws.marketplace.service.handler;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;
import ru.ws.marketplace.state.DialogueContext;
import ru.ws.marketplace.state.End;

@Service
public class MessageHandler {

   private final TChannel channel = new TChannel();
   private final CRUDChannelServiceImpl crudChannelService;
   private final SendMessage sendMessage = new SendMessage();

    @Autowired
    public MessageHandler(CRUDChannelServiceImpl crudChannelService) {
        this.crudChannelService = crudChannelService;
    }

    @SneakyThrows
    public SendMessage sortedMessage(Message message,DialogueContext context){

        if(!context.getStatusName().equals("END")){
        handleInputMessage(message,context);
        }

    return sendMessage;
    }


    public SendMessage handleInputMessage(Message message, DialogueContext context) {

        sendMessage.setChatId(message.getChatId().toString());

        String text = message.getText();

        switch (context.getStatusName()) {
            case "NAME":
                channel.setName(text);
                sendMessage.setText("Введите описание канала(цель канала, для кого предназначена информация публикуемая в канале и т.д.)");
                break;
            case "DESCRIPTION":
                channel.setDescription(text);
                sendMessage.setText("Введите ссылку на канал");
                break;
            case "LINK":
                channel.setLink(text);
                sendMessage.setText("Введите цену подписки на месяц");
                break;
            case "PRICE":
                channel.setPrice(Integer.valueOf(text));
                break;
            default:
                break;
        }
        if(context.getStatusName().equals("PRICE")){
            crudChannelService.add(channel);
            sendMessage.setText("Анкета заполнена! \n\n Перейдите по ссылке /start для дальнейшего управления ботом(проверить добавленный канал можно на вкладке -  Список Ресурсов)");
            context.setState(new End());
        }else{
            context.nextState();
        }
        return sendMessage;
    }


}
