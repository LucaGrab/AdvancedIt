package Testat1;
import java.util.concurrent.Semaphore;

public class Testat1A extends Thread{
    private int id;
    private int speed;
    private static Semaphore empty = new Semaphore(1, true);
    private static Semaphore full = new Semaphore(0,true);
    private static String buffer;

    public Testat1A(int id, int speed){
        this.id = id;
        this.speed = speed;
    }
    @Override
    public void run() {
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
                    Thread.sleep(speed*1000);
                    enterLok0();
                }catch(Exception e){
                    e.printStackTrace();
                }
                //Lok 0 im mittleren Teilstück

                exitLok0();
            }
        }
        if(id == 1){

            while(true){
                //fahren
                System.out.println("Lok" + id + " fährt außerhalb des KA");
                try {
                    Thread.sleep(speed*1000);
                    enterLok1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Lok 0 im mittleren Teilstück
                exitLok1();
            }
        }
    }
    public void enterLok0() throws Exception{
        System.out.println("Lok" + id + " will in den KA befahren");
        empty.acquire();//ist ein Platz frei?
        buffer = "hier sind Daten für den Buffer";
        System.out.println("Lok" + id + " befährt den KA");

    }
    public void exitLok0(){
        System.out.println("Lok" + id + " verlässt den KA");
        full.release();//befülle buffer, erzeuge
    }
    public void enterLok1()throws Exception{
        System.out.println("Lok" + id + " will in den KA befahren");
        full.acquire();//kann ich was verbrauchen
        buffer = null;//verbrauche
        System.out.println("Lok" + id + " befährt den KA");

    }
    public void exitLok1(){
        System.out.println("Lok" + id + " verlässt den KA");
        empty.release();//habe verbraucht
    }
    public static void main(String[] args) {
        for(int i = 0; i<2; i++){
            new Testat1A(i,2+2*i).start();
        }

    }
}
