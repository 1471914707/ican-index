package com.ican.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportUtil {

    public static byte[] exportExcel(List<List<String>> data, String[] column, String title) {

        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("sheet1");

            // 样式对象
            HSSFCellStyle cs0 = wb.createCellStyle();
            cs0.setAlignment(HSSFCellStyle.ALIGN_CENTER);//
            cs0.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cs0.setBorderBottom(HSSFCellStyle.BORDER_THIN);//
            cs0.setBorderLeft(HSSFCellStyle.BORDER_THIN);//
            cs0.setBorderRight(HSSFCellStyle.BORDER_THIN);//
            cs0.setBorderTop(HSSFCellStyle.BORDER_THIN);//
            cs0.setWrapText(true);
            // 创建字体
            HSSFFont ff0 = wb.createFont();
            ff0.setFontHeightInPoints((short) 14);// 字体大小
            ff0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            cs0.setFont(ff0);// 放入样式中

            HSSFRow row0 = sheet.createRow(0);
            row0.setHeight((short) 500);
            HSSFCell cell0 = row0.createCell(0);
            cell0.setCellValue(title);
            cell0.setCellStyle(cs0);
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5));// 指定合并区域

            HSSFRow row = sheet.createRow(1);
            sheet.setColumnWidth(0, 32 * 80);
            sheet.setColumnWidth(1, 32 * 120);
            sheet.setColumnWidth(2, 32 * 120);
            sheet.setColumnWidth(3, 32 * 200);
            sheet.setColumnWidth(4, 32 * 400);
            sheet.setColumnWidth(5, 32 * 400);
            sheet.setColumnWidth(6, 32 * 150);
            sheet.setColumnWidth(7, 32 * 400);

            // 样式对象
            HSSFCellStyle cs = wb.createCellStyle();
            cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);//
            cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);//
            cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);//
            cs.setBorderRight(HSSFCellStyle.BORDER_THIN);//
            cs.setBorderTop(HSSFCellStyle.BORDER_THIN);//
            cs.setWrapText(true);
            // 创建字体
            HSSFFont ff = wb.createFont();
            ff.setFontHeightInPoints((short) 10);// 字体大小
            ff.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            cs.setFont(ff);// 放入样式中

            // 样式对象
            HSSFCellStyle cs2 = wb.createCellStyle();
            cs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//
            cs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cs2.setBorderBottom(HSSFCellStyle.BORDER_THIN);//
            cs2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//
            cs2.setBorderRight(HSSFCellStyle.BORDER_THIN);//
            cs2.setBorderTop(HSSFCellStyle.BORDER_THIN);//
            cs2.setWrapText(true);
            // 创建字体
            HSSFFont ff2 = wb.createFont();
            ff2.setFontHeightInPoints((short) 10);// 字体大小
            cs2.setFont(ff2);// 放入样式中

            HSSFCellStyle cs3 = wb.createCellStyle();
            cs3.setAlignment(HSSFCellStyle.ALIGN_CENTER);//
            cs3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cs3.setBorderBottom(HSSFCellStyle.BORDER_THIN);//
            cs3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//
            cs3.setBorderRight(HSSFCellStyle.BORDER_THIN);//
            cs3.setBorderTop(HSSFCellStyle.BORDER_THIN);//
            cs3.setWrapText(true);
            // 创建字体
            HSSFFont ff3 = wb.createFont();
            ff3.setFontHeightInPoints((short) 10);// 字体大小
            // 设置红色
            ff3.setColor(HSSFColor.RED.index);
            cs3.setFont(ff3);// 放入样式中

            HSSFCell cell = null;
            for (int i = 0; i < column.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(column[i]);
                cell.setCellStyle(cs);
            }

            int i = 0;
            for (List<String> ls : data) {
                row = sheet.createRow(i + 2);
                i++;
                for (int j = 0; j < ls.size(); j++) {
                    cell = row.createCell(j);
                    cell.setCellValue(ls.get(j));
                    cell.setCellStyle(cs2);
                }
            }
            // 先写到字节数组，在从字节数组读出
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                wb.write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = os.toByteArray();
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
