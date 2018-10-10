package cells;

public abstract class Cell implements Runnable {

    public abstract void divide();
    public abstract void eat();
    public abstract boolean canDivide();
    public abstract boolean canLive();

    public void live(){
        while(canLive()){
            eat();
            if(canDivide())
            divide();
        }
    }
    
    @Override
    public void run() {
        live();
    }
}
