package tda.src.logic.apriori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;

public class AprioriAnalyzer {

	private int failedPercentage;
	private int minSupport;
	private double minConfidence;

	List<TestedClass> items = new LinkedList<TestedClass>();
	List<TestRun> transactions = new LinkedList<TestRun>();

	HashMap<List<TestedClass>, Integer> itemSet;

	public AprioriAnalyzer() {
		items = TestData.getInstance().getTestedClasses();
		transactions = TestData.getInstance().getTestRunList();

		HashMap<List<TestedClass>, Integer> itemSet = new HashMap<List<TestedClass>, Integer>();

		this.failedPercentage = 15;
	}

	public void analyze(int minSupport, double minConfidence) {
//		this.minSupport = minSupport;
		this.minConfidence = minConfidence;
		this.minSupport = transactions.size() / 2;
		
		System.out.println("Started Apriori with minimum support of " + this.minSupport);
		
		HashMap<List<TestedClass>, Integer> itemSet1 = getFirstItemSet();
		HashMap<List<TestedClass>, Integer> frequentItemSet1 = pruneItemSet(itemSet1, minSupport);
		
		HashMap<List<TestedClass>, Integer> oldFrqItemSet = frequentItemSet1;
		HashMap<List<TestedClass>, Integer> frqItemSet;
		
		printItemSet(itemSet1);
		System.out.println();
		printItemSet(frequentItemSet1);

		int k = 1;
		
		System.out.println();
		
		itemSet = initializeNewItemSet(frequentItemSet1, k + 1);
		updateItemSet(itemSet);
		
		printItemSet(itemSet);
		System.out.println();
		
		itemSet = pruneItemSet(itemSet, minSupport);
		// frqItemSet = eliminateInsignificantItemSets(itemSet, oldFrqItemSet);
		
		printItemSet(itemSet);
		
		
		
	}


	private HashMap<List<TestedClass>, Integer> eliminateInsignificantItemSets(HashMap<List<TestedClass>, Integer> itemSet,
			HashMap<List<TestedClass>, Integer> oldItemSet) {
		HashMap<List<TestedClass>, Integer> result = new HashMap<>();
		
		List<List<TestedClass>> compareList = oldItemSet.entrySet().stream()
				.map(Entry::getKey)
				.collect(ArrayList::new, ArrayList::add, List::addAll);
		
		System.out.println(compareList);
		
		for (Entry<List<TestedClass>, Integer> entry : itemSet.entrySet()) {
			System.out.println(entry.getKey());
			if (compareList.stream().peek(System.out::println).anyMatch(tc -> entry.getKey().contains(tc))) {
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

	private HashMap<List<TestedClass>, Integer> initializeNewItemSet(
			HashMap<List<TestedClass>, Integer> itemSet, int length) {
		List<TestedClass> allTestedClasses = extractTestedClasses(itemSet);
		
		return generateFixedSubset(length, allTestedClasses);
	}

	private List<TestedClass> extractTestedClasses(HashMap<List<TestedClass>, Integer> itemSet) {
		return itemSet.entrySet().stream().parallel()
				.map(Entry::getKey)
				.collect(ArrayList::new, List::addAll, List::addAll);
	}

	private HashMap<List<TestedClass>, Integer> pruneItemSet(HashMap<List<TestedClass>, Integer> itemSet, int minSupport) {
		HashMap<List<TestedClass>, Integer> frqItemSet = new HashMap<>();
		for(Entry<List<TestedClass>, Integer> entry : itemSet.entrySet()) {
			if(entry.getValue() >= Integer.valueOf(minSupport)) {
				frqItemSet.put(entry.getKey(), entry.getValue());
			}
		}
		return frqItemSet;
	}

	private HashMap<List<TestedClass>, Integer> getFirstItemSet() {
		return transactions
				.stream()
				.map(testRun -> getFailedClasses(testRun))
				.collect(HashMap::new, (itemSet, testedClass) -> addTestedClassesToFirstItemSet(itemSet, testedClass), HashMap::putAll); 
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
	}

	private List<TestedClass> getFailedClasses(TestRun testRun) {
		return getFailedClasses(failedPercentage, testRun);
	}

	private List<TestedClass> getFailedClasses(double failurePercentage,
			TestRun testRun) {
		return items
				.stream()
				.parallel()
				.filter(testedClass -> testedClass
						.getFailurePercentageByTestrun(testRun) > failurePercentage)
				.collect(Collectors.toList());
	}
	
	private HashMap<List<TestedClass>, Integer> generateFixedSubset(int length, List<TestedClass> allTestedClasses) {
		// generating all possible combinations with length k
		// http://stackoverflow.com/questions/29910312/algorithm-to-get-all-the-combinations-of-size-n-from-an-array-java
		
		HashMap<List<TestedClass>, Integer> newItemSet = new HashMap<>();

		int[] indices = new int[length];

		if (length <= allTestedClasses.size()) {
		    // first index sequence: 0, 1, 2, ...
		    for (int i = 0; (indices[i] = i) < length - 1; i++);  
		    newItemSet.put(getSubset(allTestedClasses, indices), 0);
		    for(;;) {
		        int i;
		        // find position of item that can be incremented
		        for (i = length - 1; i >= 0 && indices[i] == allTestedClasses.size() - length + i; i--); 
		        if (i < 0) {
		            break;
		        } else {
		            indices[i]++;                    // increment this item
		            for (++i; i < length; i++) {    // fill up remaining items
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
	    for (int i = 0; i < subset.length; i++) 
	        result.add(i, allTestedClasses.get(subset[i]));
	    return result;
	}
}
