package ru.ws.marketplace.service.file;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
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
    public SendDocument sendFileToUser(Integer invoiceId, Message message) {

        String path = "/home/denis/file.xls";
        Integer rowNum = 0;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("default list");

        createRow(sheet, rowNum);

        CreationHelper createHelper = workbook.getCreationHelper();

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));

        writeUsersInFile(invoiceId, sheet, rowNum, cellStyle);
        FileOutputStream fileOutputStream = createFileOutputStream(path);
        workbook.write(fileOutputStream);
        File file = createFile(path);
        InputFile inputFile = createInputFile(file);
        return createDocument(inputFile, message);
    }

    public void writeUsersInFile(Integer invoiceId, HSSFSheet sheet, Integer rowNum, CellStyle cellStyle) {
        List<TUser> userList = fillData(invoiceId);
        for (TUser user : userList) {
            createSheetHeader(sheet, ++rowNum, user, cellStyle);
        }
    }

    private void createRow(HSSFSheet sheet, Integer rowNum) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Имя пользователя");
        row.createCell(1).setCellValue("Фамилия пользователя");
        row.createCell(2).setCellValue("Внесенная сумма за подписку");
        row.createCell(3).setCellValue("Начало действия подписки");
        row.createCell(4).setCellValue("Окончание действия подписки");
    }


    @SneakyThrows
    private FileOutputStream createFileOutputStream(String path) {
        FileOutputStream out = new FileOutputStream(path);
        return out;
    }

    private File createFile(String path) {
        return new File(path);
    }

    private InputFile createInputFile(File file) {
        InputFile inputFile = new InputFile();
        inputFile.setMedia(file);
        return inputFile;
    }

    private SendDocument createDocument(InputFile inputFile, Message message) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setDocument(inputFile);
        sendDocument.setChatId(String.valueOf(message.getChatId()));
        return sendDocument;
    }


    private void createSheetHeader(HSSFSheet sheet, int rowNum, TUser user, CellStyle cellStyle) {
        Row row = sheet.createRow(rowNum);
        Cell cell;

        row.createCell(0).setCellValue(user.getFirstName());
        row.createCell(1).setCellValue(user.getLastName());
        row.createCell(2).setCellValue(user.getPayment());

        cell = row.createCell(3);
        cell.setCellValue(user.getStartDate().getTime());
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellValue(user.getEndDate().getTime());
        cell.setCellStyle(cellStyle);
    }

    private List<TUser> fillData(Integer invoiceId) {
        return crudUserService.getAllByInvoiceId(invoiceId);
    }
}

