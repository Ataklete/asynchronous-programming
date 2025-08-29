import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class ThenApply {
    public static void main(String[] args) throws Exception {

        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> IntStream.rangeClosed(1, 10).filter(n -> n % 2 == 0).sum());
        CompletableFuture<String> stringCompletableFuture = cf.thenApply(Integer::toBinaryString);
        System.out.println("sum of even number in binary " + stringCompletableFuture.get()); // Output: sum of even number in binary 1100

    }
}