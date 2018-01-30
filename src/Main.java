public class Main {

    public static void main(String[] args) {

        int n = 5;
        int delay = 1000;

        BusinessCenter place = new BusinessCenter();

        for (int i = 0; i < n; i++) {

            Visitor visitor = new Visitor(place);
            Thread thread = new Thread(visitor);
            thread.start();

            try {
                Thread.sleep(delay);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
}
