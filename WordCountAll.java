import java.util.Scanner;
import java.util.Arrays;

public class WordCountAll {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int totalWords = sc.nextInt();
		String[] strs = new String[totalWords];
		int[] count = new int[totalWords];

		int index = 0;
		for (int i = 1; i <= totalWords; i++) {
			String str = sc.next();
			boolean found = false;
			for (int j = 0; j < strs.length; j++) {

				if (strs[j] != null && strs[j].equals(str)) {
					found = true;
					count[j]++;
					break;
				}

			}
			if (!found) {
				strs[index] = str;
				count[index++]++;
			}
		}
		
		
		for(int i = 0;i<count.length;i++) {
			if(count[i]>0) {
				System.out.println(strs[i]+" "+count[i]);
			}
		}
		
	}
}