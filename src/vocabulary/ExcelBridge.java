package vocabulary;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import grammar.Gender;
import grammar.Word;
import grammar.WordFactory;
import grammar.WordType;

public class ExcelBridge {
	
	public static final int HEADER_ROW_IDX = 0;
	public static final int HEADER_TABLE_COLUMN_IDX = 0;
	public static final int HEADER_NOUN_COLUMN_IDX = 4;
	public static final int HEADER_VERBS_COLUMN_IDX = 8;
	public static final int HEADER_ADJECTIVES_COLUMN_IDX = 10;
	public static final int HEADER_ARTICLES_COLUMN_IDX = 12;
	
	public static final int PROPERTIES_LANGUAGE_ROW_IDX = 1;
	public static final int PROPERTIES_LEVEL_ROW_IDX = 2;
	public static final int PROPERTIES_LANGUAGE1_COL_IDX = 0;
	public static final int PROPERTIES_LANGUAGE2_COL_IDX = 2;
	public static final int PROPERTIES_LEVEL1_COL_IDX = 0;
	public static final int PROPERTIES_LEVEL2_COL_IDX = 2;
	
	public static final int NOUNS_CATEGORY_ROW_IDX = 1;
	public static final int NOUNS_LANGUAGE1_COL_IDX = 4;
	public static final int NOUNS_LANGUAGE2_COL_IDX = 5;
	public static final int NOUNS_GENDER1_COL_IDX = 6;
	public static final int NOUNS_GENDER2_COL_IDX = 7;
	
	public static final int VERBS_CATEGORY_ROW_IDX = 1;
	public static final int VERBS_LANGUAGE1_COL_IDX = 8;
	public static final int VERBS_LANGUAGE2_COL_IDX = 9;
	
	public static final int ADJECTIVES_CATEGORY_ROW_IDX = 1;
	public static final int ADJECTIVES_LANGUAGE1_COL_IDX = 10;
	public static final int ADJECTIVES_LANGUAGE2_COL_IDX = 11;
	
	public static final int ARTICLES_CATEGORY_ROW_IDX = 1;
	public static final int ARTICLES_LANGUAGE1_COL_IDX = 12;
	public static final int ARTICLES_LANGUAGE2_COL_IDX = 13;
	public static final int ARTICLES_GENDER1_COL_IDX = 14;
	public static final int ARTICLES_GENDER2_COL_IDX = 15;
	
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
	
