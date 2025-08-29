import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SumOfEvenTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> IntStream.rangeClosed(1, 10).filter(n -> n % 2 == 0).sum());
        TimeUnit.SECONDS.sleep(3);
        return future.get();
    }
}
