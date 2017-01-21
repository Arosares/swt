package tda.src.logic;

import tda.src.exceptions.WrongXMLAttributeException;

public interface Parser {
   
   /**
    *  Parses a given XML File in our Data-Structure.
 * @throws WrongXMLAttributeException if the XML is not valid.
    */
	public void parse(String path) throws WrongXMLAttributeException;

}
