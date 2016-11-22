package com.stocks.client.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.fuzzy.stocks.model.FuzzyData;
import com.fuzzy.stocks.service.*;
import com.fuzzy.stocks.service.impl.FuzzyInferenceServiceImpl;
import com.fuzzy.stocks.service.impl.FuzzyMembershipConstructionServiceImpl;
import com.fuzzy.stocks.service.impl.FuzzyMembershipServiceImpl;
import com.stocks.client.FuzzyClient;
import com.stocks.client.reader.DataReader;

public class FuzzyClientImpl implements FuzzyClient {

	FuzzyMembershipService fuzzyMembershipService;
	FuzzyMembershipConstructionService fuzzyMembershipConstructionService;
	FuzzyInferenceService fuzzyInferenceService;

	List<FuzzyData> rawData;

	private FuzzyClientImpl() {

	}

	public static class FuzzyClientBuilder {

		public FuzzyClientBuilder() {
		}

		public FuzzyClient build() {

			FuzzyClientImpl client = new FuzzyClientImpl();
			List<FuzzyData> data = DataReader.readFuzzyData();

			client.fuzzyMembershipService = new FuzzyMembershipServiceImpl.FuzzyMembershipServiceBuilder(data).build();
			client.rawData = new ArrayList<FuzzyData>();
			client.rawData.addAll(data);
			return client;

		}
	}

	public boolean calculateMembershipFunctionValues() {
		boolean methodStatus;

		methodStatus = fuzzyMembershipService.sortRawData();
		if(methodStatus){
			methodStatus = fuzzyMembershipService.prepareDifferenceSequence();
		} else{
			return false;
		}
		if(methodStatus){
			methodStatus = fuzzyMembershipService.calculateStandartDerivationOfFees();
		} else{
			return false;
		}
		if(methodStatus){
			methodStatus = fuzzyMembershipService.calculateSimilarities();
		} else{
			return false;
		}
		if(methodStatus){
			methodStatus = fuzzyMembershipService.groupDataBasedOnSimilarities();
		} else{
			return false;
		}
		if(methodStatus){
			methodStatus = fuzzyMembershipService.calculateCenterPointB();
		} else{
			return false;
		}
		if(methodStatus){
			methodStatus = fuzzyMembershipService.calculateLeftCornerPointA();
		} else{
			return false;
		}
		if(methodStatus){
			methodStatus = fuzzyMembershipService.calculateRigthCornerPointC();
		} else{
			return false;
		}

		if(methodStatus){
			methodStatus = fuzzyMembershipService.findTheMembershipValues();
		} else{
			return false;
		}
		fuzzyMembershipConstructionService = (FuzzyMembershipConstructionServiceImpl) new FuzzyMembershipConstructionServiceImpl.FuzzyMembershipConstructionServiceBuilder(
				fuzzyMembershipService.getPreparedData()).build();
		return true;
	}

	public boolean constructNewMemberShipFunctions() {
		fuzzyMembershipConstructionService.constructInitialDecisionTable();

		fuzzyMembershipConstructionService.mergeAdjacentColumnsIfTheyAreSame();

		fuzzyMembershipConstructionService.mergeAdjacentRowsIfTheyAreSame();

		fuzzyMembershipConstructionService.mergeAdjacentColumsForOperation2();

		fuzzyMembershipConstructionService.mergeAdjacentRowsForOperation2();

		fuzzyMembershipConstructionService.mergeColumnsForOperation3();

		fuzzyMembershipConstructionService.mergeRowsForOperation3();

		fuzzyMembershipConstructionService.mergeColumnsForOperation4();

		fuzzyMembershipConstructionService.mergeRowsForOperation4();

		fuzzyMembershipConstructionService.mergeColumnsForOperation5();

		fuzzyMembershipConstructionService.mergeRowsForOperation5();

		return true;
	}

	public boolean calculateInteferenceValues() {

		List<Double> centralPoints = fuzzyMembershipService.getCentralPoints();

		fuzzyInferenceService = new FuzzyInferenceServiceImpl.FuzzyInferenceServiceBuilder(fuzzyMembershipConstructionService.getPropertyMembershipRules(),
				fuzzyMembershipConstructionService.getAgeMembershipRules(), centralPoints).build();

		for(FuzzyData data : rawData){
			double result = fuzzyInferenceService.calculateInteferenceValue(data.getAge(), data.getProperty());

			System.out.println("Karlılık -> " + data.getAge() + " | Defter Değeri -> " + data.getProperty() + " | Hisse Senedindeki Değişim  -> " + result);
		}

		return true;

	}

}
