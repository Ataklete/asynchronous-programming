import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class VotingSimulator {

    static int noOfVotes = 0; // total number of votes cast
    static int voters = 0; // total number of voters

    public static void main(String[] args) throws InterruptedException {
        Map<Integer, String> candidateVotes = Map.of(
                1, "Alice",
                2, "Bob",
                3, "Charlie",
                4, "David",
                5, "Eve",
                6, "Frank",
                7, "Grace",
                8, "Heidi",
                9, "Ivan",
                10, "Judy"
        );
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of voters:");
        boolean flag;

        do {
            try {
                voters = scanner.nextInt();
                if (voters < 0) {
                    System.out.println("Number of voters must be non-negative. Please enter again:");
                    scanner.nextInt(); // Prompt again for valid input
                }
                flag = false; // Valid input received, exit loop
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer number.");
                scanner.next();
                flag = true;// Clear the invalid input
            }
        }while (flag);
        scanner.close();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1); //voting and counting
        Map<String, Integer> voteCount = new HashMap<>();
        // Simulate voting process every second adding random vote
        Runnable vote = () -> {
        Random random = new Random();
        System.out.println("enter your vote (1 for Alice, 2 for Bob, 3 for Charlie):");
            int v = random.nextInt(10) + 1;
            if (candidateVotes.containsKey(v)) {
                noOfVotes++;
                String candidate = candidateVotes.get(v);
                voteCount.put(candidate, voteCount.getOrDefault(candidate, 0) + 1);
                System.out.println("Vote recorded for " + candidate);
            } else {
                System.out.println("Invalid vote. Please vote again 1, 2 or 3.");
            }
        };
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(vote, 0, 1, TimeUnit.SECONDS);

        // Count and display votes every second
        Runnable count = () -> {
            voteCount.forEach((key, value) ->
                    System.out.println(key + " ==> has votes " + value + " people ==> percentage " + Math.ceil(value * 100.0 / noOfVotes) + "%")
            );
        };
        scheduledExecutorService.scheduleWithFixedDelay(count, 5, 1, TimeUnit.SECONDS);

        while (noOfVotes != voters) {
            TimeUnit.MILLISECONDS.sleep(100);   // wait for all votes to be cast
        }

        System.out.println("Voting completed with " + noOfVotes + " votes.");
        scheduledFuture.cancel(true); // stop voting

        try {
            if(!scheduledExecutorService.awaitTermination(1, TimeUnit.SECONDS)){
                scheduledExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        scheduledExecutorService.shutdown();
    }

}
