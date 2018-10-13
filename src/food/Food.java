package food;

import java.util.Random;

public class Food {
	private int resourceUnits;
	public String name;
	
	public Food(int resourceUnits) {
		this.resourceUnits = resourceUnits;
		name = String.valueOf(resourceUnits);
	}
	
	public int getResourceUnits() throws InterruptedException {
		return resourceUnits;
	}
	
	public void decrementResourceUnits() {
		this.resourceUnits --;
	}
}
