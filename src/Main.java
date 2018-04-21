import java.time.LocalDate;
import java.util.*;


public class Main extends Thread{

    @Override
    public void run(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.print("run");
    }
    public static void main(String[] args){
        Main example=new Main();
        example.start();
        System.out.print("main");
    }
}
