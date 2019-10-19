public class Counter {
    long counter;

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public Counter() {
    }
    public long add(int num) {
        return counter+=num;
    }
}
