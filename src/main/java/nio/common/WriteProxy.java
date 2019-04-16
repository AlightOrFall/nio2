package nio.common;

import java.util.Queue;

/**
 * Created by jj
 */
public class WriteProxy {

	private MessageBuffer messageBuffer = null;
	private Queue writeQueue = null;

	public WriteProxy(MessageBuffer messageBuffer, Queue writeQueue) {
		this.messageBuffer = messageBuffer;
		this.writeQueue = writeQueue;
	}

	public Message getMessage() {
		return this.messageBuffer.getMessage();
	}

	public boolean enqueue(Message message) {
		return this.writeQueue.offer(message);
	}

}
