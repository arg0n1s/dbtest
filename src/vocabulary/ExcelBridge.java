package vocabulary;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import grammar.Word;

public class ExcelBridge {
	private Workbook wb;
	public ExcelBridge() {
		wb = null;
	}
	
	public boolean openExcelFile(String path) {
		InputStream inp;
		try {
			inp = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		
		try {
			wb = WorkbookFactory.create(inp);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		/*
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(2);
        Cell cell = row.getCell(3);
		*/
		return true;
	}
	
	public LinkedList<Word> extractListOfWords() {
		LinkedList<Word> words = new LinkedList<Word>();
		
		return words;
	}

}
