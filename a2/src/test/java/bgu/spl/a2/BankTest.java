package bgu.spl.a2;

import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.actions.*;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.HashMap;
import java.util.LinkedList;
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

                    Action<Boolean> OpenCourse0 = new OpenANewCourse(15 ,new Vector<String>(), "CS");
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
                    Action<Boolean> AddStudent6 = new AddStudent("CS");
                    Action<Boolean> AddStudent7 = new AddStudent("CS");
                    Action<Boolean> AddStudent8 = new AddStudent("CS");
                    Action<Boolean> AddStudent9 = new AddStudent("CS");
                    Action<Boolean> AddStudent10 = new AddStudent("CS");
                    Action<Boolean> AddStudent11 = new AddStudent("CS");

                    pool.submit(AddStudent0, "Tom", new StudentPrivateState());
                    pool.submit(AddStudent1, "Dor", new StudentPrivateState());
                    pool.submit(AddStudent2, "Amit", new StudentPrivateState());
                    pool.submit(AddStudent3, "Sasha", new StudentPrivateState());
                    pool.submit(AddStudent4, "Shir1", new StudentPrivateState());
                    pool.submit(AddStudent5, "Shir2", new StudentPrivateState());
                    pool.submit(AddStudent6, "Aviran", new StudentPrivateState());
                    pool.submit(AddStudent7, "Biran", new StudentPrivateState());
                    pool.submit(AddStudent8, "Hoze", new StudentPrivateState());
                    pool.submit(AddStudent9, "Bibi", new StudentPrivateState());
                    pool.submit(AddStudent10, "Yair", new StudentPrivateState());
                    pool.submit(AddStudent11, "David", new StudentPrivateState());

                    Action<Boolean> PartInCourse0 = new ParticipatingInCourse("DataStructers", 100);
                    Action<Boolean> PartInCourse1 = new ParticipatingInCourse("DataStructers", 88);
                    Action<Boolean> PartInCourse2 = new ParticipatingInCourse("DataStructers", 75);
                    Action<Boolean> PartInCourse3 = new ParticipatingInCourse("DataStructers", 87);
                    Action<Boolean> PartInCourse4 = new ParticipatingInCourse("DataStructers", 43);
                    Action<Boolean> PartInCourse5 = new ParticipatingInCourse("DataStructers", 30);
                    Action<Boolean> PartInCourse6 = new ParticipatingInCourse("DataStructers", 100);
                    Action<Boolean> PartInCourse7 = new ParticipatingInCourse("DataStructers", 88);
                    Action<Boolean> PartInCourse8 = new ParticipatingInCourse("DataStructers", 75);
                    Action<Boolean> PartInCourse9 = new ParticipatingInCourse("DataStructers", 87);
                    Action<Boolean> PartInCourse10 = new ParticipatingInCourse("DataStructers", 43);
                    Action<Boolean> PartInCourse11 = new ParticipatingInCourse("DataStructers", 30);

                    pool.submit(PartInCourse0, "Tom", new StudentPrivateState());
                    pool.submit(PartInCourse1, "Dor", new StudentPrivateState());
                    pool.submit(PartInCourse2, "Amit", new StudentPrivateState());
                    pool.submit(PartInCourse3, "Sasha", new StudentPrivateState());
                    pool.submit(PartInCourse4, "Shir1", new StudentPrivateState());
                    pool.submit(PartInCourse5, "Shir2", new StudentPrivateState());
                    pool.submit(PartInCourse6, "Aviran", new StudentPrivateState());
                    pool.submit(PartInCourse7, "Biran", new StudentPrivateState());
                    pool.submit(PartInCourse8, "Bibi", new StudentPrivateState());
                    pool.submit(PartInCourse9, "David", new StudentPrivateState());
                    pool.submit(PartInCourse10, "Hoze", new StudentPrivateState());
                    pool.submit(PartInCourse11, "Yair", new StudentPrivateState());

                    Action<Boolean> AddNewPlace = new OpeningNewPlacesInACourse(30,"DataStructers");

                    pool.submit(AddNewPlace,"DataStructers",new CoursePrivateState());

                    CountDownLatch l = new CountDownLatch(25);
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
                    AddStudent6.getResult().subscribe(() -> l.countDown());
                    AddStudent7.getResult().subscribe(() -> l.countDown());
                    AddStudent8.getResult().subscribe(() -> l.countDown());
                    AddStudent9.getResult().subscribe(() -> l.countDown());
                    AddStudent10.getResult().subscribe(() -> l.countDown());
                    AddStudent11.getResult().subscribe(() -> l.countDown());
                    PartInCourse6.getResult().subscribe(() -> l.countDown());
                    PartInCourse7.getResult().subscribe(() -> l.countDown());
                    PartInCourse8.getResult().subscribe(() -> l.countDown());
                    PartInCourse9.getResult().subscribe(() -> l.countDown());
                    PartInCourse10.getResult().subscribe(() -> l.countDown());
                    PartInCourse11.getResult().subscribe(() -> l.countDown());
                    AddNewPlace.getResult().subscribe(() -> l.countDown());
                    try {
                            l.await();
                    } catch (InterruptedException e) {
                    }

                    HashMap<String,Computer> computers = new HashMap<>();
                    computers.put("A",new Computer("A"));
                    computers.put("B",new Computer("B"));
                    computers.put("C",new Computer("C"));
                    computers.get("A").setSig(-1,1);
                    computers.get("B").setSig(-2,2);
                    computers.get("C").setSig(-3,3);

                    Warehouse warehouse = new Warehouse(computers);

                    LinkedList<String> Students0 = new LinkedList<>();
                    Students0.add("Tom");
                    Students0.add("Shir1");
                    LinkedList<String> Cond0 = new LinkedList<>();
                    Cond0.add("DataStructers");

                    Action<Boolean> Check0 = new CheckAdministrativeObligations(Students0,Cond0,"A",warehouse);

                    LinkedList<String> Students1 = new LinkedList<>();
                    Students1.add("Amit");
                    Students1.add("Dor");
                    LinkedList<String> Cond1 = new LinkedList<>();
                    Cond1.add("DataStructers");

                    Action<Boolean> Check1 = new CheckAdministrativeObligations(Students1,Cond1,"A",warehouse);

                    LinkedList<String> Students2 = new LinkedList<>();
                    Students2.add("Sasha");
                    Students2.add("Shir2");
                    LinkedList<String> Cond2 = new LinkedList<>();
                    Cond2.add("DataStructers");

                    Action<Boolean> Check2 = new CheckAdministrativeObligations(Students2,Cond2,"A",warehouse);


                    LinkedList<String> Students3 = new LinkedList<>();
                    Students3.add("Aviran");
                    Students3.add("Hoze");
                    LinkedList<String> Cond3 = new LinkedList<>();
                    Cond3.add("DataStructers");

                    Action<Boolean> Check3 = new CheckAdministrativeObligations(Students3,Cond3,"A",warehouse);

                    LinkedList<String> Students4 = new LinkedList<>();
                    Students4.add("Bibi");
                    Students4.add("Yair");
                    LinkedList<String> Cond4 = new LinkedList<>();
                    Cond4.add("DataStructers");

                    Action<Boolean> Check4 = new CheckAdministrativeObligations(Students4,Cond4,"B",warehouse);

                    LinkedList<String> Students5 = new LinkedList<>();
                    Students5.add("David");
                    Students5.add("Biran");
                    LinkedList<String> Cond5 = new LinkedList<>();
                    Cond5.add("DataStructers");

                    Action<Boolean> Check5 = new CheckAdministrativeObligations(Students5,Cond5,"A",warehouse);

                    pool.submit(Check0,"CS", new DepartmentPrivateState());
                    pool.submit(Check4,"CS4", new DepartmentPrivateState());
                    pool.submit(Check5,"CS5", new DepartmentPrivateState());

                    CountDownLatch l3 = new CountDownLatch(3);
                    Check0.getResult().subscribe(() -> l3.countDown());
                    Check4.getResult().subscribe(() -> l3.countDown());
                    Check5.getResult().subscribe(() -> l3.countDown());
                    try {
                            l3.await();
                    } catch (InterruptedException e) {
                    }

                    pool.submit(Check1,"CS0", new DepartmentPrivateState());
                    pool.submit(Check2,"CS1", new DepartmentPrivateState());
                    pool.submit(Check3,"CS3", new DepartmentPrivateState());

                    CountDownLatch l4 = new CountDownLatch(3);
                    Check1.getResult().subscribe(() -> l4.countDown());
                    Check2.getResult().subscribe(() -> l4.countDown());
                    Check3.getResult().subscribe(() -> l4.countDown());
                    try {
                            l4.await();
                    } catch (InterruptedException e) {
                    }

                    pool.shutdown();
            }
        System.out.println("END OF TEST");
    }
}
