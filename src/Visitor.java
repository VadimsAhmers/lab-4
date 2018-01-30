public class Visitor implements Runnable{


    private static int totalCount = 1;

    private BusinessCenter place;
    private int num, floor;

     Visitor(BusinessCenter place) {
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

    private void enterBuilding() {
        System.out.println(place.countDuration() + this.toString() + " вошел в здание, ему нужно на " + this.floor + " этаж");
        if (place.enterControl(this))
            place.passControl(this);
    }

    private void goUp(){
        if (place.callLiftAndWait(this)) {
            place.moveLift(null, 1);
            place.enterLift(this);
            place.moveLift(this, this.floor);
            place.exitLift(this);
        }

    }

    private void doSomeWork(){
        System.out.println(place.countDuration() + this.toString() + " делает свои дела");
        try {
            Thread.sleep((long)Math.abs(Math.random()*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(place.countDuration() + this.toString() + " закончил свои дела.");

    }

    private void goDown(){
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
