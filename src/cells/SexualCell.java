package cells;

public class SexualCell extends Cell {
	
    public SexualCell(int timeUntilHungry, int timeUntilStarve, String name) {
		super(timeUntilHungry, timeUntilStarve, name);
	}
    
	@Override
    public void divide() {

    }

    @Override
    public boolean canDivide() {
        return false;
    }
}
