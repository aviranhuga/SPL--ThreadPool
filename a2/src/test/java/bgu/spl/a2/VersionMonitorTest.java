package bgu.spl.a2;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class VersionMonitorTest {

    @Test
    public void getVersion() {
        try {//try for every Exception in the Test
            VersionMonitor p = new VersionMonitor();// create new VersionMonitor
            assertEquals(0,p.getVersion());
        }
        catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void inc() {
        try {//try for every Exception in the Test
            VersionMonitor p = new VersionMonitor();// create new VersionMonitor
            assertEquals(0,p.getVersion());
            p.inc();
            assertEquals(1,p.getVersion());
            p.inc();
            p.inc();
            p.inc();
            assertEquals(4,p.getVersion());
        }
        catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void await() {
        try {//try for every Exception in the Test
            VersionMonitor p = new VersionMonitor();// create new VersionMonitor
            p.inc();
        }
        catch (Exception ex) {
            Assert.fail();
        }
    }
}