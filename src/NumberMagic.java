import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NumberMagic {
    static int number = 0;
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner((System.in));
        System.out.println("Enter an integer number");
        boolean flag;

        do {
            try {
                number = sc.nextInt();
                flag = false;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer number.");
                sc.next(); // Clear the invalid input
                flag = true;
            }
        } while (flag);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Callable<String> squareRoot = () -> {
            double  sqrt = Math.sqrt(number);
            return "Square Root of " + number + " is " + sqrt;
        };
        Callable<String> fibonacci = () -> {
            int frist = 0, second = 1, next = frist + second;
            for (int i = 2; i < number; i++) {
                frist = second;
                second = next;
                next = frist + second;
            }
            return "Fibonacci of " + number + " is " +next;
        };
        Callable<String> factorial = () -> {
            int i = 2, fact = 1;
            while (i <= number) {
                fact *=i++;
            }
            return "Factorial of " + number + " is " + fact;
        };
        Callable<String> binaryNumber    = () -> number + "in binary " + Integer.toBinaryString(number);
        List<Callable<String>> tasks = List.of(squareRoot,fibonacci,factorial,binaryNumber);
        executorService.invokeAll(tasks).forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        });
        executorService.shutdown();
    }
}
