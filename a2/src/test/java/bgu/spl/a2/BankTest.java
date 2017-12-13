package bgu.spl.a2;

import bgu.spl.a2.sim.actions.AddStudent;
import bgu.spl.a2.sim.actions.OpenANewCourse;
import bgu.spl.a2.sim.actions.ParticipatingInCourse;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Lenovo on 12/9/2017.
 */
class confirmation extends Action{
    String clientA;
    String clientB;
    String bankB;
    PrivateState bankState;

    public confirmation(String clientA,String clientB,String bankB,PrivateState bankState){
        this.clientA = clientA;
        this.clientB =clientB;
        this.bankB =bankB;
        this.bankState =bankState;
        Result = new Promise<Boolean>();
        this.setActionName("Confirmation");
    }
    @Override
    protected void start() {
        Result.resolve(true);//just for test lets say the other bank always approve the transaction
    }
}



class Transmission extends Action{
    int amount;
    String clientA;
    String clientB;
    String bankA;
    String bankB;
    PrivateState bankState;
    VersionMonitor vm ;
    public Transmission(int amount,String clientA,String clientB,String bankA,String bankB,PrivateState bankState){
        this.amount = amount;
        this.clientA =clientA;
        this.clientB = clientB;
        this.bankA =bankA;
        this.bankB =bankB;
        this.bankState =bankState;
        vm = new VersionMonitor();
        Result = new Promise<String>();
        this.setActionName("Transmission");
    }

    public VersionMonitor getVm(){
        return vm;
    }
    protected void start(){
        System.out.println("Start Transmission");

        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> confAction1 = new confirmation(clientA,clientB,bankB,new PrivateState() {});
        Action<Boolean> confAction = new confirmation(clientA,clientB,bankB,new PrivateState() {});
        actions.add(confAction);
        actions.add(confAction1);
        sendMessage(confAction1, bankB, new PrivateState() {});
        sendMessage(confAction, bankB, new PrivateState() {});
        then(actions,()->{
            Boolean result = actions.get(0).getResult().get();
            if(result==true){
                complete("transmission good");
                System.out.println("transmission good");
            }
            else{
                complete("transmission bad");
                System.out.println("transmission bad");
            }
        });

    }

}

public class BankTest {

    public static void main(String[] args) throws InterruptedException {

            ActorThreadPool pool = new ActorThreadPool(8);


            Action<Boolean> AddStudent0 = new AddStudent("Tom","CS");
            Action<Boolean> AddStudent1 = new AddStudent("Dor","CS");
            Action<Boolean> AddStudent2 = new AddStudent("Amit","CS");

            Action<Boolean> PartInCourse0 = new ParticipatingInCourse("Tom","DataStructers",56);
            Action<Boolean> PartInCourse1 = new ParticipatingInCourse("Dor","DataStructers",88);
            Action<Boolean> PartInCourse2 = new ParticipatingInCourse("Amit","Linear Algebra",30);
            Action<Boolean> PartInCourse3 = new ParticipatingInCourse("Tom","System Programing",56);

            Action<Boolean> OpenCourse0 = new OpenANewCourse("DataStructers",10,new Vector<String>() ,"CS");
            Action<Boolean> OpenCourse1 = new OpenANewCourse("Linear Algebra",10,new Vector<String>() ,"Math");
            Vector<String> pre = new Vector<>();
            pre.add("DataStructers");
            Action<Boolean> OpenCourse2 = new OpenANewCourse("System Programing",10,pre,"CS");

            pool.submit(AddStudent0,"Tom",new StudentPrivateState());
            pool.submit(AddStudent1,"Dor",new StudentPrivateState());
            pool.submit(AddStudent2,"Amit",new StudentPrivateState());

            pool.start();

            pool.submit(OpenCourse0,"DataStructers", new CoursePrivateState());
            pool.submit(OpenCourse1,"Linear Algebra", new CoursePrivateState());
            pool.submit(OpenCourse2,"System Programing", new CoursePrivateState());

            pool.submit(PartInCourse3,"Tom",new StudentPrivateState());
            pool.submit(PartInCourse0,"Tom",new StudentPrivateState());
            pool.submit(PartInCourse1,"Dor",new StudentPrivateState());
            pool.submit(PartInCourse2,"Amit",new StudentPrivateState());




            CountDownLatch l = new CountDownLatch(10);
            OpenCourse0.getResult().subscribe(() -> l.countDown());
            OpenCourse1.getResult().subscribe(() -> l.countDown());
            OpenCourse2.getResult().subscribe(() -> l.countDown());
            AddStudent0.getResult().subscribe(()-> l.countDown());
            AddStudent1.getResult().subscribe(()-> l.countDown());
            AddStudent2.getResult().subscribe(()-> l.countDown());
            PartInCourse0.getResult().subscribe(()-> l.countDown());
            PartInCourse1.getResult().subscribe(()-> l.countDown());
            PartInCourse2.getResult().subscribe(()-> l.countDown());
            PartInCourse3.getResult().subscribe(()-> l.countDown());
            try {
                l.await();
            } catch (InterruptedException e) {
            }
            pool.shutdown();

        System.out.println("END OF TEST");
    }
}
