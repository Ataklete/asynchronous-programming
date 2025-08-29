import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class ThenCompose {
    public static void main(String[] args) throws Exception {

        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> IntStream.rangeClosed(1, 10).filter(n -> n % 2 == 0).sum())
                .thenCompose(result -> CompletableFuture.supplyAsync(() -> result * result));
        System.out.println("sum of even number squared " + cf.get()); // 900
    }
}