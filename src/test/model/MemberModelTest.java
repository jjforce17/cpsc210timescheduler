package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MemberModelTest {
    MemberModel john;
    DayScheduleModel d1;
    DayScheduleModel d2;
    DayScheduleModel d8;

    @BeforeEach
    void runBeforeEach() {
        john = new MemberModel("John");
        d1 = new DayScheduleModel();
        d2 = new DayScheduleModel();
        d8 = new DayScheduleModel();
        d8.setFullDayBusy();
    }

    @Test
    void testGetAndSet() {
        assertEquals("John", john.getName());
        Boolean johnSchedSame = true;
        for (DayScheduleModel d : john.getMemberSchedule()) {
            if (!d.dayEquals(new DayScheduleModel())) {
                johnSchedSame = false;
            }
        }
        assertTrue(johnSchedSame);
        john.setName("");
        john.setMemberSchedule(new ArrayList<DayScheduleModel>(Arrays.asList(d1,
                d2,
                new DayScheduleModel(),
                new DayScheduleModel(),
                new DayScheduleModel(),
                new DayScheduleModel(),
                new DayScheduleModel())) );
        assertEquals("", john.getName());
        Boolean johnSchedSame2 = true;
        for (DayScheduleModel d : john.getMemberSchedule()) {
            if (!d.dayEquals(new DayScheduleModel())) {
                johnSchedSame2 = false;
            }
        }
        assertTrue(johnSchedSame2);
    }
    @Test
    void testSetSpecificDay() {
        john.setSpecificDay(7, d8);
        assertEquals(john.getSpecificDay(7),d8);
    }

    @Test
    void testGetSpecificDay() {
        assertTrue(john.getSpecificDay(1).dayEquals(new DayScheduleModel()));
    }

    @Test
    void testToJson() {
        assertEquals("{\"memberScheudle\":[{\"scheduleArr\":[\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fals" +
                "e\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fals" +
                "e\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false" +
                "\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"" +
                ",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\"]},{\"scheduleArr\":[\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fals" +
                "e\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"" +
                ",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fals" +
                "e\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"" +
                ",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fals" +
                "e\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"]},{\"scheduleArr\":[\"false\"" +
                ",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fals" +
                "e\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fals" +
                "e\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\"]},{\"scheduleArr\":[\"false\",\"false\",\"f" +
                "alse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fals" +
                "e\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"" +
                ",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\"]},{\"scheduleArr\":[\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"" +
                ",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"," +
                "\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fals" +
                "e\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"" +
                ",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"" +
                "false\",\"false\",\"false\",\"false\",\"false\"]},{\"scheduleArr\":[\"false\",\"false\",\"false\"" +
                ",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"," +
                "\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"," +
                "\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"," +
                "\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"," +
                "\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"]},{\"scheduleArr\":[" +
                "\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"," +
                "\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"," +
                "\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"," +
                "\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"," +
                "\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"," +
                "\"false\"]}],\"name\":\"John\"}",john.toJson().toString());
    }
}
