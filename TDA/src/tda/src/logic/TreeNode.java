package tda.src.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeNode {
	
	String key;
	TestedClass testedClass;
	List<TreeNode> children = new LinkedList<TreeNode>();
	
	public TreeNode(String key, TestedClass testedClass) {
		this.key = key;
		this.testedClass = testedClass;
	}
	
	public void insert(Queue<String> packageName, TestedClass testedClass) {
		if(packageName.isEmpty()) {
			TreeNode classNode = new TreeNode(testedClass.getClassName(), testedClass);
			if(!children.contains(classNode)){
				children.add(classNode);
			}
		} else {
			TreeNode packageNode = new TreeNode(packageName.poll(), null);
			if(!children.contains(packageNode)){
				children.add(packageNode);
			} else {
				packageNode = children.get(children.indexOf(packageNode));
			}
			packageNode.insert(packageName, testedClass);
		}
	}
	
	/**
	 * Searches in the DataTree for the Class with className as key
	 * @param key
	 * @return the testedClass
	 */
	public TestedClass searchByName(String key){
		if (this.key.equals(key)) {
			return testedClass;
		} else {
			for (TreeNode treeNode : children) {
				TestedClass toBeFound = treeNode.searchByName(key);
				if (toBeFound != null) {
					return toBeFound;
				}
			}
		}
		
		return null;
	}
	
	public List<TestedClass> getTestedClasses(List<TestedClass> testedClasses) {
		if(testedClass != null) {
			testedClasses.add(testedClass);
		}
		for (TreeNode node : children) {
			node.getTestedClasses(testedClasses);
		}
		return testedClasses;
	}
	
	public TestedClass search(TestedClass testedClass){
		return searchByName(testedClass.getClassName());
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public TestedClass getTestedClass() {
		return testedClass;
	}

	public void setTestedClass(TestedClass testedClass) {
		this.testedClass = testedClass;
	}
	
	private String printTree(int increment) {
	    String s = "";
	    String inc = "";
	    for (int i = 0; i < increment; ++i) {
	      inc = inc + " ";
	    }
	    s = inc + key;
	    for (TreeNode child : children) {
	      s += "\n" + child.printTree(increment + 2);
	    }
	    return s;
	}
	
	@Override
	public String toString() {
		return printTree(0);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeNode other = (TreeNode) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
}
