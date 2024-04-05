package Configuration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.Reporter;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Common{
	public WebDriver driver;
	public Common(WebDriver driver) {
		this.driver = driver;
	}

	public void log(String msg) {
		Reporter.log("Step:- " +msg+ "<br>");
		System.out.println("Step:- " +msg);
	}

	private static void writeXMLToFile(Document document, String fileName) throws IOException {
		try (OutputStream os = new FileOutputStream(fileName)) {
			javax.xml.transform.TransformerFactory.newInstance()
					.newTransformer().transform(new javax.xml.transform.dom.DOMSource(document),
							new javax.xml.transform.stream.StreamResult(os));
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException(e);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
	}

	public static void Update_XML_File(){
		try {
			// Load XML file
			File xmlFile = new File("input.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFile);

			// Find the element by tag name
			NodeList nodeList = doc.getElementsByTagName("OrigAssigneeFlag");

			// Iterate over all matching elements
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					// Edit data of the element
					String newData = "new value"; // Your new value
					element.setTextContent(newData);
				}
			}

			// Write the updated XML content back to the file
			writeXMLToFile(doc, "output.xml");

			System.out.println("XML file updated successfully.");

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void verify_XML_Sent_response(){

		// Set base URI of the API
		RestAssured.baseURI = "Parth API";

		// Read XML file
		File xmlFile = new File("path_to_your_xml_file.xml");

		// Make POST request with XML file as payload
		Response response = RestAssured.given()
				.contentType("application/xml")
				.body(xmlFile)
				.post();

		// Verify response code is 200
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Expected status code 200 but found " + statusCode);

		// You can also verify other aspects of the response if needed
		// For example, response body, headers, etc.
		// String responseBody = response.getBody().asString();
		// System.out.println("Response Body is: " + responseBody);

	}
}