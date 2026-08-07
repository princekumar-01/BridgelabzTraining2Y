import java.util.Scanner;
public class Add {
    public static void main(String[] args ){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter First No.");
        int a=sc.nextInt();
        System.out.println("Enter Second No.");
        int b=sc.nextInt();
        System.out.println("Sum is :"+(a+b));
        sc.close();

    }
}