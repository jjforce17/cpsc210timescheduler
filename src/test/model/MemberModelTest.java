package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void testSetSpecificDay() {
        john.setSpecificDay(7, d8);
        assertEquals(john.getSpecificDay(7),d8);
    }

    @Test
    void testGetSpecificDay () {
        assertTrue(john.getSpecificDay(1).dayEquals(new DayScheduleModel()));
    }
}
