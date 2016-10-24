/**
 * 
 */
package com.crt.excel.imports;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author UOrder
 *
 */
abstract class AbstractExcel {
	/**
	 * 获取工作薄的行数
	 * @param sheet
	 * @param primaryColumn
	 * @return
	 */
	protected Integer getNumberOfRows(Sheet sheet, int primaryColumn) {
        Integer noOfEntries = 1;
        // getLastRowNum and getPhysicalNumberOfRows showing false values
        // sometimes
       while (sheet.getRow(noOfEntries) !=null && sheet.getRow(noOfEntries).getCell(primaryColumn) != null) {
           noOfEntries++;
       }
        	
        return noOfEntries;
    }
    /**
     * 获取某一行是否导入
     * @param row
     * @param statusColumn
     * @return
     */
    protected boolean isNotImported(Row row, int statusColumn) {
		return !readAsString(statusColumn, row).equals("Imported");
	}
    /**
     * 将某行某列转换为int
     * @param colIndex
     * @param row
     * @return
     */
    protected Integer readAsInt(int colIndex, Row row) {
        try {
        	Cell c = row.getCell(colIndex);
        	if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)
        		return null;
        	FormulaEvaluator eval = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
        	if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
        		CellValue val = null;
        		try {
        			val = eval.evaluate(c);
        		} catch (NullPointerException npe) {
        			return null;
        		}
        		return ((Double)val.getNumberValue()).intValue() ;
        	}
        	return ((Double) c.getNumericCellValue()).intValue() ;
        } catch (RuntimeException re) {
            return Integer.valueOf(row.getCell(colIndex).getStringCellValue());
        }
    }
    /**
     * 将某列转换为Long
     * @param colIndex
     * @param row
     * @return
     */
    protected Long readAsLong(int colIndex, Row row) {
        try {
        	Cell c = row.getCell(colIndex);
        	if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)
        		return null;
        	FormulaEvaluator eval = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
        	if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
        		CellValue val = null;
        		try {
        			val = eval.evaluate(c);
        		} catch (NullPointerException npe) {
        			return null;
        		}
        		return ((Double) val.getNumberValue()).longValue() ;
        	}
        	return ((Double) c.getNumericCellValue()).longValue() ;
        } catch (RuntimeException re) {
            return Long.parseLong(row.getCell(colIndex).getStringCellValue());
        }
    }
    /**
     * 将某列转换为Double
     * @param colIndex
     * @param row
     * @return
     */
    protected Double readAsDouble(int colIndex, Row row) {
    	Cell c = row.getCell(colIndex);
    	if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)
    		return 0.0;
    	FormulaEvaluator eval = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
    	if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
    		CellValue val = null;
    		try {
    			val = eval.evaluate(c);
    		} catch (NullPointerException npe) {
    			return 0.0;
    		}
    		return val.getNumberValue();
    	}
    	return row.getCell(colIndex).getNumericCellValue();
    }
    /**
     * 将某列转换为String
     * @param colIndex
     * @param row
     * @return
     */
    protected String readAsString(int colIndex, Row row) {
        try {
        	Cell c = row.getCell(colIndex);
        	if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)
        		return "";
        	FormulaEvaluator eval = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
        	if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
        		CellValue val = null;
        		try {
        			val = eval.evaluate(c);
        		} catch(NullPointerException npe) {
        			return "";
        		}
        		String res = trimEmptyDecimalPortion(val.getStringValue());
        		return res.trim();
        	}
        	String res = trimEmptyDecimalPortion(c.getStringCellValue().trim());
            return res.trim();
        } catch (Exception e) {
//        	e.printStackTrace();
            return ((Double)row.getCell(colIndex).getNumericCellValue()).intValue() + "";
        }
    }
    
    private String trimEmptyDecimalPortion(String result) {
    	if(result != null && result.endsWith(".0"))
    		return	result.split("\\.")[0];
    	else
    		return result;
    }
    /**
     * 将某列转换为Date
     * @param colIndex
     * @param row
     * @return
     */
    protected Date readAsDate(int colIndex, Row row) {
    	try{
    		Cell c = row.getCell(colIndex);
    		if(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)
    			return null;
    		return c.getDateCellValue();
    	}  catch  (Exception e) {
    		return null;
    	}
    }
    /**
     * 将某列转换为Boolean
     * @param colIndex
     * @param row
     * @return
     */
    protected Boolean readAsBoolean(int colIndex, Row row) {
    	try{
    	    Cell c = row.getCell(colIndex);
		    if(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)
			    return false;
		    FormulaEvaluator eval = row.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
        	if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
        		CellValue val = null;
        		try {
        			val = eval.evaluate(c);
        		} catch (NullPointerException npe) {
        			return false;
        		}
        		return val.getBooleanValue();
        	}
    	    return c.getBooleanCellValue();
    	} catch (Exception e) {
    		String booleanString = row.getCell(colIndex).getStringCellValue().trim();
    		if(booleanString.equalsIgnoreCase("TRUE"))
    			return true;
    		else
    			return false;
    	}
    }
}
