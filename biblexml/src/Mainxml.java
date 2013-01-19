import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

public class Mainxml {

static Connection connection = null;
static Statement statement;
	
  public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, SQLException, ClassNotFoundException  {

	  String sMakeTable = "CREATE TABLE entext (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, bible INTEGER, chapter INTEGER, poem INTEGER, poemtext TEXT)";
	  Class.forName("org.sqlite.JDBC");
	  connection = DriverManager.getConnection("jdbc:sqlite:enbible.db");
	  connection.setAutoCommit(false);
      statement = connection.createStatement();
      statement.executeUpdate(sMakeTable);
	
    exportbiblesqlite();

  }

//osis xml to sqlite bible
private static void exportbiblesqlite() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, SQLException {
	// TODO Auto-generated method stub
	DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    domFactory.setNamespaceAware(true); // never forget this!
    DocumentBuilder builder = domFactory.newDocumentBuilder();
    
    Document doc = builder.parse("./xmls/a1.xml");
    XPathFactory factory = XPathFactory.newInstance();
    XPath xpath = factory.newXPath();
    
    for (int i = 1; i < 67; i++) {
        
    	String forxpath = "//div["+i+"]/chapter/verse";
    	
    	XPathExpression expr = xpath.compile(forxpath);
    	
    	Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        
        String[] bookglav = null;
        
        for (int iglav = 0; iglav < nodes.getLength(); iglav++) {
        	String str = nodes.item(iglav).getAttributes().item(0).getTextContent();
        	bookglav = str.split("\\.");

        	String inserttobase = "insert into entext(bible, chapter, poem, poemtext) values(" + i + "," + bookglav[1] + "," + bookglav[2] + ",\""  + nodes.item(iglav).getTextContent() + "\");"; 
        	statement.execute(inserttobase);
        	
            System.out.println(i + "/" + bookglav[1] + "/" + bookglav[2] + "/"  + nodes.item(iglav).getTextContent()); 
        }
        
        connection.commit();
    	
    }
    
}

//вывод конкретной главы
private static void bibleglav() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
	// TODO Auto-generated method stub
	DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    domFactory.setNamespaceAware(true); // never forget this!
    DocumentBuilder builder = domFactory.newDocumentBuilder();
    
    Document doc = builder.parse("./xmls/rus.xml");
    
    XPathFactory factory = XPathFactory.newInstance();
    XPath xpath = factory.newXPath();

    //Ниже путь: книга 43 (Евангелие от Иоанна) / глава 1 / все стихи главы
    XPathExpression expr = xpath.compile("//div[43]/chapter[1]/verse/text()");
    
    //Ниже путь: вся книга 43 (Евангелие от Иоанна)
    //XPathExpression expr = xpath.compile("//div[43]/chapter/verse/text()");

    Object result = expr.evaluate(doc, XPathConstants.NODESET);
    NodeList nodes = (NodeList) result;
    
    for (int i = 0; i < nodes.getLength(); i++) {
        System.out.println((i+1) + ". " + nodes.item(i).getNodeValue()); 
    }

}
  

}
