package nio.test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MyClient {

	public static void main(String[] args) {
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30000);
		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new CmccSipcCodecFactory(Charset.forName("UTF-8"))));
		connector.setHandler(new ClientHandler());
		connector.connect(new InetSocketAddress("localhost", 9123));
	}
}
