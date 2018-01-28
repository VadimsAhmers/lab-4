public class BusinessCenter {

    int liftFloor = 1;
    Visitor visitorAtControl = null;
    Visitor visitorInLift = null;
    boolean liftFree;

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

        System.out.println(visitor.toString() + " вошел в проходную.");
        return true;
    }

    void passControl (Visitor visitor){
        if (visitorAtControl!=visitor) throw new RuntimeException();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(visitor.toString() + " покинул проходную.");
        visitorAtControl = null;
    }

    boolean callLiftAndWait (Visitor visitor){
        while (visitorInLift!=null){
            try {
                Thread.currentThread().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        visitorInLift = visitor;
        System.out.println(visitor.toString() + " вызвал лифт");
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
                    targetFloor > liftFloor? "Лифт поднялся на " + targetFloor + " этаж"
                                        : "Лифт спустился на " + targetFloor + " этаж");
        }
        liftFloor = targetFloor;
    }

    void enterLift(Visitor visitor){
        System.out.println(visitor.toString() + " вошел в лифт");

    }

    void exitLift(Visitor visitor){
        visitorInLift = null;
        System.out.println(visitor.toString() + " вышел из лифта");

    }
}


