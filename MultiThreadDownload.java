import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MultiThreadDownload {
    public static void main(String[] args) throws Exception {
       //1: declare file name and download address
       String fileName = "test.mp4";
       String urlStr = "http://192.168.8.250";
       //2: declare the Url
       URL url = new URL(urlStr+"/"+fileName);
       //3: get the connection
       HttpURLConnection con =
           (HttpURLConnection) url.openConnection();
       //4: set the request mode
       con.setRequestMethod("GET");
       //5: gets the request header, that is, the length of the file
       int length = con.getContentLength();//Gets the length of the downloaded file to calculate how much data each thread should download.
       //6: create a file of the same size in the specified directory
       RandomAccessFile file = new RandomAccessFile("d:/"+fileName, "rw");//Create a file of the same size.
       //7: set the file size and hold the space
       file.setLength(length);//Set the file size.
 
       file.close();
       //8: define the number of threads
       int size = 3;
       //9: calculate how many bytes of data each thread should download, preferably if it is exactly divisible, otherwise add 1
       int block = length/size==0?length/size:length/size+1;//Calculates the amount of data that each thread should download.

       System.err.println(" Each thread should download: "+block);
       //10: run three threads and calculate which byte to start and which byte to end
       for(int i=0;i<size;i++){
           int start = i*block;
           int end = start+(block-1);//Calculates the start and end bytes of each thread.
 
         System.err.println(i+"="+start+","+end);
           new MyDownThread(fileName, start, end,url).start();
       }
    }
    static class MyDownThread extends Thread{
       //Define file name
       private String fileName;
       //Define where to start downloading
       private int start;
       //Defines which bytes to download to
       private int end;
       private URL url;
       public MyDownThread(String fileName,int start,int end,URL url){
           this.fileName=fileName;
           this.start=start;
           this.end=end;
           this.url=url;
       }
       @Override
       public void run() {
           try{
              //11: start downloading
              HttpURLConnection con =
                     (HttpURLConnection) url.openConnection();
              con.setRequestMethod("GET");
              //12: sets the request header for segmented downloads
              con.setRequestProperty("Range","bytes="+start+"-"+end);//Sets the file blocks to be read from the server.
 
              //13: start downloading, need to judge 206
              if(con.getResponseCode()==206){//If the access is successful, the status code returned is 206.
                  InputStream in = con.getInputStream();
                  //14: declare a random write file object. Note that RWD means writing data to a file immediately without using a cache
                  RandomAccessFile out = new RandomAccessFile("d:/"+fileName,"rwd");
                  out.seek(start);//Set to start writing data from somewhere in the file.
                  byte[] b=new byte[1024];
                  int len = 0;
                  while((len=in.read(b))!=-1){
                     out.write(b,0,len);
                  }
                  out.close();
                  in.close();
              }
              System.err.println(this.getName()+" completes ");
           }catch(Exception e){
              throw new RuntimeException(e);
           }
       }
    }
}
