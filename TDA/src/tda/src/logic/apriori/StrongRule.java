package tda.src.logic.apriori;

import java.util.List;

import tda.src.logic.TestData;
import tda.src.logic.TestedClass;
import tda.src.logic.TreeNode;

public class StrongRule implements Comparable<StrongRule>{

	private List<TestedClass> leftSide;
	private List<TestedClass> rightSide;
	private double confidence;
	private int maxDistance;

	public StrongRule(List<TestedClass> leftSide, List<TestedClass> rightSide, double confidence) {
		this.leftSide = leftSide;
		this.rightSide = rightSide;
		setConfidence(confidence);
		maxDistance = computeMaxDistance();
	}

	private int computeMaxDistance() {
		int maxDistance = 0;
		
		// Distance between left and right side
		for (TestedClass leftClass : leftSide) {
			for (TestedClass rightClass : rightSide) {
				int distance = getDistance(leftClass, rightClass);
				if (distance > maxDistance) {
					maxDistance = distance;
				}
			}
		}
		
		// Distance for only the left side
		int distance = getMaxDistance(leftSide);
		if (distance > maxDistance) {
			maxDistance = distance;
		}
		
		// Distance for only the right side
		distance = getMaxDistance(rightSide);
		if (distance > maxDistance) {
			maxDistance = distance;
		}
	
		return maxDistance;
	}

	private int getMaxDistance(List<TestedClass> testedClasses) {
		if (testedClasses.size() >= 2) return 0;
		
		int maxDistance = 0;
		
		for (int i = 0; i < testedClasses.size() - 1; i++) {
			for (int j = i + 1; j < testedClasses.size(); j++) {
				int distance = getDistance(testedClasses.get(i), testedClasses.get(j));
				if (distance > maxDistance) {
					maxDistance = distance;
				}
			}
		}
		return maxDistance;
	}

	private int getDistance(TestedClass classLeft, TestedClass classRight) {
		TreeNode root = TestData.getInstance().getRoot();
		TreeNode leftNode = root.searchNode(classLeft.getClassName());
		TreeNode rightNode = root.searchNode(classRight.getClassName());
		
		return leftNode.getDistance(rightNode);
	}

	public List<TestedClass> getLeftSide() {
		return leftSide;
	}

	public List<TestedClass> getRightSide() {
		return rightSide;
	}

	public double getConfidence() {
		return confidence;
	}

	private void setConfidence(double confidence) {
		// limit double to two digits after comma
		int round = (int) Math.round(confidence * 100);
		this.confidence = round / 100.0;
	}
	
	public void print() {
		System.out.println(toString());
	}
	
	public String toString() {
		return leftSide + " -> " + rightSide + " | " + confidence * 100 + "%";
	}

	@Override
	public int compareTo(StrongRule other) {
		if (other.confidence > this.confidence) {
			return 1;
		} else if (other.confidence < this.confidence) {
			return -1;
		} else {
			return 0;
		}
	}

	public int getMaxDistance() {
		return maxDistance;
	}
}
