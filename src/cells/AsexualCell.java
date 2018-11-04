package cells;

public class AsexualCell extends Cell {
	
    public AsexualCell(int timeUntilHungry, int timeUntilStarve, String name) {
		super(timeUntilHungry, timeUntilStarve, name);
	}

	@Override
    public void divide() {
        this.alive = false;
        System.out.println("~~~~~~~~~~~~CELL "+this.NAME+" HAS DIVIDED!~~~~~~~~~~~");

        Cell c1 = new AsexualCell(this.TIME_UNTIL_HUNGRY,this.TIME_UNTIL_STARVE,this.NAME+"-child1");
        Cell c2 = new AsexualCell(this.TIME_UNTIL_HUNGRY, this.TIME_UNTIL_STARVE,this.NAME+"-child2");
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);

        addCellToSpace(c1);
        addCellToSpace(c2);

        t1.start();
        t2.start();
    }

    @Override
    public boolean canDivide() {
        if(this.foodUnits >= 4){
            return true;
        }
        return false;
    }

}
