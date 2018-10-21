package space;

import cells.AsexualCell;
import cells.Cell;
import food.Food;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Space {
    private ArrayList<Cell> cells = new ArrayList<>();
    private CopyOnWriteArrayList<Food> food = new CopyOnWriteArrayList<>();
    Lock lock = new ReentrantLock();

    public boolean checkSpaceForFood(String threadInfo) throws InterruptedException {
    	try {
    	    Boolean lockFood;
			for(Food resource: food) {
			    lockFood = resource.lock.tryLock();
                if (lockFood)
                {
                    try{
                        System.out.println(threadInfo + " acquired lock for "+resource.name);
                        if(resource.getResourceUnits() > 0 ) {
                            System.out.println("Food:" + resource.name + " - ");
                            resource.decrementResourceUnits();
                            System.out.println("Resources left: " + resource.getResourceUnits());
                            return true;
                        }
                    }
                    finally
                    {
                        // Make sure to unlock so that we don't cause a deadlock
                        System.out.println(threadInfo+" unlocked "+resource.name);
                        resource.lock.unlock();
                    }
				}else {
                    System.out.println(threadInfo + " tried to lock food "+resource.name);
                }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
    }
    
    public void addCell(Cell c) {
    	cells.add(c);
    }
    
    public void addFood(Food f) {
    	food.add(f);
    }
    
    public void printEverything() {
    	for(Cell cell: cells) {
    		System.out.println(cell.toString());
    	}
    	for(Food resource: food) {
    		System.out.println(resource.name);
    	}
    }
    
    public void startGameOfLife() {
    	for(Cell cell: cells) {
    		Thread t = new Thread(cell);
    		t.start();
    	}
    }
    
	public void removeFoodSource(Food foodObj) {
		//if foodObj.resourceCount === 0 => 
			//then GC & Remove from Array
	}
	
    public static void main(String[] args) {
    	Space gameOfLife = new Space();
    	
    	gameOfLife.addFood(new Food(1));
    	gameOfLife.addFood(new Food(2));
    	//gameOfLife.addFood(new Food(4));
    	//gameOfLife.addFood(new Food(1));
    	
    	Cell a = new AsexualCell(10,5,"A");
    	Cell b = new AsexualCell(3,1,"B");
    	Cell c = new AsexualCell(1,1,"C");
    	Cell d = new AsexualCell(1,3,"D");

    	Cell.spaceObj = gameOfLife;
    	
    	//gameOfLife.addCell(a);
    	gameOfLife.addCell(b); 
    	gameOfLife.addCell(c);
    	//gameOfLife.addCell(d);


    	gameOfLife.startGameOfLife();
    	
    	//gameOfLife.printEverything();
    	
    }

}
