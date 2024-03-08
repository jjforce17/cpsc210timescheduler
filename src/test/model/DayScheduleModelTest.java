package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DayScheduleModelTest {
    DayScheduleModel monday;
    @BeforeEach
    void runBeforeEach() {
        monday = new DayScheduleModel();
    }

    @Test
    void testGetAndSet () {
        Boolean[] testget1 = new Boolean[96];
        for (int i = 0; i < 96; i++) {
            testget1[i] = false;
        }
        Boolean daySchedSame1 = true;
        for (int i = 0; i < 96; i++) {
            if (monday.getScheduleArr()[i] != testget1[i]) {
                daySchedSame1 = false;
            }
        }
        assertTrue(daySchedSame1);
        Boolean[] testget2 = new Boolean[96];
        for (int i = 0; i < 96; i++) {
            testget2[i] = true;
        }
        Boolean[] testget3 = new Boolean[96];
        for (int i = 0; i < 96; i++) {
            testget3[i] = true;
        }
        monday.setScheduleArr(testget2);
        Boolean daySchedSame2 = true;
        for (int i = 0; i < 96; i++) {
            if (monday.getScheduleArr()[i] != testget3[i]) {
                daySchedSame2 = false;
            }
        }
        assertTrue(daySchedSame2);
    }

    @Test
    void testDayScheduleModel() {
        for (Boolean i: monday.getScheduleArr()) {
            assertEquals(false,i);
        }
    }
    @Test
    void testSetTimeSLot() {
        monday.setTimeSlot(0,15, true);
        assertTrue(monday.getTimeSlot(0,15));
        monday.setTimeSlot(0,15, false);
        assertFalse(monday.getTimeSlot(0,15));
    }

    @Test
    void testSetHourSLot() {
        monday.setHourSlot(4,0, true);
        assertFalse(monday.getTimeSlot(3,45));
        assertTrue(monday.getTimeSlot(4,0));
        assertTrue(monday.getTimeSlot(4,15));
        assertTrue(monday.getTimeSlot(4,30));
        assertTrue(monday.getTimeSlot(4,45));
        assertFalse(monday.getTimeSlot(5,0));
        monday.setHourSlot(8,30, true);
        assertFalse(monday.getTimeSlot(8,15));
        assertTrue(monday.getTimeSlot(8,30));
        assertTrue(monday.getTimeSlot(8,45));
        assertTrue(monday.getTimeSlot(9,0));
        assertTrue(monday.getTimeSlot(9,15));
        assertFalse(monday.getTimeSlot(9,30));
    }

    @Test
    void testGetTimeSLot() {
        assertFalse(monday.getTimeSlot(0,0));
        assertFalse(monday.getTimeSlot(23,45));
        assertFalse(monday.getTimeSlot(2,30));
        assertFalse(monday.getTimeSlot(15,15));
    }

    @Test
    void testSetNightBusy() {
        monday.setNightBusy();
        assertFalse(monday.getTimeSlot(20,45));
        assertTrue(monday.getTimeSlot(21,0));
        assertTrue(monday.getTimeSlot(22,15));
        assertTrue(monday.getTimeSlot(23,45));
    }

    @Test
    void testSetMorningBusy() {
        monday.setMorningBusy();
        assertTrue(monday.getTimeSlot(0,0));
        assertTrue(monday.getTimeSlot(8,15));
        assertTrue(monday.getTimeSlot(8,45));
        assertFalse(monday.getTimeSlot(9,0));
        assertFalse(monday.getTimeSlot(9,15));
    }

    @Test
    void testSetFullDayBusy() {
        monday.setFullDayBusy();
        assertTrue(monday.getTimeSlot(0,0));
        assertTrue(monday.getTimeSlot(7,30));
        assertTrue(monday.getTimeSlot(10,15));
        assertTrue(monday.getTimeSlot(23,45));
    }

    @Test
    void testDayEquals() {
        monday.setFullDayBusy();
        DayScheduleModel tuesday = new DayScheduleModel();
        tuesday.setFullDayBusy();
        assertTrue(monday.dayEquals(tuesday));
        assertFalse(monday.dayEquals(new DayScheduleModel()));
    }

    @Test
    void testToJson() {
        assertEquals("{\"scheduleArr\":[\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fals" +
                "e\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fals" +
                "e\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fal" +
                "se\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"fa" +
                "lse\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\"]}"
                , monday.toJson().toString());
    }
}