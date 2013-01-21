package in.dobro;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Superxml {
	
	static Connection connection;
	static Statement statement;
	static String lang;
	
	public Superxml(String inlang) throws ClassNotFoundException, SQLException, XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		
		  lang = inlang;
		
		  Class.forName("org.sqlite.JDBC");
		  
		  connection = DriverManager.getConnection("jdbc:sqlite:"+lang+"_bible.db");
	      statement = connection.createStatement();
	      String sMakeTable1 = "CREATE TABLE "+lang+"text (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, bible INTEGER, chapter INTEGER, poem INTEGER, poemtext TEXT)";
	      statement.executeUpdate(sMakeTable1);
	      
	      String sMakeTable2 = "CREATE TABLE "+lang+"bible (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, biblename TEXT, chapters INTEGER)";
	      statement.executeUpdate(sMakeTable2);
	      
	      exportbiblesqlitenames();
	      
	      connection.setAutoCommit(false);
	      
	      exportbiblesqliteverses();
		
	}
	
	private void exportbiblesqlitenames() throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, SQLException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	    domFactory.setNamespaceAware(true); // never forget this!
	    DocumentBuilder builder = domFactory.newDocumentBuilder();
	    
	    Document doc = builder.parse("./xmls/"+lang+"_bible.xml");
	    XPathFactory factory = XPathFactory.newInstance();
	    XPath xpath = factory.newXPath();
	    
	    String forxpath = "//booknames/book";
    	
    	XPathExpression expr = xpath.compile(forxpath);
    	
    	Object result = expr.evaluate(doc, XPathConstants.NODESET);
    	
    	NodeList nodes = (NodeList) result;

	    for (int i = 0; i < nodes.getLength(); i++) {
	    	String biblename = nodes.item(i).getAttributes().item(2).getTextContent();
	    	String bookchapters = nodes.item(i).getAttributes().item(0).getTextContent();
        	System.out.println(biblename + " >> " + bookchapters + " глав");
        	String inserttobasenames = "insert into "+lang+"bible(biblename, chapters) values(\"" + biblename + "\"," + Integer.parseInt(bookchapters) + ");"; 
        	statement.execute(inserttobasenames);
	    }
	    
	}

	//biblexml new format to sqlite bible
	private static void exportbiblesqliteverses() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, SQLException {
		// TODO Auto-generated method stub
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	    domFactory.setNamespaceAware(true); // never forget this!
	    DocumentBuilder builder = domFactory.newDocumentBuilder();
	    
	    Document doc = builder.parse("./xmls/"+lang+"_bible.xml");
	    XPathFactory factory = XPathFactory.newInstance();
	    XPath xpath = factory.newXPath();
	    
	    for (int i = 1; i < 67; i++) {
	        
	    	String forxpath = "//booktext["+i+"]/chapter/verse";
	    	
	    	XPathExpression expr = xpath.compile(forxpath);
	    	
	    	Object result = expr.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodes = (NodeList) result;
	        
	        String[] bookglav = null;
	        
	        for (int iglav = 0; iglav < nodes.getLength(); iglav++) {
	        	String str = nodes.item(iglav).getAttributes().item(0).getTextContent();
	        	bookglav = str.split("\\.");

	        	String inserttobase = "insert into "+lang+"text(bible, chapter, poem, poemtext) values(" + i + "," + bookglav[1] + "," + bookglav[2] + ",\""  + nodes.item(iglav).getTextContent() + "\");"; 
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
