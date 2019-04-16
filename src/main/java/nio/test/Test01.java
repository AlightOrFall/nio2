package nio.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class Test01 {

	@Test
	public void test01() {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream("E:/file/test/hello.txt"));
			byte[] buf = new byte[1024];
			int bytesRead = in.read(buf);
			while (bytesRead != -1) {
				for (int i = 0; i < bytesRead; i++)
					System.out.print((char) buf[i]);
				bytesRead = in.read(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test02() {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("E:/file/test/hello.txt", "rw");
			FileChannel fileChannel = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(1024);
			int bytesRead = fileChannel.read(buf);
			System.out.println(bytesRead);
			while (bytesRead != -1) {
				buf.flip();
				while (buf.hasRemaining()) {
					System.out.print((char) buf.get());
				}
				buf.compact();
				bytesRead = fileChannel.read(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (aFile != null) {
					aFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void client() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		SocketChannel socketChannel = null;
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(new InetSocketAddress("10.10.195.115", 8080));
			if (socketChannel.finishConnect()) {
				int i = 0;
				while (true) {
					TimeUnit.SECONDS.sleep(1);
					String info = "I'm " + i++ + "-th information from client";
					buffer.clear();
					buffer.put(info.getBytes());
					buffer.flip();
					while (buffer.hasRemaining()) {
						System.out.println(buffer);
						socketChannel.write(buffer);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socketChannel != null) {
					socketChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void method4() {
		RandomAccessFile aFile = null;
		FileChannel fc = null;
		try {
			aFile = new RandomAccessFile("E:/download/51CTO下载-响应式web设计.pdf", "rw");
			fc = aFile.getChannel();
			long timeBegin = System.currentTimeMillis();
			ByteBuffer buff = ByteBuffer.allocate((int) aFile.length());
			buff.clear();
			fc.read(buff);
//			 System.out.println((char)buff.get((int)(aFile.length()/2-1)));
			// System.out.println((char)buff.get((int)(aFile.length()/2)));
//			 System.out.println((char)buff.get((int)(aFile.length()/2)+1));
			long timeEnd = System.currentTimeMillis();
			System.out.println("Read time: " + (timeEnd - timeBegin) + "ms");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (aFile != null) {
					aFile.close();
				}
				if (fc != null) {
					fc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public  void method3() {
		RandomAccessFile aFile = null;
		FileChannel fc = null;
		try {
			aFile = new RandomAccessFile("E:/download/51CTO下载-响应式web设计.pdf", "rw");
			fc = aFile.getChannel();
			long timeBegin = System.currentTimeMillis();
			MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, aFile.length());
//			 System.out.println((char)mbb.get((int)(aFile.length()/2-1)));
			// System.out.println((char)mbb.get((int)(aFile.length()/2)));
//			 System.out.println((char)mbb.get((int)(aFile.length()/2)+1));
			long timeEnd = System.currentTimeMillis();
			System.out.println("Read time: " + (timeEnd - timeBegin) + "ms");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (aFile != null) {
					aFile.close();
				}
				if (fc != null) {
					fc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
