package Testat1;
import java.util.concurrent.Semaphore;

public class Testat1B extends Thread{
    private static Semaphore lok0 = new Semaphore(0, true);
    private static Semaphore lok1 = new Semaphore(0, true);
    private static Semaphore mutex = new Semaphore(1,true);
    private int id;
    private int speed;
    private static int[] state = new int[2];
    private static final int NORMALFAHREND = 0;
    private static final int VORWEICHE = 1;
    private static final int IMABSCHNITT = 2;
    private static final boolean[] istAnDerReihe = new boolean[2];
    public static void main(String[] args) {
        Testat1B firstLok = new Testat1B(0, 1000);
        Testat1B secondLok = new Testat1B(1,3000);
        firstLok.start();
        secondLok.start();
    }
    public Testat1B(int id, int speed){
        this.id = id;
        this.speed = speed;
        state[id] = 0;
        istAnDerReihe[0] = true;
        istAnDerReihe[1] = false;
    }
    @Override
    public void run(){
        if(id == 0){
            try{
                Thread.sleep(5000);

            }catch(Exception e){
                e.printStackTrace();
            }
            while(true){
                //fahren
                System.out.println("Lok" + id + " fährt außerhalb des KA");
                try{
                    Thread.sleep(speed);
                    enterLok0();
                    exitLok0();

                }catch(Exception e){
                    e.printStackTrace();
                }
                //Lok 0 im mittleren Teilstück

            }
        }
        if(id == 1){

            while(true){
                //fahren
                System.out.println("Lok" + id + " fährt außerhalb des KA");
                try {
                    Thread.sleep(speed);
                    enterLok1();
                    exitLok1();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Lok 0 im mittleren Teilstück
            }
        }
    }
    public void enterLok0() throws Exception{
        mutex.acquire();
        System.out.println("Lok" + id + " will in KA");
        if(state[1] != 2 && istAnDerReihe[0] == true){
            state[0] = 2;
            lok0.release();
        }else{
            state[0] = 1;
            System.out.println("Lok" + id + " muss warten");
        }
        mutex.release();
        lok0.acquire();
        System.out.println("Lok" + id + " ist in KA");
    }
    public void exitLok0() throws Exception{
        mutex.acquire();
        state[0] = 0;
        istAnDerReihe[id] = false;
        istAnDerReihe[1] = true;
        System.out.println("Lok" + id + " ist aus KA raus");
if(state[1] == 1){
    lok1.release();
}
mutex.release();
    }
    public void enterLok1() throws Exception{
        mutex.acquire();
        System.out.println("Lok" + id + " will in KA");
        System.out.println(id + "state" + state[id] + istAnDerReihe[id]);
        if(state[0] != 2 && istAnDerReihe[1] == true){
            state[1] = 2;
            lok1.release();
        }else{
            state[1] = 1;
            System.out.println("Lok" + id + " muss warten");
        }
        mutex.release();
        lok1.acquire();
        System.out.println("Lok" + id + " ist in KA");
    }
    public void exitLok1() throws Exception{
        mutex.acquire();
        state[1] = 0;
        istAnDerReihe[id] = false;
        istAnDerReihe[0] = true;
        System.out.println("Lok" + id + " ist aus KA raus");
        if(state[0] == 1){
            lok0.release();
        }
        mutex.release();
    }
    }

