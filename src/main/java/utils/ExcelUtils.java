package utils;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

import static java.sql.Types.BOOLEAN;
import static org.apache.poi.ss.formula.DataValidationEvaluator.ValidationEnum.FORMULA;

public class ExcelUtils {
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;

    public static void setExcelFile(String excelPath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(excelPath);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
    }

    public static String getCellData(int rowNum, int colNum) {
        XSSFCell cell = sheet.getRow(rowNum).getCell(colNum);

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue()); // handles ZIP, SSN
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "Unsupported cell type";
        }
    }
    public static int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }
}