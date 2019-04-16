package nio.example;

import java.io.IOException;

import nio.common.IMessageProcessor;
import nio.common.Message;
import nio.common.Server;
import nio.common.WriteProxy;
import nio.http.HttpMessageReaderFactory;

public class Main {

	public static void main(String[] args) throws IOException {

		String httpResponse = "HTTP/1.1 200 OK\r\n" + "Content-Length: 38\r\n" + "Content-Type: text/html\r\n" + "\r\n"
				+ "<html><body>Hello World!</body></html>";

		final byte[] httpResponseBytes = httpResponse.getBytes("UTF-8");

		IMessageProcessor messageProcessor = new IMessageProcessor() {
			public void process(Message request, WriteProxy writeProxy) {
				System.out.println("Message Received from socket: " + request.socketId);

				Message response = writeProxy.getMessage();
				response.socketId = request.socketId;
				response.writeToMessage(httpResponseBytes);

				writeProxy.enqueue(response);
			}
		};

		Server server = new Server(9999, new HttpMessageReaderFactory(), messageProcessor);

		server.start();

	}
}
