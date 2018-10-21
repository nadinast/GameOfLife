package food;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Food {
	private int resourceUnits;
	public String name;
    public final Lock lock = new ReentrantLock();
	
	public Food(int resourceUnits) {
		this.resourceUnits = resourceUnits;
		name = String.valueOf(resourceUnits);
	}
	
	public int getResourceUnits() throws InterruptedException {
		//Thread.sleep(new Random().nextInt(1000));
		return resourceUnits;
	}
	
	public void decrementResourceUnits() {
		this.resourceUnits --;
	}
}
