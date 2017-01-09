package tda.src.logic.apriori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import tda.src.logic.Analyzer;
import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;

/**
 * @author Tobias Schwartz
 * @version 08.01.2017
 *
 */
public class AprioriAnalyzer implements Analyzer {

	private int minSupport;
	private double minConfidence;

	// A list containing all testedClasses
	List<TestedClass> items = new LinkedList<TestedClass>();
	// A list containing all TestRuns
	List<TestRun> transactions = new LinkedList<TestRun>();

	// All item sets with a set (list) of testedClasses as key and its support
	// count as value
	HashMap<List<TestedClass>, Integer> itemSet;

	// -- CACHE --
	// Stores all item sets and rules for all test runs
	// Filtering can then be done fast over cached entries
	List<StrongRule> cachedStrongRules = new LinkedList<>();
	HashMap<List<TestedClass>, Integer> cachedFrequentItemSets = new HashMap<>();

	private final int failedPercentage = 15;

	/**
	 * Default constructor with 
	 * support = number of transactions / 2
	 * confidence = 80 %
	 */
	public AprioriAnalyzer() {
		newAprioriAnalyzer(-1, 0.8);
	}

	public AprioriAnalyzer(int minSupport, double minConfidence) {
		newAprioriAnalyzer(minSupport, minConfidence);
	}

	private void newAprioriAnalyzer(int minSupport, double minConfidence) {
		items = TestData.getInstance().getTestedClasses();
		transactions = TestData.getInstance().getTestRunList();

		itemSet = new HashMap<List<TestedClass>, Integer>();

		this.minSupport = (minSupport >= 0) ? minSupport : (transactions.size() / 2);
		this.minConfidence = minConfidence;
	}

	@Override
	public void analyze() {
		System.out.println("Starting Apriori with minimum support of " + this.minSupport);

		// 1. Generate frequent item sets
		HashMap<List<TestedClass>, Integer> frequentItemSet = getMaxFrequentItemSet();

		// 2. Generate strong rules from frequent item sets
		List<StrongRule> strongRules = generateStrongRules(frequentItemSet, minConfidence);
		for (StrongRule strongRule : strongRules) {
			strongRule.print();
		}
	}

	/**
	 * Generates the strong rules from a given frequent item set and filters by
	 * a given confidence threshold
	 * 
	 * @param frequentItemSet
	 *            The frequent item set used for generation of strong rules
	 * @param confidence
	 *            The minimum confidence for a successful strong rule
	 * @return A filtered {@link list} of {@link StrongRule}
	 */
	private List<StrongRule> generateStrongRules(HashMap<List<TestedClass>, Integer> frequentItemSet,
			double confidence) {
		List<StrongRule> strongRules = generateStrongRules(frequentItemSet);

		return strongRules.stream().filter(rule -> rule.getConfidence() >= confidence).sorted()
				.collect(Collectors.toList());
	}

	private List<StrongRule> generateStrongRules(HashMap<List<TestedClass>, Integer> frequentItemSet) {

		List<StrongRule> strongRules = new LinkedList<>();

		for (Entry<List<TestedClass>, Integer> entry : frequentItemSet.entrySet()) {
			List<TestedClass> entryKey = entry.getKey();

			System.out.println("Power Set of key " + entryKey);
			HashMap<List<TestedClass>, Integer> powerItemSet = getPowerSet(entryKey);

			updateItemSet(powerItemSet);
			printItemSet(powerItemSet);

			System.out.println("Generate Strong Rules for " + entryKey);

			strongRules.addAll(generateStrongRulesForEntry(powerItemSet, entryKey));
			
			System.out.println(" --- ");

		}

		return strongRules;
	}

	private List<StrongRule> generateStrongRulesForEntry(HashMap<List<TestedClass>, Integer> powerItemSet,
			List<TestedClass> fullKey) {
		List<StrongRule> strongRules = new LinkedList<>();

		// S -> (I - S)
		for (Entry<List<TestedClass>, Integer> entry : powerItemSet.entrySet()) {
			List<TestedClass> entryKey = entry.getKey();
			if (entryKey.size() >= 1 && entryKey.size() < fullKey.size()) {
				List<TestedClass> leftSide = entryKey;
				List<TestedClass> rightSide = new LinkedList<>();
				rightSide.addAll(fullKey);
				rightSide.removeAll(leftSide);

				// conf = support (I) / support (S)
				double confidence = (double) powerItemSet.get(fullKey) / entry.getValue();
				StrongRule strongRule = new StrongRule(leftSide, rightSide, confidence);
				strongRules.add(strongRule);
				strongRule.print();
			}
		}
		return strongRules;
	}

