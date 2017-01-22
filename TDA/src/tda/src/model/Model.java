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

public class Model extends Observable {
	/**
	 * <pre>
	 *           1..1     1..1
	 * Model ------------------------> Parser
	 *           model        &gt;       parser
	 * </pre>
	 */
	private Parser parser;

	public Model() {
		super();
		// initialize program:
		parser = new StAXParser();

	}

	public void setParser(Parser value) {
		this.parser = value;
	}

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

	public Set<TestRun> getTestRun() {
		if (this.testRun == null) {
			this.testRun = new HashSet<TestRun>();
		}
		return this.testRun;
	}

	public void parseFile(String xmlPath) {
		try {
			parser.parse(xmlPath);
		} catch (WrongXMLAttributeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
