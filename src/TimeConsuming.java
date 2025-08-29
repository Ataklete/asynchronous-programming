import java.awt.*;
import java.util.concurrent.*;

public class TimeConsuming {
    public static void main(String[] args) {

        ExecutorService services = Executors.newFixedThreadPool(2);
        long start = System.currentTimeMillis();
        Future<Integer> future1 = services.submit(new SumOfEvenTask());
        Future<Integer> future2 = services.submit(new SumOfSquarTask());
        while (!future1.isDone() && !future2.isDone()) {
            System.out.println("task is still in progress");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            System.out.println("sum of even number  " + future1.get());
            System.out.println("sum of even number  " + future2.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        long elapsedTime = System.currentTimeMillis() -start;
        System.out.println("time taken " + elapsedTime + " ms");
        services.shutdown();
    }
}
