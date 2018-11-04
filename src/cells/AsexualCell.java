package cells;

public class AsexualCell extends Cell {
	
    public AsexualCell(int timeUntilHungry, int timeUntilStarve, String name) {
		super(timeUntilHungry, timeUntilStarve, name);
	}

	@Override
    public void divide() {
        Cell c1 = new AsexualCell(this.timeUntilHungry,this.timeUntilStarve,this.cellName + this.cellName);
        Cell c2 = new AsexualCell(this.timeUntilHungry,this.timeUntilStarve,this.cellName + this.cellName);
        this.alive = false; //this cell that divided is no longer alive
        System.out.println("~~~~~~~~~~~~CELL " + this.cellName+" HAS DIVIDED!~~~~~~~~~~~");
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        //add the cells to the space
        addCellToSpace(c1);
        addCellToSpace(c2);
        //start two new threads
        t1.start();
        t2.start();
    }

    
    public void eat() {
    }

    @Override
    public boolean canDivide() {
        if(this.foodUnits >= 4){
            return true;
        }
        return false;
    }
    /*
    @Override
    public boolean canLive() {
        return false;
    }*/

}
