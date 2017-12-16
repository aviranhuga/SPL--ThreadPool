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
            for (int i=0 ; i<100000 ; i++) {
                    ActorThreadPool pool = new ActorThreadPool(8);
                    pool.start();

                    Action<Boolean> OpenCourse0 = new OpenANewCourse(10 ,new Vector<String>(), "CS");
                    pool.submit(OpenCourse0, "DataStructers", new CoursePrivateState());

                    CountDownLatch l1 = new CountDownLatch(1);
                    OpenCourse0.getResult().subscribe(() -> l1.countDown());
                    try {
                            l1.await();
                    } catch (InterruptedException e) {
                    }

                    Action<Boolean> AddStudent0 = new AddStudent("CS");
                    Action<Boolean> AddStudent1 = new AddStudent("CS");
                    Action<Boolean> AddStudent2 = new AddStudent("CS");
                    Action<Boolean> AddStudent3 = new AddStudent("CS");
                    Action<Boolean> AddStudent4 = new AddStudent("CS");
                    Action<Boolean> AddStudent5 = new AddStudent("CS");

                    pool.submit(AddStudent0, "Tom", new StudentPrivateState());
                    pool.submit(AddStudent1, "Dor", new StudentPrivateState());
                    pool.submit(AddStudent2, "Amit", new StudentPrivateState());
                    pool.submit(AddStudent3, "Sasha", new StudentPrivateState());
                    pool.submit(AddStudent4, "Shir1", new StudentPrivateState());
                    pool.submit(AddStudent5, "Shir2", new StudentPrivateState());

                    Action<Boolean> PartInCourse0 = new ParticipatingInCourse("DataStructers", 100);
                    Action<Boolean> PartInCourse1 = new ParticipatingInCourse("DataStructers", 88);
                    Action<Boolean> PartInCourse2 = new ParticipatingInCourse("DataStructers", 30);
                    Action<Boolean> PartInCourse3 = new ParticipatingInCourse("DataStructers", 57);
                    Action<Boolean> PartInCourse4 = new ParticipatingInCourse("DataStructers", 57);
                    Action<Boolean> PartInCourse5 = new ParticipatingInCourse("DataStructers", 30);

                    pool.submit(PartInCourse0, "Tom", new StudentPrivateState());
                    pool.submit(PartInCourse1, "Dor", new StudentPrivateState());
                    pool.submit(PartInCourse2, "Amit", new StudentPrivateState());
                    pool.submit(PartInCourse3, "Sasha", new StudentPrivateState());
                    pool.submit(PartInCourse4, "Shir1", new StudentPrivateState());
                    pool.submit(PartInCourse5, "Shir12", new StudentPrivateState());

                    Action<Boolean> AddNewPlace = new OpeningNewPlacesInACourse(30,"DataStructers");

                    pool.submit(AddNewPlace,"DataStructers",new CoursePrivateState());

                    CountDownLatch l = new CountDownLatch(13);
                    AddStudent0.getResult().subscribe(() -> l.countDown());
                    AddStudent1.getResult().subscribe(() -> l.countDown());
                    AddStudent2.getResult().subscribe(() -> l.countDown());
                    AddStudent3.getResult().subscribe(() -> l.countDown());
                    AddStudent4.getResult().subscribe(() -> l.countDown());
                    AddStudent5.getResult().subscribe(() -> l.countDown());
                    PartInCourse0.getResult().subscribe(() -> l.countDown());
                    PartInCourse1.getResult().subscribe(() -> l.countDown());
                    PartInCourse2.getResult().subscribe(() -> l.countDown());
                    PartInCourse3.getResult().subscribe(() -> l.countDown());
                    PartInCourse4.getResult().subscribe(() -> l.countDown());
                    PartInCourse5.getResult().subscribe(() -> l.countDown());
                    AddNewPlace.getResult().subscribe(() -> l.countDown());
                    try {
                            l.await();
                    } catch (InterruptedException e) {
                    }

                    Action<Boolean> CloseCourse = new CloseACourse("CS");
                    pool.submit(CloseCourse,"DataStructers",new CoursePrivateState());

                    CountDownLatch l2 = new CountDownLatch(1);
                    CloseCourse.getResult().subscribe(() -> l2.countDown());
                    try {
                            l2.await();
                    } catch (InterruptedException e){}

                    pool.shutdown();
            }
        System.out.println("END OF TEST");
    }
}
