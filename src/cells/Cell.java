package cells;

public abstract class Cell implements Runnable {

    public abstract void divide();
    public abstract void eat();

    @Override
    public void run() {

    }
}
