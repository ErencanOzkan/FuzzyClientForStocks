package com.stocks.client.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fuzzy.stocks.model.FuzzyData;

public class FuzzyClientImplTest {

	private FuzzyClientImpl service;

	@Before
	public void initializeTest() {

		service = (FuzzyClientImpl) new FuzzyClientImpl.FuzzyClientBuilder().build();

	}

	@Test
	public void calculateMembershipFunctionValues_Nothing_MembershipValues() {
		boolean result = service.calculateMembershipFunctionValues();
		assertTrue(result);
	}
	
	@Test
	public void constructNewMemberShipFunctions_Nothing_MembershipFunctions() {
		boolean result = service.calculateMembershipFunctionValues();
		result = service.constructNewMemberShipFunctions();
		assertTrue(result);
	}
	
	@Test
	public void calculateInteferenceValues_Nothing_MembershipFunctions() {
		boolean result = service.calculateMembershipFunctionValues();
		result = service.constructNewMemberShipFunctions();
		result = service.calculateInteferenceValues();
		assertTrue(result);
	}


}
