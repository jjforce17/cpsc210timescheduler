package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest{
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            AppUser au = new AppUser();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriter() {
        try {
            AppUser au = new AppUser();
            au.addToGroupList(new GroupModel("a", "a","a"));
            JsonWriter writer = new JsonWriter("./data/testWriteGroupsData.json");
            writer.open();
            writer.write(au);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriteGroupsData.json");
            au = reader.read();
            assertEquals("a", au.getGroupsList().get(0).getGroupName());
            assertEquals("a", au.getGroupsList().get(0).getStartDate());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}