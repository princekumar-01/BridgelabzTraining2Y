
import java.util.Scanner;
public class parimeter_of_rectangle {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Length :");
        double length=sc.nextDouble();

        System.out.println("Enter Width :");
        double width=sc.nextDouble();

        System.out.println("Parimeter of Recatangle is :"+ (2*(length + width)));   
        sc.close();//Sc object uses a system resource and should be closed otherwise system give us a warning Resource leak
    }
}
