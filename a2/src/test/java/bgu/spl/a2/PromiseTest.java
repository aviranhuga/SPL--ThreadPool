package bgu.spl.a2;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PromiseTest {

    @Test
    public void get() {
        try {//try for every Exception in the Test
            Promise<Integer> p = new Promise<Integer>();// create new promise
            try{
                p.get();
            }
            catch (IllegalStateException ex){// its suppose to throw this Exception
                p.resolve(5);
                int x = p.get();
                assertEquals(x,5); // check if get is p value is 5
                int y = p.get();
                assertEquals(x,y);
            }
            catch (Exception ex){// not the right Exception throw
                Assert.fail();
            }
            Promise<String> p1 = new Promise<String>(); // Check for string
            try{
                p1.get();
            }
            catch (IllegalStateException ex){// its suppose to throw this Exception
                p1.resolve("answer");
                String s1 = p1.get();
                assertEquals(s1,"answer"); // check if get is p value is 5
                String s2 = p1.get();
                assertEquals(s1,s2);
            }
            catch (Exception ex){// not the right Exception throw
                Assert.fail();
            }
        }
        catch (Exception ex) {//catch any try
            Assert.fail();
        }
    }

    @Test
    public void isResolved() {
        try {//try for every Exception in the Test
            Promise<Integer> p = new Promise<Integer>();// create new promise
            assertFalse(p.isResolved());
            p.resolve(0);
            assertTrue(p.isResolved());
        }
        catch (Exception ex) {//catch any try
            Assert.fail();
        }
    }

    @Test
    public void resolve() {
        try{
            Integer someNumber = 5;
            //test for adding 2 resolve
            Promise<Integer> p = new Promise<Integer>();// create new promise
            p.resolve(someNumber);// set resolve value 5
            try{
                p.resolve(6);// wouldn't work , cant enter 2 resolves
                System.out.println("Can't resolve more then one time!");
                Assert.fail();
            }
            catch (IllegalStateException ex){
                int x = p.get();
                assertEquals(x,5); // check if get is p value is 5
            }
            catch (Exception ex){
                Assert.fail();
            }
            //test if the callback happened
            p = new Promise<Integer>();
            //add dummy callbacks
            callback callback = new DummyCallback();
            p.subscribe(callback);
            assertFalse(((DummyCallback)callback).wasCalled);
            // runs resolve
            p.resolve(someNumber);
            // test callback runs
            assertTrue(((DummyCallback)callback).wasCalled);
        }
        catch (Exception ex){
            System.out.println("Exception " + ex.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void subscribe() {
        try {//try for every Exception in the Test
            Integer someNumber = 5;
            Promise<Integer> p = new Promise<Integer>();// create new promise
            // Creates dummy callback and subscribes it
            callback callback = new DummyCallback();
            p.subscribe(callback);
            //test that the callback didnt happened
            assertFalse(((DummyCallback)callback).wasCalled);
            // runs resolve
            p.resolve(someNumber);
            // test callback runs
            assertTrue(((DummyCallback)callback).wasCalled);
            //subscribe after resolve run
            callback callback2 = new DummyCallback();
            p.subscribe(callback2);
            assertTrue(((DummyCallback)callback).wasCalled);

        }
        catch (Exception ex) {
            Assert.fail();
        }
    }
}