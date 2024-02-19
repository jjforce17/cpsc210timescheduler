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
}