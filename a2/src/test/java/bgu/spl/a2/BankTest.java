package bgu.spl.a2;

import bgu.spl.a2.sim.actions.*;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Lenovo on 12/9/2017.
 */

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

            Action<Boolean> unregister0 = new Unregister("Dor","DataStructers");

            Action<Boolean> CloseCourse = new CloseACourse("Linear Algebra","Math");

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

            pool.submit(unregister0,"Dor",new StudentPrivateState());

            pool.submit(CloseCourse,"Linear Algebra", new CoursePrivateState());


            CountDownLatch l = new CountDownLatch(12);
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
            unregister0.getResult().subscribe(()-> l.countDown());
            CloseCourse.getResult().subscribe(()->l.countDown());
                try {
                l.await();
            } catch (InterruptedException e) {
            }
            pool.shutdown();

        System.out.println("END OF TEST");
    }
}
