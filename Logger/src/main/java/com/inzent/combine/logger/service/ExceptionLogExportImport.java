package com.inzent.combine.logger.service;

import com.fasterxml.jackson.core.JsonEncoding;
import com.inzent.combine.logger.entity.ExceptionLog;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

@Component
public class ExceptionLogExportImport implements EntityExportImportBean<ExceptionLog> {

    @Override
    public void exportList(HttpServletRequest request, HttpServletResponse response, ExceptionLog entity, List<ExceptionLog> list) throws Exception {
        String fileName = "ExceptionLog_" + FastDateFormat.getInstance("yyyy-MM-dd hh:mm").format(new Timestamp(System.currentTimeMillis())) + ".xlsx";

        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=UTF-8''" + URLEncoder.encode(fileName, JsonEncoding.UTF8.getJavaName()).replaceAll("\\+", "%20"));
        response.setContentType("application/octet-stream");

        generateDownload(response, request.getServletContext().getRealPath("/template/List_ExceptionLog.xlsx"), entity, list);

        response.flushBuffer();
    }

    private void generateDownload(HttpServletResponse response, String templateFile, ExceptionLog entity, List<ExceptionLog> entityList) throws Exception {
        try (OutputStream outputStream = response.getOutputStream(); FileInputStream fileInputStream = new FileInputStream(templateFile); Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            Sheet writeSheet = workbook.getSheetAt(0);
            Row row;
            Cell cell;
            String values;

            CellStyle cellStyle_Base = getBaseCellStyle(workbook);
            CellStyle cellStyle_Info = getInfoCellStyle(workbook);

            // From
            values = entity.getFromLogDateTime().toString().substring(0, 19);
            row = writeSheet.getRow(3);
            cell = row.createCell(1);
            cell.setCellStyle(cellStyle_Base);
            cell.setCellValue(values);

            // To
            values = entity.getToLogDateTime().toString().substring(0, 19);
            row = writeSheet.getRow(3);
            cell = row.createCell(3);
            cell.setCellStyle(cellStyle_Base);
            cell.setCellValue(values);

            // 거래 ID
            values = entity.getTransactionId();
            row = writeSheet.getRow(3);
            cell = row.createCell(5);
            cell.setCellStyle(cellStyle_Base);
            cell.setCellValue(values);

            // 어플리케이션 ID
            values = entity.getApplicationId();
            row = writeSheet.getRow(3);
            cell = row.createCell(7);
            cell.setCellStyle(cellStyle_Base);
            cell.setCellValue(values);

            // 에러 ID
            values = (entity.getPk() != null) ? entity.getPk().getLogId() : "";
            row = writeSheet.getRow(3);
            cell = row.createCell(9);
            cell.setCellStyle(cellStyle_Base);
            cell.setCellValue(values);

            // 에러 코드
            values = entity.getExceptionCode();
            row = writeSheet.getRow(4);
            cell = row.createCell(1);
            cell.setCellStyle(cellStyle_Base);
            cell.setCellValue(values);

            // 인스턴스 ID
            values = entity.getInstanceId();
            row = writeSheet.getRow(4);
            cell = row.createCell(3);
            cell.setCellStyle(cellStyle_Base);
            cell.setCellValue(values);

            // 어댑터 ID
            values = entity.getAdapterId();
            row = writeSheet.getRow(4);
            cell = row.createCell(5);
            cell.setCellStyle(cellStyle_Base);
            cell.setCellValue(values);

            // 인터페이스 ID
            values = entity.getInterfaceId();
            row = writeSheet.getRow(4);
            cell = row.createCell(7);
            cell.setCellStyle(cellStyle_Base);
            cell.setCellValue(values);

            // 에러 메세지
            values = entity.getExceptionMsg();
            row = writeSheet.getRow(4);
            cell = row.createCell(9);
            cell.setCellStyle(cellStyle_Base);
            cell.setCellValue(values);

            // 조회결과 리스트
            long sum = 0;
            int i = 7;
            for(ExceptionLog data: entityList) {
                row = writeSheet.createRow(i);
                int c = 0;

                // 날짜/시간
                values = data.getPk().getLogDateTime();
                cell = row.createCell(c);
                cell.setCellValue(values);

                // 거래 ID
                values = data.getTransactionId();
                cell = row.createCell(++c);
                cell.setCellValue(values);

                // 어플리케이션 ID
                values = data.getApplicationId();
                cell = row.createCell(++c);
                cell.setCellValue(values);

                // 인스턴스 ID
                values = data.getInstanceId();
                cell = row.createCell(++c);
                cell.setCellValue(values);

                // 어댑터 ID
                values = data.getAdapterId();
                cell = row.createCell(++c);
                cell.setCellValue(values);

                // 인터페이스 ID
                values = data.getInterfaceId();
                cell = row.createCell(++c);
                cell.setCellValue(values);

                // 에러 코드
                values = data.getExceptionCode();
                cell = row.createCell(++c);
                cell.setCellValue(values);

                // 에러 메세지
                values = data.getExceptionMsg();
                cell = row.createCell(++c);
                cell.setCellValue(values);

                sum++;
                i++;
            }
            
            row = writeSheet.createRow(i);

            // 합계
            values = "Total";
            cell = row.createCell(0);
            cell.setCellStyle(cellStyle_Info);
            cell.setCellValue(values);

            // 합계(값)
            values = numberWithComma(Long.toString(sum));
            cell = row.createCell(1);
            cell.setCellStyle(cellStyle_Base);
            cell.setCellValue(values);

            entityList = null;

            workbook.write(outputStream);
        } catch (Exception e)
        {
            throw e;
        }
    }

    private String numberWithComma(String sum) {
        return sum.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
    }

    private CellStyle getInfoCellStyle(Workbook workbook) {
        // Cell 스타일
        XSSFCellStyle cellStyle = getBaseCellStyle(workbook);

        // 텍스트 맞춤(가로 가운데)
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        // 폰트 지정 사이즈 (굵게)
        Font font = getBaseFont(workbook, 10, IndexedColors.BLACK.getIndex());
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 242, (byte) 242, (byte) 242 }, null));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    public XSSFCellStyle getBaseCellStyle(Workbook workbook) {
        // Cell 스타일
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();

        // 텍스트 맞춤(세로 가운데)
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 텍스트 맞춤(가로 가운데)
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        // 폰트 지정 사이즈 10
        cellStyle.setFont(getBaseFont(workbook, 10, IndexedColors.BLACK.getIndex()));

        // cell 잠금
        cellStyle.setLocked(true);
        // cell text 줄바꿈 활성화
        cellStyle.setWrapText(true);

        return cellStyle;
    }

    public Font getBaseFont(Workbook workbook, int size, short color) {
        Font font = workbook.createFont();
        font.setFontHeight((short) (20 * size));
        font.setFontName("굴림");
        font.setColor(color);

        return font;
    }
}