	public HashMap<Integer, Word> extractWordsFromWorkbook() {
		HashMap<Integer, Word> words = new HashMap<Integer, Word>();
		for(Sheet sheet : wb) {
			try {
				extractWordsFromSheet(words, sheet);
			} catch (RuntimeException e) {
				System.out.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
		
		return words;
	}
	
	private void extractWordsFromSheet(HashMap<Integer, Word> words, Sheet sheet) throws RuntimeException {
		// Check header format of excel table
		Row header = sheet.getRow(HEADER_ROW_IDX);
		Cell headerEntries = header.getCell(HEADER_TABLE_COLUMN_IDX);
		if(!headerEntries.getStringCellValue().equals("Table Properties")) {
			throw new RuntimeException("'Table Properties' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		headerEntries = header.getCell(HEADER_NOUN_COLUMN_IDX);
		if(!headerEntries.getStringCellValue().equals("Nouns")) {
			throw new RuntimeException("'Nouns' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		headerEntries = header.getCell(HEADER_VERBS_COLUMN_IDX);
		if(!headerEntries.getStringCellValue().equals("Verbs")) {
			throw new RuntimeException("'Verbs' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		headerEntries = header.getCell(HEADER_ADJECTIVES_COLUMN_IDX);
		if(!headerEntries.getStringCellValue().equals("Adjectives")) {
			throw new RuntimeException("'Adjectives' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		headerEntries = header.getCell(HEADER_ARTICLES_COLUMN_IDX);
		if(!headerEntries.getStringCellValue().equals("Articles")) {
			throw new RuntimeException("'Articles' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		
		// Check table properties format of excel table
		Row languageRow = sheet.getRow(PROPERTIES_LANGUAGE_ROW_IDX);
		Cell langEntries = languageRow.getCell(PROPERTIES_LANGUAGE1_COL_IDX);
		if(!langEntries.getStringCellValue().equals("Language 1:")) {
			throw new RuntimeException("Properties: 'Language 1:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		langEntries = languageRow.getCell(PROPERTIES_LANGUAGE2_COL_IDX);
		if(!langEntries.getStringCellValue().equals("Language 2:")) {
			throw new RuntimeException("Properties: 'Language 2:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		Row levelRow = sheet.getRow(PROPERTIES_LEVEL_ROW_IDX);
		Cell levelEntries = levelRow.getCell(PROPERTIES_LEVEL1_COL_IDX);
		if(!levelEntries.getStringCellValue().equals("Language Level 1:")) {
			throw new RuntimeException("Properties: 'Language Level 1:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		levelEntries = levelRow.getCell(PROPERTIES_LEVEL2_COL_IDX);
		if(!levelEntries.getStringCellValue().equals("Language Level 2:")) {
			throw new RuntimeException("Properties: 'Language Level 2:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		
		// Check nouns format of excel table
		Row nounsRow = sheet.getRow(NOUNS_CATEGORY_ROW_IDX);
		Cell nounsEntries = nounsRow.getCell(NOUNS_LANGUAGE1_COL_IDX);
		if(!nounsEntries.getStringCellValue().equals("Language 1:")) {
			throw new RuntimeException("Nouns: 'Language 1:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		nounsEntries = nounsRow.getCell(NOUNS_LANGUAGE2_COL_IDX);
		if(!nounsEntries.getStringCellValue().equals("Language 2:")) {
			throw new RuntimeException("Nouns: 'Language 2:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		nounsEntries = nounsRow.getCell(NOUNS_GENDER1_COL_IDX);
		if(!nounsEntries.getStringCellValue().equals("Gender 1:")) {
			throw new RuntimeException("Nouns: 'Gender 1:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		nounsEntries = nounsRow.getCell(NOUNS_GENDER2_COL_IDX);
		if(!nounsEntries.getStringCellValue().equals("Gender 2:")) {
			throw new RuntimeException("Nouns: 'Gender 2:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		
		// Check verbs format of excel table
		Row verbsRow = sheet.getRow(VERBS_CATEGORY_ROW_IDX);
		Cell verbsEntries = verbsRow.getCell(VERBS_LANGUAGE1_COL_IDX);
		if(!verbsEntries.getStringCellValue().equals("Language 1:")) {
			throw new RuntimeException("Verbs: 'Language 1:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		verbsEntries = verbsRow.getCell(VERBS_LANGUAGE2_COL_IDX);
		if(!verbsEntries.getStringCellValue().equals("Language 2:")) {
			throw new RuntimeException("Verbs: 'Language 2:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		
		// Check adjectives format of excel table
		Row adjectivesRow = sheet.getRow(ADJECTIVES_CATEGORY_ROW_IDX);
		Cell adjectivesEntries = verbsRow.getCell(ADJECTIVES_LANGUAGE1_COL_IDX);
		if(!adjectivesEntries.getStringCellValue().equals("Language 1:")) {
			throw new RuntimeException("Adjectives: 'Language 1:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		adjectivesEntries = adjectivesRow.getCell(ADJECTIVES_LANGUAGE2_COL_IDX);
		if(!adjectivesEntries.getStringCellValue().equals("Language 2:")) {
			throw new RuntimeException("Adjectives: 'Language 2:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		
		// Check articles format of excel table
		Row articlesRow = sheet.getRow(ARTICLES_CATEGORY_ROW_IDX);
		Cell articlesEntries = articlesRow.getCell(ARTICLES_LANGUAGE1_COL_IDX);
		if(!articlesEntries.getStringCellValue().equals("Language 1:")) {
			throw new RuntimeException("Articles: 'Language 1:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		articlesEntries = articlesRow.getCell(ARTICLES_LANGUAGE2_COL_IDX);
		if(!articlesEntries.getStringCellValue().equals("Language 2:")) {
			throw new RuntimeException("Articles: 'Language 2:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		articlesEntries = articlesRow.getCell(ARTICLES_GENDER1_COL_IDX);
		if(!articlesEntries.getStringCellValue().equals("Gender 1:")) {
			throw new RuntimeException("Articles: 'Gender 1:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		articlesEntries = articlesRow.getCell(ARTICLES_GENDER2_COL_IDX);
		if(!articlesEntries.getStringCellValue().equals("Gender 2:")) {
			throw new RuntimeException("Articles: 'Gender 2:' entry missing or at the wrong place, got: "+headerEntries.getStringCellValue());
		}
		
		extractNounsFromSheet(words, sheet);
		extractVerbsFromSheet(words, sheet);
		extractAdjectivesFromSheet(words, sheet);
		extractArticlesFromSheet(words, sheet);
	}
	
	private void extractNounsFromSheet(HashMap<Integer, Word> words, Sheet sheet) {
		String lang1 = getLanguage1(sheet);
		String lang2 = getLanguage2(sheet);
		int level1 = getLanguageLevel1(sheet);
		int level2 = getLanguageLevel2(sheet);
		for(int i = NOUNS_CATEGORY_ROW_IDX+1; i<=sheet.getLastRowNum(); i++) {
			Row current = sheet.getRow(i);
			if(current == null) {
				return;
			}
			String word1 = null, word2 = null;
			Gender gender1 = null, gender2 = null;
			
			try {
				Cell c = current.getCell(NOUNS_LANGUAGE1_COL_IDX);
				word1 = extractString(c);
				c = current.getCell(NOUNS_LANGUAGE2_COL_IDX);
				word2 = extractString(c);
				c = current.getCell(NOUNS_GENDER1_COL_IDX);
				gender1 = extractGender(c);
				c = current.getCell(NOUNS_GENDER2_COL_IDX);
				gender2 = extractGender(c);
			} catch (RuntimeException e) {
				System.out.println(e.getClass().getName() + ": " + e.getMessage());
				break;
			}
			
			if( word1 == null || word2 == null || gender1 == null || gender2 == null) {
				break;
			}
			
			int id1 = Word.createID(word1, lang1, WordType.noun);
			int id2 = Word.createID(word2, lang2, WordType.noun);
			if(!words.containsKey(id1)) {
				Word w1 = WordFactory.createNoun(word1, lang1, level1, gender1);
				words.put(id1, w1);
			}
			if(!words.containsKey(id2)) {
				Word w2 = WordFactory.createNoun(word2, lang2, level2, gender2);
				words.put(id2, w2);
			}
		}
	}
	
	private void extractVerbsFromSheet(HashMap<Integer, Word> words, Sheet sheet) {
			
	}

	private void extractAdjectivesFromSheet(HashMap<Integer, Word> words, Sheet sheet) {
		
	}

	private void extractArticlesFromSheet(HashMap<Integer, Word> words, Sheet sheet) {
		
	}
	
	private String getLanguage1(Sheet sheet) {
		String lang = "None";
		
		Row languageRow = sheet.getRow(PROPERTIES_LANGUAGE_ROW_IDX);
		Cell langEntries = languageRow.getCell(PROPERTIES_LANGUAGE1_COL_IDX);
		lang = langEntries.getStringCellValue();
		
		return lang;
	}
	
	private String getLanguage2(Sheet sheet) {
		String lang = "None";
		
		Row languageRow = sheet.getRow(PROPERTIES_LANGUAGE_ROW_IDX);
		Cell langEntries = languageRow.getCell(PROPERTIES_LANGUAGE2_COL_IDX);
		lang = langEntries.getStringCellValue();
		
		return lang;
	}
	
	private int getLanguageLevel1(Sheet sheet) {
		int level = 0;
		
		Row levelRow = sheet.getRow(PROPERTIES_LEVEL_ROW_IDX);
		Cell levelEntries = levelRow.getCell(PROPERTIES_LEVEL1_COL_IDX);
		level = (int) levelEntries.getNumericCellValue();
		
		return level;
	}
	
	private int getLanguageLevel2(Sheet sheet) {
		int level = 0;
		
		Row levelRow = sheet.getRow(PROPERTIES_LEVEL_ROW_IDX);
		Cell levelEntries = levelRow.getCell(PROPERTIES_LEVEL2_COL_IDX);
		level = (int) levelEntries.getNumericCellValue();
		
		return level;
	}
	
	private String extractString(Cell entry) throws RuntimeException {
		if(entry == null) {
			throw new RuntimeException("Entry missing.");
		}
		String g = entry.getStringCellValue();
		if(g == null) {
			throw new RuntimeException("Entry is null.");
		}
		if(g == "") {
			throw new RuntimeException("Entry is empty.");
		}
		return g;
	
	}
	
	private Gender extractGender(Cell entry) throws RuntimeException {
		if(entry == null) {
			throw new RuntimeException("Gender entry missing.");
		}
		String g = entry.getStringCellValue();
		if(g.equals("female")) {
			return Gender.female;
		}
		else if(g.equals("male")) {
			return Gender.male;
		}
		else if(g.equals("neutral")) {
			return Gender.neutral;
		}
		else {
			throw new RuntimeException("Given gender '" + g + "' unknown.");
		}
	}

}
