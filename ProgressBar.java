import java.util.Scanner;


import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


public class ProgressBar {
	private static String urlAddress = "http://212.183.159.230/1GB.zip";
	private static int fileSize;
	private static int threadCount;
	private static int blockSize;

	public static void main(String[] args) {

		try {
			URL url = new URL(urlAddress);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			System.out.println("total file size : " + conn.getContentLength());
			fileSize = conn.getContentLength();

			System.out.println("Each file size: " + blockSize);

			RandomAccessFile file = new RandomAccessFile("d:/1GB.zip", "rw");
			file.setLength(fileSize);
			file.close();

			threadCount = 10;
			blockSize = fileSize / threadCount == 0 ? fileSize / threadCount : fileSize / threadCount + 1;
			System.out.println("Each thread should download: " + blockSize);
			System.out.println("Each thread should download in mb: " + blockSize / 1024 / 1024 + "MB");

			for (int i = 0; i < threadCount; i++) {
				int start = i * blockSize;
				int end = start + (blockSize - 1);
				System.out.println("Thread i+ : = " + start + "," + end);
				new DownloadThread("1GB.zip", start, end,url).start();
			}
			
			byte[] b=new byte[1024];

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static class DownloadThread extends Thread {
		private String fileName;
		private int start;
		private int end;
		private URL url;

		public DownloadThread(String fileName, int start, int end, URL url) {
			this.fileName = fileName;
			this.start = start;
			this.end = end;
			this.url = url;
		}
		
		public static void progress(int start, int end, int now,String name) {
			int size = end - start;

			int blocks = size / 10;
			System.out.println();
			System.out.print(name + " progress : ");
			int count = 0;
			for (int i = 1; i <= 10; i++) {
				
				if(now == end) {
					System.out.print("#");
					count=10;
				}
				else if (now > i * blocks && now != end) {
					System.out.print("#");
					count++;
				} else
					System.out.print("-");

			}
			System.out.println(" ("+count*10+" %)");
		}

		public void run() {
			try {
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Range","bytes="+start+"-"+end);
				
				if(con.getResponseCode() == 206) {
					InputStream in = con.getInputStream();
					RandomAccessFile out = new RandomAccessFile("d:/"+fileName,"rwd");
					out.seek(start);
					byte[] b=new byte[1024];
	                  int len = 0;
	                  int i = 1;
	                  while((len=in.read(b))!=-1){
	                     out.write(b,0,len);
	                     progress(start,end,1024*i,this.getName());
	                     i++;
	                  }
	                  out.close();
	                  in.close();
	                  System.err.println();
	                  System.err.println(this.getName()+" completes ");
	                  Thread.sleep(10);
				}
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
