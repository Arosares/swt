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

public class AprioriAnalyzer implements Analyzer {

	private int failedPercentage;
	private int minSupport;
	private double minConfidence;

	// A list containing all testedClasses
	List<TestedClass> items = new LinkedList<TestedClass>();
	// A list containing all TestRuns
	List<TestRun> transactions = new LinkedList<TestRun>();

	HashMap<List<TestedClass>, Integer> itemSet;

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
		this.failedPercentage = 15;
	}
	
	@Override
	public void analyze() {
		System.out.println("Started Apriori with minimum support of " + this.minSupport);

		List<HashMap<List<TestedClass>, Integer>> frequentItemSets = getFrequentItemSets();
		
		
		// DEBUG
		for (HashMap<List<TestedClass>, Integer> frqItemSet : frequentItemSets) {
			printItemSet(frqItemSet);
		}
		
		// generate strong rules from the frequent item sets
		generateStrongRules(frequentItemSets);
	}

	private void generateStrongRules(List<HashMap<List<TestedClass>, Integer>> frequentItemSets) {
		// TODO To be implemented!

	}

	/**
	 * Generates the frequent item sets by steadily increasing length k. Item
	 * sets below the minimum support are pruned and therefore not used in
	 * further computations.
	 * 
	 * @return The list of all frequent item sets, where the first entry is the
	 *         basis item set and the last entry represents the relevant
	 *         frequent item set
	 */
	private List<HashMap<List<TestedClass>, Integer>> getFrequentItemSets() {
		// Generate first item set as basis for the algorithm
		HashMap<List<TestedClass>, Integer> itemSet = getFirstItemSet();
		HashMap<List<TestedClass>, Integer> frqItemSet = pruneItemSet(itemSet, minSupport);

		// Helper to keep track of the last non empty frequent item set
		HashMap<List<TestedClass>, Integer> oldFrqItemSet = new HashMap<List<TestedClass>, Integer>();

		// List to store all frequent item sets
		List<HashMap<List<TestedClass>, Integer>> frequentItemSets = new ArrayList<HashMap<List<TestedClass>, Integer>>();

		int k = 1;

		while (!frqItemSet.isEmpty()) {
			oldFrqItemSet = frqItemSet;

			// Adds a copy of the last frequent item set to the output list
			HashMap<List<TestedClass>, Integer> itemSetCopy = new HashMap<List<TestedClass>, Integer>();
			itemSetCopy.putAll(frqItemSet);
			frequentItemSets.add(itemSetCopy);

			itemSet = initializeNewItemSet(frqItemSet, k + 1);
			updateItemSet(itemSet);

			itemSet = pruneItemSet(itemSet, minSupport);
			frqItemSet = eliminateInsignificantItemSets(itemSet, frqItemSet);

			k++;
		}

		System.out.println("Computing of Apriori done:");
		printItemSet(oldFrqItemSet);
		return frequentItemSets;
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
		// http://stackoverflow.com/questions/29910312/algorithm-to-get-all-the-combinations-of-size-n-from-an-array-java

		// TODO: Take class distance into account
		// -> Would do this later for changing filter without need of recalculating!

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
