package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppUserTest {
    AppUser au;
    AppUser au2;

    @BeforeEach
    void runBefore() {
        au = new AppUser();
        au2 = new AppUser();
        au2.addToGroupList(new GroupModel("a","a","a"));
    }

    @Test
    void testAppUser() {
        assertEquals(0, au.getGroupsList().size());
    }

    @Test
    void testGetGroupsList() {
        assertEquals(0, au.getGroupsList().size());
        assertEquals(1, au2.getGroupsList().size());
    }

    @Test
    void testGetSett() {
        assertEquals(0, au.getGroupsList().size());
        assertEquals(1, au2.getGroupsList().size());
        au2.setGroupsList(new ArrayList<GroupModel>());
        assertEquals(0, au2.getGroupsList().size());
    }

    @Test
    void testAddToGroupList() {
        au.addToGroupList(new GroupModel("b","b","b"));
        assertEquals(1, au.getGroupsList().size());
    }

    @Test
    void testToJson() {
        assertEquals("{\"groupList\":[{\"groupName\":\"a\",\"memberList\":[],\"groupDesc\":\"a\",\"" +
                "avaibleTimes\":[],\"actTime1\":99999999,\"startDate\":\"a\"}]}", au2.toJson().toString());
    }
}
