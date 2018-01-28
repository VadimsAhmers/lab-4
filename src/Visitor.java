public class Visitor implements Runnable{


    static int totalCount = 1;

    BusinessCenter place;
    int num, floor;

    public Visitor(BusinessCenter place) {
        this.place = place;
        this.num = totalCount++;
        floor = (int) (1 + Math.random() * 10);
    }

    @Override
    public void run(){
        enterBuilding();
        goUp();
        doSomeWork();
        goDown();
    }

    void enterBuilding() {
        System.out.println(this.toString() + " вошел в здание");
        if (place.enterControl(this))
            place.passControl(this);
    }

    void goUp(){
        if (place.callLiftAndWait(this)) {
            place.moveLift(null, 1);
            place.enterLift(this);
            place.moveLift(this, this.floor);
            place.exitLift(this);
        }

    }

    void doSomeWork(){

    }

    void goDown(){
        if (place.callLiftAndWait(this)) {
            place.moveLift(null, this.floor);
            place.enterLift(this);
            place.moveLift(this, 1);
            place.exitLift(this);
        }

    }

    @Override
    public String toString(){
        return "Посетитель" + this.num;
    }
}
