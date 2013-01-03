package org.gvlabs.utils.soap;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SoapClientTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInvokeOperation() {
		String endpoint = "http://www.webservicex.net/ConvertWeight.asmx";
		String operation = "http://www.webserviceX.NET/ConvertWeight";
		String input = "<web:ConvertWeight xmlns:web=\"http://www.webserviceX.NET/\">"
				+ "<web:Weight>7</web:Weight>"
				+ "<web:FromUnit>Grains</web:FromUnit>"
				+ "<web:ToUnit>Grams</web:ToUnit>" + "</web:ConvertWeight>";

		SoapClient soapClient = new SoapClient(endpoint);

		String response = null;
		try {
			response = soapClient.invokeOperation(operation, input);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><ConvertWeightResponse xmlns=\"http://www.webserviceX.NET/\"><ConvertWeightResult>0.4536</ConvertWeightResult></ConvertWeightResponse></soap:Body></soap:Envelope>", response);
		
	}
	
	@Test
	public void testInvokeOperation11() {
		String endpoint = "http://www.webservicex.net/ConvertWeight.asmx";
		String operation = "http://www.webserviceX.NET/ConvertWeight";
		String input = "<web:ConvertWeight xmlns:web=\"http://www.webserviceX.NET/\">"
				+ "<web:Weight>7</web:Weight>"
				+ "<web:FromUnit>Grains</web:FromUnit>"
				+ "<web:ToUnit>Grams</web:ToUnit>" + "</web:ConvertWeight>";

		SoapClient soapClient = new Soap11Client(endpoint);

		String response = null;
		try {
			response = soapClient.invokeOperation(operation, input);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><ConvertWeightResponse xmlns=\"http://www.webserviceX.NET/\"><ConvertWeightResult>0.4536</ConvertWeightResult></ConvertWeightResponse></soap:Body></soap:Envelope>", response);
		
	}
	
	@Test
	public void testInvokeOperation12() {
		String endpoint = "http://www.webservicex.net/ConvertWeight.asmx";
		String operation = "http://www.webserviceX.NET/ConvertWeight";
		String input = "<web:ConvertWeight xmlns:web=\"http://www.webserviceX.NET/\">"
				+ "<web:Weight>7</web:Weight>"
				+ "<web:FromUnit>Grains</web:FromUnit>"
				+ "<web:ToUnit>Grams</web:ToUnit>" + "</web:ConvertWeight>";

		SoapClient soapClient = new Soap12Client(endpoint);

		String response = null;
		try {
			response = soapClient.invokeOperation(operation, input);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><ConvertWeightResponse xmlns=\"http://www.webserviceX.NET/\"><ConvertWeightResult>0.4536</ConvertWeightResult></ConvertWeightResponse></soap:Body></soap:Envelope>", response);
		
	}

}
