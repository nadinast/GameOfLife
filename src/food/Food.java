package food;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Food {
	private int resourceUnits;
	public String name;
    public final Lock lock = new ReentrantLock();
	
	public Food(int resourceUnits, String name) {
		this.resourceUnits = resourceUnits;
		//name = String.valueOf(resourceUnits);
		this.name = name;
	}
	
	public int getResourceUnits() {
		//Thread.sleep(new Random().nextInt(1000));
		return resourceUnits;
	}
	
	public void decrementResourceUnits() {
		this.resourceUnits --;
	}

	@Override
	public String toString() {
		return "Food{" +
				"resourceUnits=" + resourceUnits +
				", name='" + name + '\'' +
				'}';
	}

    public void incrementResourceUnits(int units) {
        this.resourceUnits += units;
    }
}
