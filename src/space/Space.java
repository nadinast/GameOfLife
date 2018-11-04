package space;

import cells.AsexualCell;
import cells.Cell;
import food.Food;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Space {
    private ArrayList<Cell> cells = new ArrayList<>();
    private ArrayList<Food> food = new ArrayList<>();

    public boolean checkSpaceForFood(String threadInfo) {
    	try {
    	    Boolean lockFood;
            Iterator<Food> foodIterator = food.iterator();
    	    while(foodIterator.hasNext()) {
    	        Food resource = foodIterator.next();
			    lockFood = resource.lock.tryLock();
                if (lockFood) {
                    try{
                        int availableResources = resource.getResourceUnits();
                        System.out.println(threadInfo + " acquired lock for " + resource.name +"\n"
                                +"Food " + resource.name + " has " + (availableResources - 1) + " resources left");
                        if(availableResources == 1) {
                            foodIterator.remove();
                            System.out.println(resource);
                        }
                        else
                            resource.decrementResourceUnits();
                        return true;
                    }
                    finally{
                        System.out.println(threadInfo+" unlocked "+resource.name);
                        resource.lock.unlock();
                    }
				}else
                    System.out.println(threadInfo + " tried to lock food "+resource.name);
			}
		} catch (Exception e) {
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
    
	public void removeFoodSource(Food food) {
		//if food.resourceCount === 0 =>
			//then GC & Remove from Array
	}
	
    public static void main(String[] args) {
    	Space gameOfLife = new Space();
    	
    	gameOfLife.addFood(new Food(3, "resourceA"));
    	gameOfLife.addFood(new Food(2,"resourceB"));
    	//gameOfLife.addFood(new Food(4));
    	//gameOfLife.addFood(new Food(1));
    	
    	Cell cell1 = new AsexualCell(4,5,"A");
    	Cell cell2 = new AsexualCell(3,1,"B");
    	//Cell cell3 = new AsexualCell(1,1,"C");
    	//Cell cell4 = new AsexualCell(1,3,"D");
        cell1.setSharedSpace(gameOfLife);
    	
    	//gameOfLife.addCell(a);
    	gameOfLife.addCell(cell2);
    	gameOfLife.addCell(cell1);
    	//gameOfLife.addCell(d);


    	gameOfLife.startGameOfLife();
    	
    	//gameOfLife.printEverything();
    	
    }

}
