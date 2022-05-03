package ru.ws.marketplace.service.file;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ws.marketplace.model.TUser;
import ru.ws.marketplace.service.impl.CRUDUserServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateExcelFileHandler {

    private final CRUDUserServiceImpl crudUserService;

    @SneakyThrows
    public SendDocument getReadyExcelList(Integer invoiceId, Message message) {
        // создание самого excel файла в памяти
        HSSFWorkbook workbook = new HSSFWorkbook();
        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet("default list");
        // заполняем список какими-то данными
        List<TUser> dataList = fillData(invoiceId);
        // счетчик для строк
        int rowNum = 0;
        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Имя пользователя");
        row.createCell(1).setCellValue("Фамилия пользователя");
        row.createCell(2).setCellValue("Внесенная сумма за подписку");
        row.createCell(3).setCellValue("Начало действия подписки");
        row.createCell(4).setCellValue("Окончание действия подписки");
        // заполняем лист данными
        for (TUser user : dataList) {
            createSheetHeader(sheet, ++rowNum, user);
        }
        // записываем созданный в памяти Excel документ в файл

        String path = "/home/denis/file.xls";
        FileOutputStream out = new FileOutputStream("/home/denis/file.xls");//возможно нужен будет указать расширение
        workbook.write(out);
        System.out.println("Excel файл успешно создан!");

        File file = new File(path);

        InputFile inputFile = new InputFile();
        inputFile.setMedia(file);

        SendDocument sendDocument = new SendDocument();
        sendDocument.setDocument(inputFile);
        sendDocument.setChatId(String.valueOf(message.getChatId()));
        return sendDocument;
    }

    // заполнение строки (rowNum) определенного листа (sheet)
    // данными  из dataModel созданного в памяти Excel файла
    private void createSheetHeader(HSSFSheet sheet, int rowNum, TUser user) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(user.getFirstName());
        row.createCell(1).setCellValue(user.getLastName());
        row.createCell(2).setCellValue(user.getPayment());
        row.createCell(3).setCellValue(user.getStartDate());
        row.createCell(4).setCellValue(user.getEndDate());
    }

    // заполняем список рандомными данными
    // в реальных приложениях данные будут из БД или интернета
    private List<TUser> fillData(Integer invoiceId) {
        return crudUserService.getAllByInvoiceId(invoiceId);
    }
}

