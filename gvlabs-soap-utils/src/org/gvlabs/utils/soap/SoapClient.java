package org.gvlabs.utils.soap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import sun.misc.BASE64Encoder;

/**
 * Simple SOAP Client
 * 
 * @author Thiago Galbiatti Vespa
 * @version 1.2
 */
public class SoapClient {

	private String endpoint;

	/**
	 * Constructor with an endpoint
	 * 
	 * @param endpoint
	 *            soap endpoint
	 */
	public SoapClient(String endpoint) {
		super();
		this.endpoint = endpoint;
		
	}

	/**
	 * Invoke a SOAP Operation
	 * 
	 * @param operation
	 *            operation name (Header: SOAPAction)
	 * @param input
	 *            request payload
	 * @return response payload
	 * @throws SOAPException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public String invokeOperation(String operation, String input)
			throws SOAPException, ParserConfigurationException, SAXException,
			IOException, TransformerException {
		String response = null;
		SOAPConnection connection = null;
		try {
			// Connection
			SOAPConnectionFactory soaConnFactory = getSoaConnFactory();
			connection = soaConnFactory.createConnection();

			// Message
			MessageFactory messageFactory = getMessageFactory();
			SOAPMessage message = messageFactory.createMessage();

			// Header
			MimeHeaders headers = message.getMimeHeaders();
			headers.addHeader("SOAPAction", operation);
			
			SOAPHeader soapHeader = message.getSOAPHeader();
			
			
			SOAPElement security = soapHeader.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
			SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
			
			/* Nonce string encoded */
			SOAPElement nonce = usernameToken.addChildElement("Nonce", "wsse");
			nonce.setAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
			String nonceString = String.valueOf(this.hashCode());
		    BASE64Encoder encoder = new BASE64Encoder();
		    nonceString = encoder.encode(nonceString.getBytes());
			nonce.addTextNode(nonceString);
			
			/* Created date */
			SOAPElement created = usernameToken.addChildElement("Created", "wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
			String createdTimestamp = localToGmtTimestamp();
			created.addTextNode(createdTimestamp);
			
			/* Username */
			SOAPElement username = usernameToken.addChildElement("Username", "wsse");
			username.addTextNode("username");
			
			/* Password */
			SOAPElement password = usernameToken.addChildElement("Password", "wsse");
			password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
			password.addTextNode("textpassword");
			
			// Body
			SOAPBody body = message.getSOAPBody(); //
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);

			DocumentBuilder parser = dbf.newDocumentBuilder();
			if (input != null) {
				StringBuffer inputData = new StringBuffer(input);
				ByteArrayInputStream bis = new ByteArrayInputStream(inputData
						.toString().getBytes("UTF-8"));
				Document xmlDoc = parser.parse(bis);

				xmlDoc.setStrictErrorChecking(false);
				body.addDocument(xmlDoc);
			}

			message.saveChanges();
			
			// Call a operation
			SOAPMessage soapResponse = connection.call(message, new URL(
					endpoint));

			if (soapResponse != null) {
				// Get response
				TransformerFactory tff = TransformerFactory.newInstance();
				Transformer tf = tff.newTransformer();

				Source sc = soapResponse.getSOAPPart().getContent();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				StreamResult result = new StreamResult(baos);
				tf.transform(sc, result);
				response = baos.toString("UTF-8");
			}

		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return response;
	}
	
	/* Retorna data atual */
	private String localToGmtTimestamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		return format.format(Calendar.getInstance().getTime());
	}
	
	protected MessageFactory getMessageFactory() throws SOAPException {
		return MessageFactory.newInstance();
	}

	protected SOAPConnectionFactory getSoaConnFactory() throws SOAPException {
		return SOAPConnectionFactory.newInstance();
	}
}
