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
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("default list");
        List<TUser> dataList = fillData(invoiceId);

        int rowNum = 0;

        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Имя пользователя");
        row.createCell(1).setCellValue("Фамилия пользователя");
        row.createCell(2).setCellValue("Внесенная сумма за подписку");
        row.createCell(3).setCellValue("Начало действия подписки");
        row.createCell(4).setCellValue("Окончание действия подписки");

        for (TUser user : dataList) {
            createSheetHeader(sheet, ++rowNum, user);
        }

        String path = "/home/denis/file.xls";

        FileOutputStream out = new FileOutputStream("/home/denis/file.xls");
        workbook.write(out);

        File file = new File(path);

        InputFile inputFile = new InputFile();
        inputFile.setMedia(file);

        SendDocument sendDocument = new SendDocument();
        sendDocument.setDocument(inputFile);
        sendDocument.setChatId(String.valueOf(message.getChatId()));
        return sendDocument;
    }

    private void createSheetHeader(HSSFSheet sheet, int rowNum, TUser user) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(user.getFirstName());
        row.createCell(1).setCellValue(user.getLastName());
        row.createCell(2).setCellValue(user.getPayment());
        row.createCell(3).setCellValue(user.getStartDate().getTime());
        row.createCell(4).setCellValue(user.getEndDate().getTime());
    }

    private List<TUser> fillData(Integer invoiceId) {
        return crudUserService.getAllByInvoiceId(invoiceId);
    }
}

