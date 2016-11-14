package com.stocks.client.reader;

import java.io.File;
import java.util.List;
import java.util.function.Function;

import com.fuzzy.stocks.model.FuzzyData;
import com.fuzzy.stocks.util.FuzzyMembershipDataReader;

public class DataReader {

	private final static String fileName = "data.json";
	
	public static List<FuzzyData> readFuzzyData() {
		
		File fuzzyDataFile = getCustomerFileReader.apply(fileName);
		return FuzzyMembershipDataReader.readFuzzyData(fuzzyDataFile);
	}
	
	private static Function<String, File> getCustomerFileReader = filename -> {
		ClassLoader cl = FuzzyMembershipDataReader.class.getClassLoader();
		File fuzzyData = new File(cl.getResource(filename).getFile());
		return fuzzyData;
	};
}
