package nio.test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Main {

	public static void main(String[] args) {
		method3();
		System.out.println("=============");
		method4();
	}

	public static void method4() {
		RandomAccessFile aFile = null;
		FileChannel fc = null;
		try {
			aFile = new RandomAccessFile("E:/download/51CTO下载-响应式web设计.pdf", "rw");
			fc = aFile.getChannel();
			long timeBegin = System.currentTimeMillis();
			ByteBuffer buff = ByteBuffer.allocate((int) aFile.length());
			buff.clear();
			fc.read(buff);
			// System.out.println((char)buff.get((int)(aFile.length()/2-1)));
			// System.out.println((char)buff.get((int)(aFile.length()/2)));
			// System.out.println((char)buff.get((int)(aFile.length()/2)+1));
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

	public static void method3() {
		RandomAccessFile aFile = null;
		FileChannel fc = null;
		try {
			aFile = new RandomAccessFile("E:/download/51CTO下载-响应式web设计.pdf", "rw");
			fc = aFile.getChannel();
			long timeBegin = System.currentTimeMillis();
			MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, aFile.length());
			// System.out.println((char)mbb.get((int)(aFile.length()/2-1)));
			// System.out.println((char)mbb.get((int)(aFile.length()/2)));
			// System.out.println((char)mbb.get((int)(aFile.length()/2)+1));
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
