import java.util.Scanner;
import java.util.ArrayList;

class Data{
	Data(String word){
		this.word = word;
		this.c = 1;
	}
	String word;
	int c;
	
	void print() {
		System.out.println(word+" "+c);
	}
}

public class WordCountOneOOP {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		ArrayList<Data> datas = new ArrayList<Data>();
		for(int i = 0;i<n;i++) {
			String str = sc.next();
			boolean same = false;
			for(Data d:datas) {
				
				
				if(d != null && d.word.equals(str)) {
					same = true;
					d.c++;
					break;
				}
				
				
			}
			if(!same) {
				datas.add(new Data(str));
			}
			
		}
		
		
		boolean dub = true;
		for(Data d : datas) {
			if(d.c == 1) {
				dub = false;
				System.out.print(d.word+" ");
			}
		}
		if(dub) {
			System.out.println("ALL WORDS ARE DUPLICATED");
		}else {
			System.out.println();
		}
		
		
	}
}
