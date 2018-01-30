 class BusinessCenter {

    private int liftFloor = 1;
    private Visitor visitorAtControl = null;
    private Visitor visitorInLift = null;
    private boolean liftFree = true;
    private long startTime = System.currentTimeMillis();

    boolean enterControl (Visitor visitor){

        while (visitorAtControl!=null){
            try {
                Thread.currentThread().wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        visitorAtControl = visitor;

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
        System.out.println(countDuration() + visitor.toString() + " покинул проходную.");
        visitorAtControl = null;
        notifyAll();
    }

    boolean callLiftAndWait (Visitor visitor){
        while (!liftFree){
            try {
                Thread.currentThread().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        liftFree = false;
        visitorInLift = visitor;
        System.out.println(countDuration() + visitor.toString() + " вызвал лифт");
        return true;

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
        else System.out.println(countDuration() + "Лифт находится на том же этаже, что и посетитель");
        liftFloor = targetFloor;
    }

    void enterLift(Visitor visitor){
        System.out.println(countDuration() + visitor.toString() + " вошел в лифт");

    }

    void exitLift(Visitor visitor){
        visitorInLift = null;
        liftFree = true;
        System.out.println(countDuration() + visitor.toString() + " вышел из лифта");
        notifyAll();

    }
    String countDuration(){
        long duration =  System.currentTimeMillis() - startTime;
        return duration + " ms: ";
    }
}


