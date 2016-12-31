package tda.src.logic.apriori;

import java.util.List;

import tda.src.logic.TestedClass;

public class StrongRule implements Comparable<StrongRule>{

	private List<TestedClass> leftSide;
	private List<TestedClass> rightSide;
	private double confidence;

	public StrongRule(List<TestedClass> leftSide, List<TestedClass> rightSide, double confidence) {
		this.leftSide = leftSide;
		this.rightSide = rightSide;
		setConfidence(confidence);
	}

	public List<TestedClass> getLeftSide() {
		return leftSide;
	}

	public void setLeftSide(List<TestedClass> leftSide) {
		this.leftSide = leftSide;
	}

	public List<TestedClass> getRightSide() {
		return rightSide;
	}

	public void setRightSide(List<TestedClass> rightSide) {
		this.rightSide = rightSide;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		// limit double to two digits after comma
		int round = (int) Math.round(confidence * 100);
		this.confidence = round / 100.0;
	}
	
	public void print() {
		System.out.println(toString());
	}
	
	public String toString() {
		return leftSide + " -> " + rightSide + " | " + confidence + "%";
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
}
