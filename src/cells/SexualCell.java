package cells;

public class SexualCell extends Cell {
	
    public SexualCell(int timeUntilHungry, int timeUntilStarve, String name) {
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
