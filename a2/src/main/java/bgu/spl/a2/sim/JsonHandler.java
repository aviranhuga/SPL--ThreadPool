package bgu.spl.a2.sim;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.actions.*;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class JsonHandler {

    private String path;
    private JsonElement jsonElement;
    private JsonObject jsonObject;
    private Warehouse warehouse;

    public JsonHandler(String path) {
        this.path = path;
        try {
            JsonParser parser = new JsonParser();
            this.jsonElement = parser.parse(new FileReader(path));
            this.jsonObject = jsonElement.getAsJsonObject();
        } catch (FileNotFoundException e) {
            System.out.println("File didn't found in : " + path);
        }
    }

    public Warehouse buildWarehouse(){
        JsonArray ComputerArray = this.jsonObject.getAsJsonArray("Computers");
        HashMap<String,Computer> computers = new HashMap<>();
        for (JsonElement i:ComputerArray){
            String computerType = i.getAsJsonObject().get("Type").getAsString();
            long failSig = i.getAsJsonObject().get("Sig  Fail").getAsLong();
            long successSig = i.getAsJsonObject().get("Sig Success").getAsLong();
            Computer computer = new Computer(computerType);
            computer.setSig(failSig,successSig);
            computers.put(computerType,computer);
        }
        this.warehouse = new Warehouse(computers);
        return this.warehouse;
    }

    public ActorThreadPool buildActorThreadPool(){
        int Threads = jsonObject.get("threads").getAsInt();
        return new ActorThreadPool(Threads);
    }

    public ActionsList phaseActions(String PhaseName){
        JsonArray actions = jsonObject.getAsJsonArray(PhaseName);
        LinkedList<Action<?>> actionsList = new LinkedList<>();
        LinkedList<PrivateState> privateStates = new LinkedList<>();
        LinkedList<String> actorIds = new LinkedList<>();
        for(JsonElement i:actions){
            JsonObject action = i.getAsJsonObject();
            String actionName = action.get("Action").getAsString();
            switch (actionName){
                case "Open Course": {
                    String department = action.get("Department").getAsString();
                    String course = action.get("Course").getAsString();
                    int Space = action.get("Space").getAsInt();
                    LinkedList<String> pre = new LinkedList<>();
                    Iterator<JsonElement> It = action.get("Prerequisites").getAsJsonArray().iterator();
                    while (It.hasNext())
                        pre.add(It.next().getAsString());
                    Action<Boolean> newaction = new OpenANewCourse(Space, pre, course);
                    actionsList.add(newaction);
                    actorIds.add(department);
                    privateStates.add(new DepartmentPrivateState());
                }break;
                case "Add Student": {
                    String department = action.get("Department").getAsString();
                    String student = action.get("Student").getAsString();
                    Action<Boolean> newaction = new AddStudent(student);
                    actionsList.add(newaction);
                    actorIds.add(department);
                    privateStates.add(new DepartmentPrivateState());
                }break;
                case "Participate In Course":{
                    String student = action.get("Student").getAsString();
                    String course = action.get("Course").getAsString();
                    String gradeString = action.get("Grade").getAsJsonArray().get(0).getAsString();
                    int grade;
                    if (gradeString.charAt(0)==45)grade=-1;
                    else grade = action.get("Grade").getAsJsonArray().get(0).getAsInt();
                    Action<Boolean> newaction = new ParticipatingInCourse(student,grade);
                    actionsList.add(newaction);
                    actorIds.add(course);
                    privateStates.add(new CoursePrivateState());
                }break;
                case "Unregister":{
                    String student = action.get("Student").getAsString();
                    String course = action.get("Course").getAsString();
                    Action<Boolean> newaction = new Unregister(student);
                    actionsList.add(newaction);
                    actorIds.add(course);
                    privateStates.add(new CoursePrivateState());
                }break;
                case "Close Course": {
                    String department = action.get("Department").getAsString();
                    String course = action.get("Course").getAsString();
                    Action<Boolean> newaction = new CloseACourse(course);
                    actionsList.add(newaction);
                    actorIds.add(department);
                    privateStates.add(new DepartmentPrivateState());
                }break;
                case "Add Spaces": {
                    String course = action.get("Course").getAsString();
                    int number = action.get("Number").getAsInt();
                    Action<Boolean> newaction = new OpeningNewPlacesInACourse(number);
                    actionsList.add(newaction);
                    actorIds.add(course);
                    privateStates.add(new CoursePrivateState());
                }break;
                case "Administrative Check":{
                    String department = action.get("Department").getAsString();
                    String computer = action.get("Computer").getAsString();
                    LinkedList<String> students = new LinkedList<>();
                    Iterator<JsonElement> It = action.get("Students").getAsJsonArray().iterator();
                    while (It.hasNext())
                        students.add(It.next().getAsString());
                    LinkedList<String> conditions = new LinkedList<>();
                    It = action.get("Conditions").getAsJsonArray().iterator();
                    while (It.hasNext())
                        conditions.add(It.next().getAsString());
                    Action<Boolean> newaction = new CheckAdministrativeObligations(students,conditions,computer,this.warehouse);
                    actionsList.add(newaction);
                    actorIds.add(department);
                    privateStates.add(new DepartmentPrivateState());
                }break;
                case "Register With Preferences":{
                    String student = action.get("Student").getAsString();
                    LinkedList<String> preferences = new LinkedList<>();
                    Iterator<JsonElement> It = action.get("Preferences").getAsJsonArray().iterator();
                    while (It.hasNext())
                        preferences.add(It.next().getAsString());

                    LinkedList<Integer> grades = new LinkedList<>();
                    It = action.get("Grade").getAsJsonArray().iterator();
                    while (It.hasNext())
                        grades.add(new Integer(It.next().getAsInt()));
                    Action<Boolean> newaction = new RegisterWithPreferences(preferences,grades);
                    actionsList.add(newaction);
                    actorIds.add(student);
                    privateStates.add(new StudentPrivateState());
                }break;
            }
        }//end of for
        return new ActionsList(actionsList,actorIds,privateStates);
    }
}





