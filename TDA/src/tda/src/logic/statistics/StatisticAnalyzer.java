package tda.src.logic.statistics;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tda.src.logic.TestData;
import tda.src.logic.TestedClass;
import tda.src.logic.UnitTestsToTestRunMapper;
import tda.src.logic.apriori.StrongRule;

public class StatisticAnalyzer {

	public static List<TestedClass> getMostRelevantClasses(double confidence, int distance) {
		List<TestedClass> allClasses = TestData.getInstance().getTestedClasses();
		double threshold = 0;

		HashMap<TestedClass, Double> classSet = new HashMap<>(allClasses.size());

		// FP Average Of Class
		for (TestedClass testedClass : allClasses) {
			int numClassTestRuns = testedClass.getClassLog().size();
			double sumFailurePercentage = 0;
			for (UnitTestsToTestRunMapper mapper : testedClass.getClassLog()) {
				sumFailurePercentage += mapper.getFailurePercentage() / 100;
			}
			Double val = classSet.getOrDefault(testedClass, new Double(0));
			val += sumFailurePercentage / numClassTestRuns;
			classSet.put(testedClass, val);
		}

		// Apriori
		List<StrongRule> strongRules = TestData.getInstance().getAnalyzer().getStrongRules(confidence, distance);
		for (TestedClass testedClass : allClasses) {
			double sumConf = 0;
			int occCount = 0;
			for (StrongRule strongRule : strongRules) {
				if (strongRule.getLeftSide().contains(testedClass)) {
					sumConf += strongRule.getConfidence() / 100;
					occCount++;
				}
			}

			if (occCount > 0) {
				Double val = classSet.getOrDefault(testedClass, new Double(0));
				val += Math.sqrt(sumConf) / Math.sqrt(occCount);
				classSet.put(testedClass, val / 2);
			}
		}

		return classSet.entrySet().stream().filter(entry -> (entry.getValue() > threshold))
				.sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(LinkedList::new, (list, map) -> list.add(map.getKey()), LinkedList::addAll);
	}
}
