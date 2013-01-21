package in.dobro;

import java.io.IOException;
import java.sql.SQLException;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

public class Mainxml extends Superxml {
	
  public Mainxml(String inlang) throws ClassNotFoundException, SQLException, XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		super(inlang);
  }

public static void main(String[] args) throws ClassNotFoundException, XPathExpressionException, SQLException, ParserConfigurationException, SAXException, IOException   {

	new Mainxml("ru");

}
  

}
