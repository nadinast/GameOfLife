package cells;

import food.Food;
import space.Space;

public abstract class Cell implements Runnable {

	public static Space spaceObj;
	
    public abstract void divide();
    public abstract boolean canDivide();
    public abstract boolean canLive();

    protected int foodUnits;
    protected int timeUntilHungry;
    protected int timeUntilStarve;
    
    private int timeHungry; //
    private int timeStarve;
    
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
    
	public void live() throws InterruptedException{
        //while(canLive()){
            eat(spaceObj);
            //if(canDivide())
            //divide();
       // }
    }
	
	public void eat(Space space) throws InterruptedException {
		if(space.checkSpaceForFood(cellName)) {
			System.out.println(" - Cell: " + this.cellName + " ate. ");
			this.timeUntilHungry = timeHungry;
			foodUnits ++;
			setTime(); //time for hungry&starve are reset
			Thread.sleep(this.timeHungry * 1000);

			live();
			//wake Food thread up;
		}else {
			timeUntilHungry --;
			
			if(timeUntilHungry < 0) {
				timeUntilStarve --;
				//System.out.println(this.cellName + " has timeUntilStarve " + timeUntilStarve);
				if(timeUntilStarve == 0) {
					System.out.println("For Cell " + this.cellName + " it's game over!");
					spaceObj.addFood(new Food(this.timeHungry * 2));
				}else if(timeUntilStarve > 0) {
					eat(space);
				}
			}else {			
				//System.out.println(this.cellName + " has timeUntilHungry " + timeUntilHungry);
				eat(space);
			}
		}
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
