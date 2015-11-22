import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class PreprocessingData {
	public static final int NUM_OF_DOC = 20;
	public static final String STOP_LIST_PATH = "./docs/stopword";
	public static final String DATASET_PATH_PREFIX = "./docs/doc";
	Map<String, Integer> strToId;
	Map<Integer, Integer>[] tf;
	Set<String> stopList;
	Set<Character> puncList;
	int strId;
	int docId;

	@SuppressWarnings("unchecked")
	public PreprocessingData() {
		stopList = new HashSet<>(); // TODO
		tf = new Map[NUM_OF_DOC];
		strToId = new HashMap<>();
		char[] pubcs = { '.', ',', ':', ';', '?', '!', '(', ')', '\'', '\"' };
		puncList = new HashSet<>();
		for (char c : pubcs) {
			puncList.add(c);
		}
	}

	public List<SampleData> buildDatasetWithTF() {
		init();
		for (int i = 0; i < NUM_OF_DOC; i++) {
			String docPath = DATASET_PATH_PREFIX + i;// TODO
			tokenize(docPath);
		}
		List<SampleData> dataset = new ArrayList<>();
		int numOfAttr = strToId.size();
		System.out.println(numOfAttr);
		for (int i = 0; i < NUM_OF_DOC; i++) {
			List<Double> attributes = new ArrayList<>();
			for (int j = 0; j < numOfAttr; j++) {
				if (tf[i].containsKey(j)) {
					attributes.add((double) tf[i].get(j));
				} else {
					attributes.add(0d);
				}
			}
			dataset.add(new SampleData(attributes, i));
		}
		return dataset;
	}

	/**
	 * if the term i exists in document d, then the document d will have 1 in
	 * column i, else 0
	 * 
	 * @return
	 */
	public List<SampleData> buildDatasetWithTE() {
		init();
		for (int i = 0; i < NUM_OF_DOC; i++) {
			String docPath = DATASET_PATH_PREFIX + i;// TODO
			tokenize(docPath);
		}
		List<SampleData> dataset = new ArrayList<>();
		int numOfAttr = strToId.size();
		System.out.println(numOfAttr);
		for (int i = 0; i < NUM_OF_DOC; i++) {
			List<Double> attributes = new ArrayList<>();
			for (int j = 0; j < numOfAttr; j++) {
				if (tf[i].containsKey(j)) {
					attributes.add(1d);
				} else {
					attributes.add(0d);
				}
			}
			dataset.add(new SampleData(attributes, i));
		}
		return dataset;
	}

	private void tokenize(String fileName) {
		tf[docId] = new HashMap<Integer, Integer>();
		Map<Integer, Integer> currDocTF = tf[docId++];
		try (BufferedReader br = new BufferedReader(new FileReader(new File(
				fileName)))) {
			Tokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
			String str = null;
			while ((str = br.readLine()) != null) {
				String[] tokens = tokenizer.tokenize(str);
				for (String token : tokens) {
					// stemming
					token = stem(token);
					// remove from stop list
					if (token == null || token.length() == 0
							|| stopList.contains(token)) {
						continue;
					}
					if (!strToId.containsKey(token)) {
						strToId.put(token, strId++);
						// TODO del trace
						// System.out.println(token + "~" + strToId.get(token));
					}
					if (!currDocTF.containsKey(strToId.get(token))) {
						currDocTF.put(strToId.get(token), 1);
					} else {
						currDocTF.put(strToId.get(token),
								currDocTF.get(strToId.get(token)) + 1);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String stem(String str) {
		PorterStemmer stemmer = new PorterStemmer();
		str = stemmer.stem(str.toLowerCase());
		char[] chars = str.toCharArray();
		int writeIdx = 0;
		for (int i = 0; i < chars.length; i++) {
			if (!puncList.contains(chars[i])) {
				chars[writeIdx++] = chars[i];
			}
		}
		return new String(chars, 0, writeIdx);
	}

	private void init() {
		// read in stop word
		try (BufferedReader br = new BufferedReader(new FileReader(new File(
				STOP_LIST_PATH)))) {
			String str = null;
			while ((str = br.readLine()) != null) {
				if (!stopList.contains(str)) {
					stopList.add(str);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
