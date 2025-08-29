import java.awt.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class SumOfSquarTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> IntStream.rangeClosed(1, 10).map(n -> n * n).sum());
        TimeUnit.SECONDS.sleep(3);
        return future.get();
    }
}

