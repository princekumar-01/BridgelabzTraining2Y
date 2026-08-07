public class PrimeCheck {
    /*
     * PSEUDOCODE
     * INPUT N
     * IF N <= 1
     *     PRINT "Not Prime"
     *     STOP
     * END IF
     *
     * FOR divisor <- 2 TO floor(sqrt(N))
     *     IF N MOD divisor = 0
     *         PRINT "Not Prime"
     *         STOP
     *     END IF
     * END FOR
     * PRINT "Prime"
     */
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }

        for (int divisor = 2; divisor <= number / divisor; divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int number = args.length > 0 ? Integer.parseInt(args[0]) : 61;
        System.out.println(isPrime(number) ? "Prime" : "Not Prime");
    }
}
