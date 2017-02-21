package tda.src.gui.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import javafx.scene.control.TreeItem;
import tda.src.datastructure.TestRun;
import tda.src.datastructure.TestedClass;
import tda.src.exceptions.WrongXMLAttributeException;
import tda.src.logic.parser.Parser;
import tda.src.logic.parser.StAXParser;

/**
 * Model of the MVC - Pattern for the view
 */
public class Model extends Observable {
	/**
	 * <pre>
	 *           1..1     1..1
	 * Model ------------------------> Parser
	 *           model        &gt;       parser
	 * </pre>
	 */
	private Parser parser;

	/**
	 * Constructor to instantiate the {@code Model}. Creates an instance of
	 * class {@code StAXParser}.
	 */
	public Model() {
		super();
		// initialize program:
		parser = new StAXParser();

	}

	/**
	 * Sets the {@code Parser} of this {@code Model}.
	 * 
	 * @param value
	 *            - The {@code Parser} to be set.
	 */
	public void setParser(Parser value) {
		this.parser = value;
	}

	/**
	 * Returns the {@code Parser} of this {@code Model}.
	 * 
	 * @return {@code Parser}
	 */
	public Parser getParser() {
		return this.parser;
	}

	/**
	 * <pre>
	 *           1..1     0..*
	 * Model ------------------------> TestRun
	 *           model        &gt;       testRun
	 * </pre>
	 */
	private Set<TestRun> testRun;

	/**
	 * Returns the {@code HashSet} of {@code TestRun} of this {@code Model}. If
	 * no {@code HashSet} of {@code TestRun} exists, a new one is created.
	 * 
	 * @return {@code HashSet} of {@code TestRun}
	 */
	public Set<TestRun> getTestRun() {
		if (this.testRun == null) {
			this.testRun = new HashSet<TestRun>();
		}
		return this.testRun;
	}

	/**
	 * Calls the method {@code parse()} of the {@code Parser} of this
	 * {@code Model}.
	 * 
	 * @param xmlPath
	 *            - A {@code String} containing the location of the XML file to
	 *            be parsed.
	 * 
	 * @throws {@code
	 *             WrongXMLAttributeException} if an invalid attribute is found
	 *             in the XML file during the parsing process.
	 */
	public void parseFile(String xmlPath) {
		try {
			parser.parse(xmlPath);
		} catch (WrongXMLAttributeException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a {@code List} of {@code TestedClass} associated with the passed
	 * {@code TestRun}.
	 * 
	 * @param testRun
	 *            - The {@code TestRun} of which the {@code List} of
	 *            {@code TestedClass} is returned.
	 * 
	 * @return {@code List} of {@code TestedClass}
	 */
	public List<TestedClass> getTestedClassesFromTestRun(TestRun testRun) {
		List<TestedClass> classesOfOneRun = testRun.getTestedClasses();

		for (TestedClass testedClass : classesOfOneRun) {
			testedClass.setCurrentFailurePercentage(testRun);
		}

		return classesOfOneRun;

	}

	public String getLicense() throws IOException {
		String license = "";

		try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("License.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
			String line = reader.readLine();

			while ((line = reader.readLine()) != null) {
				license += line + "\n";
			}
		}

		return license;
	}

	public String getManual() throws IOException {
		String manual = "";
		try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("ReadMe.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
			String line = reader.readLine();

			while ((line = reader.readLine()) != null) {
				manual += line + "\n";
			}
		}

		return manual;
	}

	/**
	 * Generates a Tree showing strings of the names of all tested Classes, and
	 * the folders containing them.
	 * 
	 * @param rootDirectory
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public TreeItem<TestRun> createTreeItems(String rootDirectory) {

		// Not sure if working on windows with this regex
		String[] rootFolder = rootDirectory.split("/|\\\\");

		@SuppressWarnings("unchecked")
		TreeItem rootItem = new TreeItem(rootFolder[rootFolder.length - 1]);
		Path rootPath = Paths.get(rootDirectory);
		File[] files = rootPath.toFile().listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// Create new TreeItem as root with children
				TreeItem subRoot = createTreeItems(file.toString());
				if (subRoot.getChildren().size() != 0) {
					rootItem.getChildren().add(subRoot);
				}
			} else {
				String lowerCaseFile = file.toString().toLowerCase();
				if (lowerCaseFile.endsWith(".xml") && lowerCaseFile.contains("testrun")) {
					// Create new TreeItem as leaf and add to rootItem
					TreeItem<TestRun> treeItem = new TreeItem(file.getName());
					rootItem.getChildren().add(treeItem);
				}
			}
		}
		return rootItem;
	}

}
