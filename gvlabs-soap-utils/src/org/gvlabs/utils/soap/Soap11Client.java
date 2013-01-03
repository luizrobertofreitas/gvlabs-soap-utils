package org.gvlabs.utils.soap;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;

/**
 * Client for SOAP Version 1.1
 * 
 * @author Thiago Galbiatti Vespa
 * @version 1.2
 */
public class Soap11Client extends SoapClient {

	/**
	 * Constructor with an endpoint
	 * 
	 * @param endpoint
	 *            soap endpoint
	 */
	public Soap11Client(String endpoint) {
		super(endpoint);
	}

	@Override
	protected MessageFactory getMessageFactory() throws SOAPException {
		return MessageFactory
				.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
	}

}
