import java.util.Scanner;
import java.util.Arrays;

public class WordCountOne {
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
		
		boolean printed = false;
		for(int i = 0;i<count.length;i++) {
			if(count[i] == 1) {
				printed = true;
				System.out.print(strs[i]+" ");
			}
		}
		if(!printed)
			System.out.println("ALL WORDS ARE DUPLICATED");
		else {
			System.out.println();
		}
		
	}
}