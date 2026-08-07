
import java.util.Scanner;
public class volume_of_cylinder {
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Radius Of Cylinder:");
        Double radius=sc.nextDouble();
        System.out.println("Enter Height Of Cylinder :");
        Double Height=sc.nextDouble();
        System.out.println(String.format("Volume Of Cylinder is : %.2f",(Math.PI*radius*radius*Height)));
        //or we can use
        // System.out.printf("Volume Of Cylinder is : %.2f",(Math.PI*radius*radius*Height));
        sc.close();
    }
}