	private HashMap<List<TestedClass>, Integer> getPowerSet(List<TestedClass> testedClasses) {
		HashMap<List<TestedClass>, Integer> powerItemSet = new HashMap<>();
		
		for (int i = 1; i <= testedClasses.size(); i++) {
			HashMap<List<TestedClass>, Integer> tmp = generateFixedSubset(i, testedClasses);
			System.out.println(i + " iteration:");
			printItemSet(tmp);
			powerItemSet.putAll(tmp);
		}

		return powerItemSet;
	}

	/**
	 * Generates the maximal frequent item set by steadily increasing length k. Item
	 * sets below the minimum support are pruned and therefore not used in
	 * further computations.
	 * 
	 * @return The maximal frequent item set
	 */
	private HashMap<List<TestedClass>, Integer> getMaxFrequentItemSet() {
		// Generate first item set as basis for the algorithm
		HashMap<List<TestedClass>, Integer> itemSet = getFirstItemSet();
		HashMap<List<TestedClass>, Integer> frqItemSet = pruneItemSet(itemSet, minSupport);

		// Helper to keep track of the last non empty frequent item set
		HashMap<List<TestedClass>, Integer> oldFrqItemSet = new HashMap<List<TestedClass>, Integer>();

		// Max frequent item set - to be returned
		HashMap<List<TestedClass>, Integer> maxFrequentItemSet = new HashMap<List<TestedClass>, Integer>();

		// length of item set
		int k = 1;

		while (!frqItemSet.isEmpty()) {
			oldFrqItemSet = frqItemSet;
			 
			itemSet = initializeNewItemSet(frqItemSet, k + 1);
			updateItemSet(itemSet);

			itemSet = pruneItemSet(itemSet, minSupport);
			frqItemSet = eliminateInsignificantItemSets(itemSet, oldFrqItemSet);
			
			/**
			* if an entry of the old item set is not a subset of any new frq item set
			* (i.e. possible rule but no further correlations)
			* and its key size is greater than 2 (i.e. a rule can be obtained)
			* then add this entry to the max frequent item set
			*/
			for (Entry<List<TestedClass>, Integer> entry : oldFrqItemSet.entrySet()) {
				if (entry.getKey().size() >= 2 && !isSubset(entry, frqItemSet)) {
					maxFrequentItemSet.putIfAbsent(entry.getKey(), entry.getValue());
				}
			}
			
			k++;
		}

		System.out.println("Computing of Apriori done");
		System.out.println("Max frequent item set:");
		printItemSet(maxFrequentItemSet);
		return maxFrequentItemSet;
	}
	
	
	/**
	 * An entry e is the subset of another item set I, 
	 * if there exists at least one entry e' in I
	 * so that e' contains all elements of e.
	 *    
	 * @return
	 * 		true if e is subset of I
	 */
	private boolean isSubset(Entry<List<TestedClass>, Integer> entry, HashMap<List<TestedClass>, Integer> itemSet) {
		return itemSet.keySet().stream().anyMatch(key -> key.containsAll(entry.getKey()));
	}

