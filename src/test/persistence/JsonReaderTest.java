package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//TEST FROM https://github.com/stleary/JSON-java
class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AppUser au = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderAppUser() {
        JsonReader reader = new JsonReader("./data/groupsData.json");
        try {
            AppUser au = reader.read();
            assertEquals("test 1 rereedit", au.getGroupsList().get(0).getGroupName());
            assertEquals("rereedit date", au.getGroupsList().get(0).getStartDate());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOtherGroup() {
        JsonReader reader = new JsonReader("./data/groupsData.json");
        try {
            AppUser au = reader.read();
            assertEquals("Group 2", au.getGroupsList().get(1).getGroupName());
            assertEquals("2/2/2", au.getGroupsList().get(1).getStartDate());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}