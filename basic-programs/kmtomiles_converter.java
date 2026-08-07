
import java.util.Scanner;
public class kmtomiles_converter {
    public static void main(String[] args) {

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter distance in kilometers: ");
            double km = sc.nextDouble();

            double miles = km * 0.621371;

            System.out.printf("Distance in miles: %.3f%n",miles);
            sc.close();
        }
    }
    //Note- \n = Print new line (Platform Dependent)
    // %n = Same as \n but platform independent