import java.util.Scanner;
public class Area_of_circle {
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Radius Of Circle:");
        int radius=sc.nextInt();
        System.out.printf("Area Of Circle is : %.2f%n",(Math.PI*radius*radius));
        sc.close();
    }
}