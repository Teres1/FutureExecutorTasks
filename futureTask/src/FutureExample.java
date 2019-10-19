import java.io.IOException;
import java.util.concurrent.*;

public class FutureExample {
    public static void main(String[] args) throws IOException {
        FutureExample futureExample = new FutureExample();
        futureExample.test();
    }

    private long calculateNFactorial(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static long countExecutions(ExecutorService executor) {
        FutureExample futureExample = new FutureExample();
        Counter counter = new Counter();
        System.err.close();
        Callable<String> task = () -> {
            while (true) {
                counter.add(1);
                futureExample.calculateNFactorial(50);
                futureExample.calculateNFactorial(30);

            }
        };
        Future<String> future = executor.submit(task);
        try {
            String result = future.get(10, TimeUnit.SECONDS);
            System.out.println("result" + result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future.cancel(true);
        } finally {
            System.out.flush();
            System.err.flush();
        }
        executor.shutdown();
        return counter.getCounter();
    }

    public void test() {
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(1);
        long result = countExecutions(executorService);
        System.out.println("Executions count " + result);
        executorService.shutdown();
    }
}
