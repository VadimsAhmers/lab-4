 class BusinessCenter {

    private int liftFloor = 1;
    private Visitor visitorAtControl = null;
    private Visitor visitorInLift = null;
    private boolean liftFree = true;
    private long startTime = System.currentTimeMillis();
    long liftWaitTimeBeforeWalking = 1000;

    boolean enterControl (Visitor visitor){

        synchronized (this) {
            while (visitorAtControl != null) {
                try {
                    System.out.println(countDuration() + visitor.toString() + " ожидает освобождения проходной");
                    this.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            visitorAtControl = visitor;
        }
            System.out.println(countDuration() + visitor.toString() + " вошел в проходную.");
            return true;
    }

    void passControl (Visitor visitor){
        if (visitorAtControl!=visitor) throw new RuntimeException();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             synchronized (this) {
            System.out.println(countDuration() + visitor.toString() + " покинул проходную.");
            visitorAtControl = null;
            notifyAll();
        }
    }

    boolean callLiftAndWait (Visitor visitor){
        synchronized (this) {
            while (!liftFree) {
                try {
                    System.out.println(countDuration() + visitor.toString() + " ожидает освобождения лифта");

                    this.wait();/*(liftWaitTimeBeforeWalking);
                    System.out.println(countDuration() + visitor.toString() + " подождал 1 секунду и пошел пешком");
                   visitor.walk();
                    break;*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            liftFree = false;
            visitorInLift = visitor;
            System.out.println(countDuration() + visitor.toString() + " вызвал лифт");
            return true;
        }
    }

    void moveLift(Visitor visitor, int targetFloor){
        if (targetFloor!=liftFloor) {
            long delay = (Math.abs(targetFloor - liftFloor)) * 500;
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(
                    targetFloor > liftFloor? countDuration() + "Лифт поднялся на " + targetFloor + " этаж"
                                        : countDuration() + "Лифт спустился на " + targetFloor + " этаж");
        }
        else System.out.println(countDuration() + "Лифт находится на том же этаже, что и " + visitorInLift.toString());
        liftFloor = targetFloor;
    }

    void enterLift(Visitor visitor){
        System.out.println(countDuration() + visitor.toString() + " вошел в лифт");
    }

    void exitLift(Visitor visitor){
        synchronized (this) {
            visitorInLift = null;
            liftFree = true;
            System.out.println(countDuration() + visitor.toString() + " вышел из лифта");
            notifyAll();
        }
    }
    String countDuration(){
        long duration =  System.currentTimeMillis() - startTime;
        return duration + " ms: ";
    }
}


