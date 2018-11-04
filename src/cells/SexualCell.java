package cells;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SexualCell extends Cell {
    public final Lock lock = new ReentrantLock();
    private boolean divisible = false;
    public boolean hasDivided = false;

    public boolean getDivisibleStatus() {
        return divisible;
    }

    public void setDivisibleStatus(boolean status) {
        this.divisible = status;
    }

    public SexualCell(int timeUntilHungry, int timeUntilStarve, String name) {
        super(timeUntilHungry, timeUntilStarve, name);
    }

    @Override
    public void divide() {
        System.out.println("~~~~~~" + this.cellName + " wants to divide!");
        //search for cells that want to divide too
        LinkedBlockingQueue<Cell> cellsQ = spaceObj.getCellsQueue();
        Iterator<Cell> it = cellsQ.iterator();
        try {
            while (it.hasNext()) {
                Cell currentCell = it.next();
                if(!(currentCell instanceof SexualCell))
                    continue;
                SexualCell sexualCell = (SexualCell)currentCell;
                if (!sexualCell.equals(this)) {
                    if (sexualCell.getDivisibleStatus() && sexualCell.hasDivided == false) {
                        boolean lockCell = sexualCell.lock.tryLock();
                        if (lockCell) {
                            try {
                                boolean lockThis = sexualCell.lockCell(this);
                                if(lockThis) {
                                    try {
                                        System.out.println("********************************"+this.cellName+ " was locked by "+currentCell.cellName);
                                        //make baby
                                        this.divisible = false;
                                        this.hasDivided = true;
                                        sexualCell.setDivisibleStatus(false);
                                        sexualCell.hasDivided = true;
                                        System.out.println("#############################################> Sexual division this-> " + this.cellName + " and other-> " + currentCell.cellName);
                                        System.out.println("#############################################");
                                        Cell c = new SexualCell(this.timeUntilHungry, this.timeUntilStarve, this.cellName + "-Schild");
                                        spaceObj.addCell(c);
                                        Thread t = new Thread(c);
                                        t.start();
                                    }
                                    finally{
                                        this.alive = false;
                                        currentCell.alive = false;

                                        spaceObj.removeCell(sexualCell);
                                        System.out.println("Removed current cell -> " + this.cellName);
                                        spaceObj.removeCell(this);
                                        System.out.println("Removed other cell -> " + sexualCell.cellName);
                                        sexualCell.unlockCell(this);
                                    }
                                }
                            } finally {
                                sexualCell.lock.unlock();
                                //MUST ALSO STOP THREADS??!!!

                            }
                        }

                    }
                }
                if(this.hasDivided == true)
                    break;
                it.next();
            }
        } catch (Exception e) {
            System.out.println("No cells left in queue!");
        }
    }

    public void eat() {

    }

    public boolean lockCell(SexualCell c) {
        if (c.lock.tryLock()) {
            return true;
        } else {
            return false;
        }
    }

    public void unlockCell(SexualCell c) {
        c.lock.unlock();
    }

    public boolean canDivide() {
        if ((this.foodUnits >= 4) && (hasDivided == false)) {
            divisible = true;
            return true;
        }
        return false;
    }
    /*
    @Override
    public boolean canLive() {
        return false;
    }
    */
}
