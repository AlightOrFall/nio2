package nio.test;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class MyIoHandler extends IoHandlerAdapter {

	// 这里我们使用的SLF4J作为日志门面，至于为什么在后面说明。
	private final static Logger log = LoggerFactory.getLogger(MyIoHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		/*String str = message.toString();
		log.info("The message received is [" + str + "]");
		if (str.endsWith("quit")) {
			session.close(true);
			return;
		}*/
		SmsObject sms = (SmsObject) message;
		log.info("The message received is [" + sms.getMessage() + "]"); 
	}

}
