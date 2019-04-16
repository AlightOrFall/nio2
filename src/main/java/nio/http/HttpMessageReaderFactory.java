package nio.http;

import nio.common.IMessageReader;
import nio.common.IMessageReaderFactory;

/**
 * Created by jj
 */
public class HttpMessageReaderFactory implements IMessageReaderFactory {

	public HttpMessageReaderFactory() {
	}

	public IMessageReader createMessageReader() {
		return new HttpMessageReader();
	}

	
}
