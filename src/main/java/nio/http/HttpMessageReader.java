package nio.http;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import nio.common.IMessageReader;
import nio.common.Message;
import nio.common.MessageBuffer;
import nio.common.Socket;

/**
 * Created by jj
 */
public class HttpMessageReader implements IMessageReader {

	private MessageBuffer messageBuffer = null;

	private List<Message> completeMessages = new ArrayList<Message>();
	private Message nextMessage = null;

	public HttpMessageReader() {
	}

	public void init(MessageBuffer readMessageBuffer) {
		this.messageBuffer = readMessageBuffer;
		this.nextMessage = messageBuffer.getMessage();
		this.nextMessage.metaData = new HttpHeaders();
	}

	public void read(Socket socket, ByteBuffer byteBuffer) throws IOException {
		int bytesRead = socket.read(byteBuffer);
		byteBuffer.flip();

		if (byteBuffer.remaining() == 0) {
			byteBuffer.clear();
			return;
		}

		this.nextMessage.writeToMessage(byteBuffer);

		int endIndex = HttpUtil.parseHttpRequest(this.nextMessage.sharedArray, this.nextMessage.offset,
				this.nextMessage.offset + this.nextMessage.length, (HttpHeaders) this.nextMessage.metaData);
		if (endIndex != -1) {
			Message message = this.messageBuffer.getMessage();
			message.metaData = new HttpHeaders();

			message.writePartialMessageToMessage(nextMessage, endIndex);

			completeMessages.add(nextMessage);
			nextMessage = message;
		}
		byteBuffer.clear();
	}

	public List<Message> getMessages() {
		return this.completeMessages;
	}

}
