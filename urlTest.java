import java.net.URLConnection;
import java.net.URL;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.io.*;

public class urlTest {
	
	static String address = "https://countdown.crossknight.com/static/amv.mkv";

	static class ThreadDownload implements Runnable {
		private int threadId;
		private long startIndex;
		private long endIndex;

		// The constructor receives three parameters to initialize local variables:
		// thread ID, start position and end position
		public ThreadDownload(int threadId, long startIndex, long endIndex) {
			this.threadId = threadId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}

		@Override
		public void run() {
			try {
				

			URLConnection url = new URL(address).openConnection();
			HttpURLConnection conn = (HttpURLConnection) url;
			
			System.out.println(
					"thread" + threadId + "Download start location" + startIndex + " End position:" + endIndex);

			// Set request header and request some resources to download
			conn.setRequestProperty("Range", "bytes:" + startIndex + "-" + endIndex);
			// Server response code 206 indicates that some resources are successfully
			// requested
			
				BufferedInputStream inputStream = new BufferedInputStream(conn.getInputStream());
				RandomAccessFile raf = new RandomAccessFile(new File("C:\\Users\\Porome\\Desktop\\learning\\amv.mkv"), "rw");
				// Find write start position
				raf.seek(startIndex);
				byte array[] = new byte[1024];
				int len = -1;
				while ((len = inputStream.read(array, 0, 1024)) >= 0) {
					raf.write(array, 0, len);
				}
				
			
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	static int threadCount = 5;

	public static void main(String[] args) throws Exception {
		URLConnection url = new URL(address).openConnection();
		HttpURLConnection conn = (HttpURLConnection) url;
		long length = conn.getContentLengthLong();
		System.out.println(length);
		System.out.println("Total file size:" + length / 1024 / 1024 + "MB");
		int code = conn.getResponseCode();
		System.out.println(code);
		if (code == 200) {
			long blockSize = length / threadCount;
			System.out.println(blockSize);
			long startIndex, endIndex;
			RandomAccessFile raf = new RandomAccessFile("amv.mkv", "rw");
			for (int threadId = 0; threadId < threadCount; threadId++) {
				Thread thread = null;
				System.out.println(threadId);
				startIndex = (blockSize * threadId) + 1;
				endIndex = (blockSize * (threadId + 1));
				if (threadId == 0)
					startIndex = 0;
				if (threadId == 4)
					endIndex = length;
				System.out.println("thread: " + threadId + " start:" + startIndex + " end:" + endIndex);
				thread  = new Thread(new ThreadDownload(threadId, startIndex, endIndex));
				thread.start();
			}
		}
	}
}
