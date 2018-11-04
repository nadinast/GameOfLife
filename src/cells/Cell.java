package cells;

import food.Food;
import space.Space;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Cell implements Runnable {
	private static Space space;

    protected final int TIME_UNTIL_HUNGRY;
    protected final int TIME_UNTIL_STARVE;
    protected final String NAME;

    protected int foodUnits;
    protected boolean alive = true;

    private int currentTimeUntilHungry;
    private int currentTimeUntilStarve;

    public Cell(int timeUntilHungry, int timeUntilStarve, String name) {
		this.foodUnits = 0;
		this.TIME_UNTIL_HUNGRY = timeUntilHungry;
		this.TIME_UNTIL_STARVE = timeUntilStarve;
		this.NAME = name;
        this.currentTimeUntilHungry = this.TIME_UNTIL_HUNGRY;
        this.currentTimeUntilStarve = this.TIME_UNTIL_STARVE;
	}

    public abstract void divide();
    public abstract boolean canDivide();

    public void resetHungryAndStarveTimes(){
    	this.currentTimeUntilHungry = this.TIME_UNTIL_HUNGRY;
    	this.currentTimeUntilStarve = this.TIME_UNTIL_STARVE;
    }


    public void addCellToSpace(Cell c){
        space.addCell(c);
    }

	public void live(){
        while(alive){
            eat();
            if(canDivide())
                divide();
       }
    }
	
	public void eat(){
        if (space.checkSpaceForFood(NAME)) {
            System.out.println(" - Cell: " + this.NAME + " ate.");
            foodUnits++;
            resetHungryAndStarveTimes();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            currentTimeUntilHungry--;
            if (currentTimeUntilHungry < 0) {
                currentTimeUntilStarve--;
                if (currentTimeUntilStarve == 0) {
                    System.out.println("----------Cell " + this.NAME + " died----------");
                    //randomly generated resources after cell death by starvation
                    int randomResources = ThreadLocalRandom.current().nextInt(1, 5);
                    space.addFood(new Food(randomResources, "cellFood" + this.NAME));
                    System.out.println("----------Cell "+ this.NAME + " generated " + randomResources + " resources!----------");
                    this.alive = false;
                }
            }
        }
    }

    @Override
    public void run() {
        System.out.println("----Cell " + NAME + " is alive!----");
        live();
    }

    @Override
    public String toString() {
        return this.NAME;
    }

    public void setSharedSpace(Space space) {
        this.space = space;
    }
}
