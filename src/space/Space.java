package space;

import cells.AsexualCell;
import cells.Cell;
import cells.SexualCell;
import food.Food;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Space {

    private LinkedBlockingQueue<Cell> cellsQueue = new LinkedBlockingQueue<Cell>();
    private ArrayList<Food> food = new ArrayList<>();

    public boolean checkSpaceForFood(String threadInfo) throws InterruptedException {
    	try {
    	    Boolean lockFood;
			    Iterator<Food> foodIt = food.iterator();
			    while(foodIt.hasNext()) {
				  Food resource = foodIt.next();
			    lockFood = resource.lock.tryLock();
                if (lockFood){
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
				        }else 
                    System.out.println(threadInfo + " tried to lock food " + resource.name);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
    }

    public void addCell(Cell c) {
        try {
            cellsQueue.put(c);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public LinkedBlockingQueue<Cell> getCellsQueue() {
        return cellsQueue;
    }

    public void removeCell(Cell c){
        cellsQueue.remove(c);
    }

    /*
    public void printEverything() {
    	for(Cell cell: cells) {
    		System.out.println(cell.toString());
    	}
    	for(Food resource: food) {
    		System.out.println(resource.name);
    	}
    }
    */

    public void startGameOfLife() {
    	for(Cell cell: cellsQueue) {
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
    	

    	gameOfLife.addToInitialFoodStash(new Food(5,"resourceA"));
    	gameOfLife.addToInitialFoodStash(new Food(5,"resourceB"));
    	gameOfLife.addToInitialFoodStash(new Food(5,"resourceC"));
      gameOfLife.addToInitialFoodStash(new Food(5,"resourceD"));

    	//gameOfLife.addFood(new Food(1));
    	
    	Cell a = new AsexualCell(10,5,"A");
    	Cell b = new AsexualCell(4,5,"B");
    	Cell c = new AsexualCell(3,1,"C");
    	Cell d = new AsexualCell(1,3,"D");

    	Cell e = new SexualCell(3,4,"sE");
    	Cell f = new SexualCell(3,4,"sF");
    	Cell g = new SexualCell(3,4,"sG");
    	Cell h = new SexualCell(3,4,"sH");

    	Cell.spaceObj = gameOfLife;
    	
    	//gameOfLife.addCell(a);

    	//gameOfLife.addCell(c);
    	//gameOfLife.addCell(b);
    	//gameOfLife.addCell(d);
        gameOfLife.addCell(e);
        gameOfLife.addCell(f);
        gameOfLife.addCell(g);
        gameOfLife.addCell(h);

    	gameOfLife.startGameOfLife();
    	
    	//gameOfLife.printEverything();
    	
    }

}
