package Testat1;

import java.util.concurrent.Semaphore;
public class Testat1 extends Thread{
    private int id;
    private static Semaphore Lok0 = new Semaphore(1,true);
    private static Semaphore Lok1 = new Semaphore(0, true);
    private static Semaphore mutex = new Semaphore(1,true);
    private static int[] state = new int[2];
    private static final int STEHEND = 0;
    private static final int WILLFAHREN = 1;
    private static final int INABSCHNITT = 2;

    public static void main(String[] args) {
        state[0] = STEHEND;
        state[1] = STEHEND;
        Thread firstThread = new Testat1(0);
        Thread secondThread = new Testat1(1);
        firstThread.start();
        secondThread.start();
    }
    public Testat1(int id){
        this.id = id;
    }
    public void run(){
        state[id] = WILLFAHREN;
        if(id == 0){
            while( true ) {
                try {
                    sleep(600);
                    System.out.println("Lok" + id + "geht gleich in enter Methode mit Status" + state[id]);
                    enterLok0();
                    System.out.println("Lok" + id + "ist im Abschnitt mit Status" + state[id]);
                    // Lok 0 im mittleren Teilstu¨ck
                    exitLok0();
                    System.out.println("Lok" + id + "ist aus dem Abschnitt raus mit Status" + state[id]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        if(id == 1) {
            while (true) {
                // fahren
                try {
                    sleep(1000);
                    System.out.println("Lok" + id + "geht gleich in enter Methode mit Status" + state[id]);
                    enterLok1();
                    System.out.println("Lok" + id + "ist im Abschnitt mit Status" + state[id]);

                    // Lok 1 im mittleren Teilstu¨ck
                    exitLok1();
                    System.out.println("Lok" + id + "ist aus dem Abschnitt raus mit Status" + state[id]);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void enterLok0() throws InterruptedException {
        mutex.acquire();
        System.out.println("Lok 0 ist in mutex enter");
        if(state[1]!= INABSCHNITT && state[0]==WILLFAHREN) {
            System.out.println("Lok 0 ist in if enter");

            state[0] = INABSCHNITT;
            Lok0.acquire();
        }
        mutex.release();
    }
    public void exitLok0() throws InterruptedException{
        mutex.acquire();
        state[0] = WILLFAHREN;
        Lok1.release();
        mutex.release();
    }
    public void enterLok1() throws InterruptedException{
        mutex.acquire();
        System.out.println("Lok 1 ist in mutex enter");


        if(state[0]!=INABSCHNITT && state[1] == WILLFAHREN){
            System.out.println("Lok 1 ist in if enter");

            state[1] = INABSCHNITT;
            Lok1.acquire();
        }
        mutex.release();
    }

    public void exitLok1() throws InterruptedException{
        mutex.acquire();
        state[1] = WILLFAHREN;
        Lok0.release();
        mutex.release();
    }
}