	private HashMap<List<TestedClass>, Integer> eliminateInsignificantItemSets(
			HashMap<List<TestedClass>, Integer> itemSet, HashMap<List<TestedClass>, Integer> oldItemSet) {
		HashMap<List<TestedClass>, Integer> result = new HashMap<>();

		for (Entry<List<TestedClass>, Integer> entry : itemSet.entrySet()) {
			if (generateFixedSubset(entry.getKey().size() - 1, entry.getKey()).entrySet().stream()
					.allMatch(e -> oldItemSet.containsKey(e.getKey()))) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		return result;
	}

	private void updateItemSet(HashMap<List<TestedClass>, Integer> itemSet) {
		for (TestRun testRun : transactions) {
			for (Entry<List<TestedClass>, Integer> entry : itemSet.entrySet()) {
				if (getFailedClasses(testRun).containsAll(entry.getKey())) {
					incrementItemSetValue(itemSet, entry.getKey());
				}
			}
		}
	}

	private HashMap<List<TestedClass>, Integer> initializeNewItemSet(HashMap<List<TestedClass>, Integer> itemSet,
			int length) {
		List<TestedClass> allTestedClasses = extractTestedClasses(itemSet);

		return generateFixedSubset(length, allTestedClasses);
	}

	private List<TestedClass> extractTestedClasses(HashMap<List<TestedClass>, Integer> itemSet) {
		List<TestedClass> result = new LinkedList<TestedClass>();
		for (Entry<List<TestedClass>, Integer> entry : itemSet.entrySet()) {
			for (TestedClass testedClass : entry.getKey()) {
				if (!result.contains(testedClass)) {
					result.add(testedClass);
				}
			}
		}
		return result;
	}

	private HashMap<List<TestedClass>, Integer> pruneItemSet(HashMap<List<TestedClass>, Integer> itemSet,
			int minSupport) {
		HashMap<List<TestedClass>, Integer> frqItemSet = new HashMap<>();
		for (Entry<List<TestedClass>, Integer> entry : itemSet.entrySet()) {
			if (entry.getValue() >= Integer.valueOf(minSupport)) {
				frqItemSet.put(entry.getKey(), entry.getValue());
			}
		}
		return frqItemSet;
	}

	/**
	 * Maps every TestRun to its TestedClasses with a FP higher then a certain
	 * bound (field: failurePercentage)
	 * 
	 * @return HasMap, containing one List for each TestRun containing the
	 *         selected TestedClasses
	 */
	private HashMap<List<TestedClass>, Integer> getFirstItemSet() {
		return transactions.stream().map(testRun -> getFailedClasses(testRun)).collect(HashMap::new,
				(itemSet, testedClass) -> addTestedClassesToFirstItemSet(itemSet, testedClass), HashMap::putAll);
	}

	private void addTestedClassesToFirstItemSet(HashMap<List<TestedClass>, Integer> itemSet,
			List<TestedClass> testedClasses) {
		for (TestedClass testedClass : testedClasses) {
			addTestedClassToItemSet(itemSet, testedClass);
		}
	}

	private void addTestedClassToItemSet(HashMap<List<TestedClass>, Integer> itemSet, TestedClass testedClass) {
		List<TestedClass> testedClassList = new LinkedList<TestedClass>();
		testedClassList.add(testedClass);
		incrementItemSetValue(itemSet, testedClassList);
	}

	private void incrementItemSetValue(HashMap<List<TestedClass>, Integer> itemSet, List<TestedClass> key) {
		Integer value = itemSet.getOrDefault(key, 0);
		itemSet.put(key, value + 1);
	}

	private void printItemSet(HashMap<List<TestedClass>, Integer> itemSet) {
		Set<List<TestedClass>> keySet = itemSet.keySet();
		for (List<TestedClass> key : keySet) {
			System.out.print("(");
			for (TestedClass testedClass : key) {
				System.out.print("[" + testedClass.getClassName() + "], ");
			}
			System.out.print(itemSet.get(key) + ")\n");
		}
		System.out.println();
	}

	private List<TestedClass> getFailedClasses(TestRun testRun) {
		return getFailedClasses(failedPercentage, testRun);
	}

	/**
	 * @param failurePercentage
	 *            the min FP a class should have in the List
	 * @param testRun
	 *            The TestRun which will be searched for classes
	 * @return List of TestedClasses containing only classes with higher FP as
	 *         the param failurePercentage
	 */
	private List<TestedClass> getFailedClasses(double failurePercentage, TestRun testRun) {
		return items.stream().parallel()
				.filter(testedClass -> testedClass.getFailurePercentageByTestrun(testRun) > failurePercentage)
				.collect(Collectors.toList());
	}

	private HashMap<List<TestedClass>, Integer> generateFixedSubset(int length, List<TestedClass> allTestedClasses) {
		// generating all possible combinations with length k
		// based on: http://stackoverflow.com/questions/29910312/algorithm-to-get-all-the-combinations-of-size-n-from-an-array-java

		HashMap<List<TestedClass>, Integer> newItemSet = new HashMap<>();

		int[] indices = new int[length];

		if (length <= allTestedClasses.size()) {
			// first index sequence: 0, 1, 2, ...
			for (int i = 0; (indices[i] = i) < length - 1; i++)
				;
			newItemSet.put(getSubset(allTestedClasses, indices), 0);
			for (;;) {
				int i;
				// find position of item that can be incremented
				for (i = length - 1; i >= 0 && indices[i] == allTestedClasses.size() - length + i; i--)
					;
				if (i < 0) {
					break;
				} else {
					indices[i]++; // increment this item
					for (++i; i < length; i++) { // fill up remaining items
						indices[i] = indices[i - 1] + 1;
					}
					newItemSet.put(getSubset(allTestedClasses, indices), 0);
				}
			}
		}
		return newItemSet;
	}

	// generate actual subset by index sequence
	List<TestedClass> getSubset(List<TestedClass> allTestedClasses, int[] subset) {
		List<TestedClass> result = new ArrayList<>(subset.length);

		for (int i = 0; i < subset.length; i++) {
			result.add(i, allTestedClasses.get(subset[i]));
		}

		return result;
	}
}
