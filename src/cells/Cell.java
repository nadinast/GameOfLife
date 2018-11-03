package cells;

import food.Food;
import space.Space;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Cell implements Runnable {
	public static Space spaceObj;

    public abstract void divide();
   // public abstract boolean canLive();

    protected int foodUnits;
    protected int timeUntilHungry;
    protected int timeUntilStarve;
    
    private int timeHungry;
    private int timeStarve;
    protected boolean alive = true;
    
    protected String cellName;
    
    public Cell(int timeUntilHungry, int timeUntilStarve, String name) {
		this.foodUnits = 0;
		this.timeUntilHungry = timeUntilHungry;
		this.timeUntilStarve = timeUntilStarve;
		this.cellName = name;
		setTime();
	}
    
    public void setTime(){
    	this.timeHungry = this.timeUntilHungry;
    	this.timeStarve = this.timeUntilStarve;
    }

    public void addCellToSpace(Cell c){
        spaceObj.addCell(c);
    }

	public void live() throws InterruptedException{
        while(alive){
            eat(spaceObj);
            if(canDivide()) divide();
       }
    }

	
	public void eat(Space space) throws InterruptedException {
        if (space.checkSpaceForFood(cellName)) {
            System.out.println(" - Cell: " + this.cellName + " ate. ");
            //this.timeUntilHungry = timeHungry;
            foodUnits++;
            setTime(); //time for hungry&starve are reset
            //Thread.sleep(this.timeHungry * 1000); //only for testing!
            Thread.sleep(1000);

        } else {
            timeHungry--;
            if (timeHungry < 0) {
                timeStarve--;
                if (timeStarve == 0) {
                    System.out.println("----------For Cell " + this.cellName + " it's game over!----------");
                    //randomly generated resources after cell death by starvation
                    int randomResources = ThreadLocalRandom.current().nextInt(1, 5);
                    //spaceObj.addFood(new Food(randomResources, "cellFood"+this.cellName));
                    System.out.println("----------Cell "+this.cellName+" has generated "+randomResources+" resources!----------");
                    System.out.println();
                    this.alive = false;
                }
            }
        }
    }

    public boolean canDivide() {
        if(this.foodUnits >= 4){
            return true;
        }
        return false;
    }
	
	public String toString() {
		return this.cellName;
	}
    
    @Override
    public void run() {
        try {
			live();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
