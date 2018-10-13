package cells;

public class AsexualCell extends Cell {
	
    public AsexualCell(int timeUntilHungry, int timeUntilStarve, String name) {
		super(timeUntilHungry, timeUntilStarve, name);
	}

	@Override
    public void divide() {

    }

    
    public void eat() {
    }

    @Override
    public boolean canDivide() {
        return false;
    }

    @Override
    public boolean canLive() {
        return false;
    }


}
