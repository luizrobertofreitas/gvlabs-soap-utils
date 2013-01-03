package org.gvlabs.utils.soap;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;

/**
 * Fixed Message and Soap Connection Factory
 * com.sun.xml.internal.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl
 * com.sun.xml.internal.messaging.saaj.client.p2p.HttpSOAPConnectionFactory
 * 
 * @author Thiago Galbiatti Vespa
 * @version 1.1
 */
public class SoapClientFixedDefaultFactory extends SoapClient {

	/**
	 * Constructor with an endpoint
	 * @param endpoint soap endpoint 
	 */
	public SoapClientFixedDefaultFactory(String endpoint) {
		super(endpoint);
	}

	@Override
	public MessageFactory getMessageFactory() throws SOAPException {
		MessageFactory messageFactory = new com.sun.xml.internal.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl();
		return messageFactory;
	}

	public SOAPConnectionFactory getSoaConnFactory() throws SOAPException {
		SOAPConnectionFactory soaConnFactory = new com.sun.xml.internal.messaging.saaj.client.p2p.HttpSOAPConnectionFactory();
		return soaConnFactory;
	}
}
