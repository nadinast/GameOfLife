package space;

import cells.AsexualCell;
import cells.Cell;
import food.Food;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Space {
    private ArrayList<Cell> cells = new ArrayList<>();
    private ArrayList<Food> food = new ArrayList<>();
    Lock lock = new ReentrantLock();

    public boolean checkSpaceForFood(String threadInfo) throws InterruptedException {
    	try {
    	    Boolean lockFood;
			Iterator<Food> foodIt = food.iterator();
			while(foodIt.hasNext()) {
				Food resource = foodIt.next();
			    lockFood = resource.lock.tryLock();
                if (lockFood)
                {
                    try{
                        System.out.println(threadInfo + " acquired lock for "+resource.name);
                        if(resource.getResourceUnits() > 0 ) {
                            resource.decrementResourceUnits();
                            System.out.println("Food:" + resource.name + " - " + "Resources left: " + resource.getResourceUnits());
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
                    System.out.println(threadInfo + " tried to lock food " + resource.name);
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
    
    public void addFood(int resources, String threadInfo) {
		try {
			Boolean lockFood;
			Iterator<Food> foodIt = food.iterator();
			while(foodIt.hasNext()) {
				Food availableResource = foodIt.next();
				lockFood = availableResource.lock.tryLock();
				if (lockFood)
				{
					try{
						System.out.println(threadInfo + " acquired lock for " + availableResource.name + "---- in order to add resource");
						availableResource.incrementResourceUnits(resources);
						break;
					}
					finally
					{
						// Make sure to unlock so that we don't cause a deadlock
						System.out.println(threadInfo+" unlocked " + availableResource.name);
						availableResource.lock.unlock();
					}
				}else {
					System.out.println(threadInfo + " tried to lock food " + availableResource.name);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void addToInitialFoodStash(Food food){
    	this.food.add(food);
	}

    public static void main(String[] args) {
    	Space gameOfLife = new Space();
    	
    	gameOfLife.addToInitialFoodStash(new Food(3, "RESOURCE1"));
    	gameOfLife.addToInitialFoodStash(new Food(2,"RESOURCE2"));
    	//gameOfLife.addFood(new Food(4));
    	//gameOfLife.addFood(new Food(1));
    	
    	Cell a = new AsexualCell(10,5,"A");
    	Cell b = new AsexualCell(4,5,"B");
    	Cell c = new AsexualCell(3,1,"C");
    	Cell d = new AsexualCell(1,3,"D");

    	Cell.spaceObj = gameOfLife;
    	
    	//gameOfLife.addCell(a);
    	gameOfLife.addCell(c);
    	gameOfLife.addCell(b);
    	//gameOfLife.addCell(d);


    	gameOfLife.startGameOfLife();
    	
    	//gameOfLife.printEverything();
    	
    }

}
