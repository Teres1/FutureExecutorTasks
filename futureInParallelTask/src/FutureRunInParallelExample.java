

import java.util.concurrent.*;


public class FutureRunInParallelExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureRunInParallelExample futureTask = new FutureRunInParallelExample();
        futureTask.test();

    }

    private long calculateNFactorial(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    //Count the number of the parallel executions of calculateNFactorial for 10seconds
    private long[] countExecutionsOfCalculateNFactorial(ExecutorService executor) throws InterruptedException, TimeoutException, ExecutionException {
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        System.err.close();

        Callable<String> task1 = () -> {
            while (true) {
                counter1.add(1);
                FutureRunInParallelExample futureRunInParallelExample = new FutureRunInParallelExample();
                futureRunInParallelExample.calculateNFactorial(50);

            }
        };
        Callable<String> task2 = () -> {
            while (true) {
                counter2.add(1);
                calculateNFactorial(30);


            }
        };
        Future<String> future1 = executor.submit(task1);
        Future<String> future2 = executor.submit(task2);
        try {
            String result1 = future1.get(10, TimeUnit.SECONDS);
            String result2 = future2.get(0, TimeUnit.SECONDS);

            System.out.println("result1" + result1);
            System.out.println("result2" + result2);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            future1.cancel(true);
            future2.cancel(true);

        } finally {
            System.out.flush();
            System.err.flush();
        }


        return new long[]{counter1.getCounter(), counter2.getCounter()};
    }


    private void test() throws InterruptedException, TimeoutException, ExecutionException {
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(2);
        long start = System.currentTimeMillis();
        long[] result3 = countExecutionsOfCalculateNFactorial(executorService);
        System.out.println("Executions count of calculate n factorial in parallel " + result3[0] + " " + result3[1]);
        long end = System.currentTimeMillis();
        System.out.println("elapsed: " + (end - start) / 1000);
        executorService.shutdown();
    }
}



