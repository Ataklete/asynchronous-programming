import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class ThenCombine {
    public static void main(String[] args) throws Exception {

        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> IntStream.rangeClosed(1, 10).filter(n -> n % 2 == 0).sum())
                .thenCombine(CompletableFuture.supplyAsync(() -> IntStream.rangeClosed(1, 10).map(n -> n * n).sum()), (res1, res2) -> res1 + res2);
        System.out.println("sum of even number  + sum of squares " + cf.get()); // 220900

    }
}