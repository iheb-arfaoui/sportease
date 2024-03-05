package controllers;

import entities.Academy;
import entities.Coach;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class ExcelExport {

    private static String filePathC;
    private static String filePathA;

    private static List<Coach> coaches;
    private static List<Academy> academies;


    public static void exportToExcel(List<Coach> coaches, String filePathC) {
        ExcelExport.coaches = coaches;
        ExcelExport.filePathC = filePathC;
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Coaches");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Nom du coach", "Email", "Tel", "Nom de l'académie"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1; // Commencer à la deuxième ligne après les titres des colonnes
            for (Coach coach : coaches) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                //

            /*int rowNum = 0;
            for (Club club : clubs) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;*/
                row.createCell(colNum++).setCellValue(coach.getName());
                row.createCell(colNum++).setCellValue(coach.getEmail());
                row.createCell(colNum++).setCellValue(coach.getPhone());
                row.createCell(colNum++).setCellValue(coach.getAcademyName());

            }

            try (FileOutputStream outputStream = new FileOutputStream(filePathC)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void exportToExcelA(List<Academy> academies, String filePathA) {
        ExcelExport.academies = academies;
        ExcelExport.filePathA = filePathA;
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Coaches");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Nom", "Categorie", "Localisation", "Créé par"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1; // Commencer à la deuxième ligne après les titres des colonnes
            for (Academy academy : academies) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                //


                row.createCell(colNum++).setCellValue(academy.getName());
                row.createCell(colNum++).setCellValue(academy.getCategory());
                row.createCell(colNum++).setCellValue(academy.getLocalisation());
                row.createCell(colNum++).setCellValue(academy.getCreated_by());

            }

            try (FileOutputStream outputStream = new FileOutputStream(filePathA)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
