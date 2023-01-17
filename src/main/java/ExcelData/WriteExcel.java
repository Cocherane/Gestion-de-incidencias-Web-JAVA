package ExcelData;

import models.IncidenciaDataClass;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WriteExcel {




    public static void writeExcelIncidencias( ArrayList<IncidenciaDataClass> incidencias, String pathConfig ){
        // creamos la instancia
        Workbook workbook = new XSSFWorkbook();
        // asignamos el nombre de la pagina
        Sheet sheet = workbook.createSheet("Incidencias");

        Row header = sheet.createRow(0);

        // estilo del encabezado
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        headerStyle.setFont(font);



        // encabezado de las etiquetas
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Id_incidencia");
        headerCell.setCellStyle(headerStyle);


        headerCell = header.createCell(1);
        headerCell.setCellValue("Descripción");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Prioridad");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Fecha_creación");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Dias");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Nombre_usuario_creo");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Email_usuario");
        headerCell.setCellStyle(headerStyle);

        /// rellena las columnas dada un numero de tupla
        int rowNum = 1;
        for (var incidencia : incidencias) {

            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object data : incidencia.dataResume() ) {

                Cell cell = row.createCell(colNum++);
                if (data instanceof String) {
                    cell.setCellValue((String) data);
                } else if (data instanceof Integer) {
                    cell.setCellValue((Integer) data);
                }
            }
        }

        // creamos la ruta donde se va a guardar
        try {
            File path = new File( pathConfig + "\\incidencias.xlsx" );
            path.getCanonicalFile();
            FileOutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
