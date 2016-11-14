package com.stocks.client.reader;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.fuzzy.stocks.model.FuzzyData;
import com.stocks.client.reader.DataReader;

public class DataReaderTest {

	@Test
	public void readFuzzyDataTest_Json_ListOfFuzzyData() {

		List<FuzzyData> fuzzyData = DataReader.readFuzzyData();

		assertTrue(fuzzyData != null);
		assertTrue(fuzzyData.size() > 0);
	}
}
