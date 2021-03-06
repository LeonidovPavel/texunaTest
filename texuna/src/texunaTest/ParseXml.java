package texunaTest;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Pavel
 */

public class ParseXml {

	public String number_width = "";
	public String date_width = "";
	public String name_width = "";
	public String height;
	public String width;

	public void parse() {
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse("src/texunaTest/settings.xml");
			Node root = document.getDocumentElement();
			NodeList books = root.getChildNodes();
			Node page = books.item(1);
			Node col = books.item(3);
			NodeList pages = page.getChildNodes();
			width = pages.item(1).getTextContent();
			height = pages.item(3).getTextContent();
			NodeList columns = col.getChildNodes();
			int k = 0;
			for (int i = 0; i < columns.getLength(); i++) {
				Node columnschild = columns.item(i);
				if (columnschild.getNodeType() != Node.TEXT_NODE) {
					NodeList columnsProps = columnschild.getChildNodes();
					for (int j = 0; j < columnsProps.getLength(); j++) {
						Node columnProp = columnsProps.item(j);
						if (columnProp.getNodeType() != Node.TEXT_NODE) {
							if (k == 0) {
								if (columnProp.getNodeName().equals("width")) {
									number_width = columnProp.getChildNodes().item(0).getTextContent();
									k++;
								}
							} else {
								if (k == 1) {
									if (columnProp.getNodeName().equals("width")) {
										date_width = columnProp.getChildNodes().item(0).getTextContent();
										k++;
									}
								} else {
									if (k == 2) {
										if (columnProp.getNodeName().equals("width")) {
											name_width = columnProp.getChildNodes().item(0).getTextContent();
											k++;
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace(System.out);
		} catch (SAXException ex) {
			ex.printStackTrace(System.out);
		} catch (IOException ex) {
			ex.printStackTrace(System.out);
		}
	}
}