package nio.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.util.ReferenceCountingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MyServer {
	public static void main(String[] args) throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.setHandler(new MyIoHandler());
		/*
		 * acceptor.getFilterChain().addLast("codec", new
		 * ProtocolCodecFilter(new
		 * TextLineCodecFactory(Charset.forName("UTF-8"),
		 * LineDelimiter.WINDOWS.getValue(),
		 * LineDelimiter.WINDOWS.getValue())));
		 */
		
		 acceptor.getFilterChain().addLast("codec", new
		  ProtocolCodecFilter(new
		  CmccSipcCodecFactory(Charset.forName("UTF-8"))));
		  acceptor.getFilterChain().addLast("myIoFilter", new
		  ReferenceCountingFilter(new MyIoFilter()));
		 
		acceptor.bind(new InetSocketAddress(9123));

	}
}
