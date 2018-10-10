package cells;

public class AsexualCell extends Cell {
    @Override
    public void divide() {

    }

    @Override
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
