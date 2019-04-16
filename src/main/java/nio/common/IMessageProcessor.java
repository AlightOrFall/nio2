package nio.common;

/**
 * Created by jj
 */
public interface IMessageProcessor {

	public void process(Message message, WriteProxy writeProxy);

}
