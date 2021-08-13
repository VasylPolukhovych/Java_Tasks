package pizza.service;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.dto.CookedDish;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class LoadDataFromExcel {
    @Autowired
    CookedDishService cookedDishService;
    private Map<String, String> getInputMapping(){
        Map<String, String> inputMapping = new HashMap<>();
        inputMapping.put("count", "count");
        inputMapping.put("curCount", "current count");
        inputMapping.put("dateOfMaking", "date of making");
        inputMapping.put("idDish", "id dish");
        return inputMapping;
    }
    public List<CookedDish> readFromExcel(String file) throws IOException, IllegalAccessException {
        List<CookedDish> listCookedDish = new ArrayList<>();
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);
        Iterator<Row> rowIterator = myExcelSheet.iterator();
        Map<String,String> inputMapping = getInputMapping();
        Map<String, Integer> outputMapping = new HashMap<>();
        Field[] fields = CookedDish.class.getDeclaredFields();
        int rowIndex = 0;
        int rowIndexHeaders = 10000;
        while (rowIterator.hasNext()) {
            rowIndex = rowIndex + 1;
            int countOfFields = fields.length;
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            CookedDish cd = new CookedDish();

            while (cellIterator.hasNext()) {
                Integer cellNumberValue = null;
                String cellStringValue = null;
                LocalDate cellDateValue = null;
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        cellStringValue = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        double doubleVal = cell.getNumericCellValue();
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            cellDateValue = cell.getDateCellValue().toInstant()
                                    .atZone(ZoneId.systemDefault()).toLocalDate();
                        } else {
                            cellNumberValue = (int) doubleVal;
                        }
                        break;
                    default:
                }
                if (cellStringValue != null) {
                    for (Map.Entry<String, String> entry : inputMapping.entrySet()) {
                        if (cellStringValue.equalsIgnoreCase(entry.getValue())) {
                            outputMapping.putIfAbsent(entry.getKey(), cell.getColumnIndex());
                            rowIndexHeaders = rowIndex;
                        }
                    }
                }
                if (countOfFields == outputMapping.size() + 1) {
                    for (Map.Entry<String, Integer> outEntry : outputMapping.entrySet()) {
                        if (cell.getColumnIndex() == outEntry.getValue() & rowIndexHeaders < rowIndex) {
                            for (Field field : fields) {
                                field.setAccessible(true);
                                String fieldName = field.getName();
                                String fieldType = field.getType().getTypeName();
                                if (outEntry.getKey().equalsIgnoreCase(fieldName)) {
                                    if (fieldType.contains("LocalDate")) {
                                        field.set(cd, cellDateValue);
                                    } else if (fieldType.contains("Long")) {
                                        field.set(cd, cellNumberValue.longValue());
                                    } else if (fieldType.contains("String")) {
                                        field.set(cd, cellStringValue);
                                    } else {
                                        field.set(cd, cellNumberValue);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (cd.getCount() != null & cd.getDateOfMaking() != null & cd.getIdDish() != null) {
                CookedDish createdCookedDish =
                        cookedDishService.addCookedDish(cd);
                if (createdCookedDish != null) {
                    listCookedDish.add(createdCookedDish);
                }
            }
        }
        myExcelBook.close();
        return listCookedDish;
    }
}