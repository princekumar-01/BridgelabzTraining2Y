public class NumberDigitReversal {
    /*
     * PSEUDOCODE
     * INPUT N
     * sign <- 1
     * IF N < 0
     *     sign <- -1
     *     N <- absolute value of N
     * END IF
     * reversed <- 0
     * WHILE N > 0
     *     digit <- N MOD 10
     *     reversed <- reversed * 10 + digit
     *     N <- N DIV 10
     * END WHILE
     * OUTPUT sign * reversed
     */
    public static long reverseDigits(int number) {
        long remaining = Math.abs((long) number);
        long reversed = 0;

        while (remaining > 0) {
            long digit = remaining % 10;
            reversed = reversed * 10 + digit;
            remaining /= 10;
        }
        return number < 0 ? -reversed : reversed;
    }

    public static void main(String[] args) {
        int number = args.length > 0 ? Integer.parseInt(args[0]) : 806;
        System.out.println(reverseDigits(number));
    }
}
