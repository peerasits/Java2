import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;

public class Test {

	public static void main(String[] args) {
		RandomAccessFile file = null;
		try {
			String path = "C:\\Users\\Porome\\Desktop\\learning\\txt\\test.txt";
			file = new RandomAccessFile(new File(path), "rw");
			file.seek(4);
			byte[] bytes = new byte[2];
			file.read(bytes, 1, 1);
			
			System.out.println(file.getFilePointer());
			
			char[] c = {'Z','X'};
			for(int i = 0 ;i<2;i++)
				bytes[i]=(byte)c[i];
			
			file.write(bytes);
			System.out.println(file.getFilePointer());
			file.write(bytes);
			System.out.println(file.getFilePointer());
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (file != null)
					file.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
