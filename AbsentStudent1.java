import java.util.Scanner;

class ClassRoom {
	String className, teacherName;
	int numStudent;
	int learningDay;
	int[] absentday;
	
	ClassRoom(String className,String teacherName,int numStudent,int learningDay){
		this.className = className;
		this.teacherName = teacherName;
		this.numStudent = numStudent;
		this.learningDay = learningDay;
		absentday = new int[numStudent];
		
	}
	
	void absent(int number) {
		if(number>=1 && number<=numStudent) {
			absentday[number-1]++;
		}else {
			System.out.println("Error!!! Student ID was not found.");
		}
	}
	
	int getAbsentDay(int number) {
		if(number>=1 && number<=numStudent) {
			return absentday[number-1];
		}
		return -1;
	}
	
	void print() {
		System.out.println("CLASS:"+className);
		System.out.println("BY T."+teacherName);
		for(int i = 0;i<absentday.length;i++) {
			System.out.println("Student ID#"+(i+1)+":"+(learningDay-absentday[i])+" school days.");
		}
	}
}

public class AbsentStudent1 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String className, teacherName;
		int totalStudyDay, numStudent, learningDay;
		className = in.next();
		teacherName = in.next();
		numStudent = in.nextInt();
		learningDay = in.nextInt();
		ClassRoom room = new ClassRoom(className, teacherName, numStudent, learningDay);
		for (int i = 0; i < learningDay; i++) {
			int numAbsent = in.nextInt();
			if (numAbsent > 0) {
				int id;
				for (int j = 0; j < numAbsent; j++) {
					id = in.nextInt();
					room.absent(id);
					if (room.getAbsentDay(id) > -1)
						System.out.println(id + " missed " + room.getAbsentDay(id) + " school days");
				}
			}
		}
	}
}