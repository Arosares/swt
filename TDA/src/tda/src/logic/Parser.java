package tda.src.logic;

import tda.src.exceptions.WrongXMLAttributeException;

public interface Parser {
   
   /**
    *  Parses a given XML File in our Data-Structure.
    *  Mainly to {@code UnitTest.java} format
 * @throws WrongXMLAttributeException 
    */
	public void parse(String path) throws WrongXMLAttributeException;

}
