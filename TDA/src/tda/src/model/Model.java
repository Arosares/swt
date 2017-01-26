package tda.src.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import tda.src.exceptions.WrongXMLAttributeException;
import tda.src.logic.Parser;
import tda.src.logic.StAXParser;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;

/**
 * Class to initialize the database of TDA. 
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
	 * Constructor to instantiate the {@code Model}.  
	 * Creates an instance of class {@code StAXParser}. 
	 */
	public Model() {
		super();
		// initialize program:
		parser = new StAXParser();

	}

	/**
	 * Sets the {@code Parser} of this {@code Model}. 
	 * 
	 * @param value - The {@code Parser} to be set. 
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
	 * Returns the {@code HashSet} of {@code TestRun} of this {@code Model}. 
	 * If no {@code HashSet} of {@code TestRun} exists, a new one is created. 
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
	 * Calls the method {@code parse()} of the {@code Parser} of this {@code Model}. 
	 * 
	 * @param xmlPath - A {@code String} containing the location of the XML file to be parsed. 
	 * 
	 * @throws {@code WrongXMLAttributeException} if an invalid attribute is found in the XML file during the parsing process. 
	 */
	public void parseFile(String xmlPath) {
		try {
			parser.parse(xmlPath);
		} catch (WrongXMLAttributeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns a {@code List} of {@code TestedClass} associated with the passed {@code TestRun}. 
	 * 
	 * @param testRun - The {@code TestRun} of which the {@code List} of {@code TestedClass} is returned. 
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

	public String getManual() throws IOException  {
		String manual = "";
		Path readMe = Paths.get("ReadMe/ReadMe.txt");
		
		List<String> lines = Files.readAllLines(readMe);
		for (String string : lines) {
			manual += string + "\n";
		}
		
		return manual;
	}
	
	public String getLicene() throws IOException  {
		String licence = "";
		Path licencePath = Paths.get("ReadMe/Licence.txt");
		
		List<String> lines = Files.readAllLines(licencePath);
		for (String string : lines) {
			licence += string + "\n";
		}
		
		return licence;
	}

}
