package nio.test;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends IoHandlerAdapter {

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

//	private final String values;

	public ClientHandler() {
		
	}
	
	/*public ClientHandler(String values) {
		this.values = values;
	}*/

	@Override
	public void sessionOpened(IoSession session) {
		SmsObject sms = new SmsObject();
		sms.setSender("153080106");
		sms.setReceiver("183822679");
		sms.setMessage("你好！Hello World!");
		session.write(sms);
	}

}
