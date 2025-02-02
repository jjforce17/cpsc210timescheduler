package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.metal.MetalComboBoxButton;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GroupModelTest {
    GroupModel g1;
    GroupModel g2;
    GroupModel g3;
    MemberModel m1;
    MemberModel m2;
    MemberModel m3;

    @BeforeEach
    void runBeforeEach() {
        g1 = new GroupModel("Group 1", "Not free", "Jan 1");
        g2= new GroupModel("Group 2", "3 am Free", "Jan 1");
        g3= new GroupModel("Group 3", "3 am Free", "Jan 1");
        m1 = new MemberModel("A");
        m2 = new MemberModel("B");
        m3 = new MemberModel("C");
        MemberModel m4 = new MemberModel("A");
        MemberModel m5 = new MemberModel("B");
        MemberModel m6 = new MemberModel("C");
        MemberModel m7 = new MemberModel("A");
        MemberModel m8 = new MemberModel("B");
        MemberModel m9 = new MemberModel("C");
        ArrayList<MemberModel> mlist1 = new ArrayList<MemberModel>();
        ArrayList<MemberModel> mlist2 = new ArrayList<MemberModel>();
        ArrayList<MemberModel> mlist3 = new ArrayList<MemberModel>();

        mlist1.add(m1);
        mlist1.add(m2);
        mlist1.add(m3);
        for (MemberModel m : mlist1) {
            for (int i = 1; i < 8; i++) {
                DayScheduleModel busyDay = new DayScheduleModel();
                busyDay.setFullDayBusy();
                m.setSpecificDay(i, busyDay);
            }
        }
        g1.setMemberList(mlist1);

        mlist2.add(m4);
        mlist2.add(m5);
        mlist2.add(m6);
        for (MemberModel m : mlist2) {
            for (int i = 1; i < 8; i++) {
                DayScheduleModel busyDay = new DayScheduleModel();
                busyDay.setFullDayBusy();
                m.setSpecificDay(i, busyDay);
            }
        }
        DayScheduleModel free745am = new DayScheduleModel();
        free745am.setFullDayBusy();
        free745am.setHourSlot(7,45,false);
        m4.setSpecificDay(3, free745am);
        m5.setSpecificDay(3, free745am);
        m6.setSpecificDay(3, free745am);
        g2.setMemberList(mlist2);

        mlist3.add(m7);
        mlist3.add(m8);
        mlist3.add(m9);
        for (MemberModel m : mlist3) {
            for (int i = 1; i < 8; i++) {
                DayScheduleModel busyDay = new DayScheduleModel();
                busyDay.setFullDayBusy();
                m.setSpecificDay(i, busyDay);
            }
        }
        DayScheduleModel free515pm = new DayScheduleModel();
        free515pm.setFullDayBusy();
        free515pm.setHourSlot(17,15,false);
        free515pm.setHourSlot(18,15,false);
        m7.setSpecificDay(5, free515pm);
        m8.setSpecificDay(1, free515pm);
        m9.setSpecificDay(2, free515pm);
        m7.setSpecificDay(2, free515pm);
        m8.setSpecificDay(5, free515pm);
        m9.setSpecificDay(1, free515pm);
        m7.setSpecificDay(6, free515pm);
        m8.setSpecificDay(2, free515pm);
        m9.setSpecificDay(5, free515pm);
        g3.setMemberList(mlist3);
    }

    @Test
    void testGetAndSet() {
        ArrayList<MemberModel> testGetML = new ArrayList<MemberModel>();
        testGetML.add(m1);
        testGetML.add(m2);
        testGetML.add(m3);
        assertEquals("Group 1", g1.getGroupName());
        assertEquals("Not free", g1.getGroupDesc());
        assertEquals("Jan 1", g1.getStartDate());
        assertEquals(new ArrayList<Integer>(), g1.getAvailableTimes());
        assertEquals(testGetML, g1.getMemberList());
        g1.setGroupName("");
        g1.setGroupDesc("");
        g1.setStartDate("");
        g1.setAvailableTimes(new ArrayList<Integer>());
        g1.setMemberList(new ArrayList<MemberModel>());
        g1.setActTime1(10);
        assertEquals(10, g1.getActTime1());
        assertEquals("", g1.getGroupName());
        assertEquals("", g1.getGroupDesc());
        assertEquals("", g1.getStartDate());
        assertEquals(new ArrayList<Integer>(), g1.getAvailableTimes());
        assertEquals(new ArrayList<MemberModel>(), g1.getMemberList());
    }

    @Test
    void testAddGroupMember() {
        MemberModel bobby = new MemberModel("Bobby");
        g1.addGroupMember(bobby);
        ArrayList<MemberModel> ml1 = new ArrayList<>();
        ml1.add(m1);
        ml1.add(m2);
        ml1.add(m3);
        ml1.add(bobby);
        assertEquals(ml1, g1.getMemberList());
    }

    @Test
    void testRemoveGroupMember() {
        assertEquals(1, g1.removeGroupMember("B"));
        assertEquals(-1, g1.removeGroupMember("cde"));
        ArrayList<MemberModel> ml1 = new ArrayList<>();
        ml1.add(m1);
        ml1.add(m3);
        assertEquals(ml1, g1.getMemberList());
    }

    @Test
    void testFindCommonSched() {
        ArrayList<Integer> seven54to845 = new ArrayList<>();
        seven54to845.add(30745);
        seven54to845.add(30800);
        seven54to845.add(30815);
        seven54to845.add(30830);
        ArrayList<Integer> multDays = new ArrayList<>();
        multDays.add(21715);
        multDays.add(21730);
        multDays.add(21745);
        multDays.add(21800);
        multDays.add(21815);
        multDays.add(21830);
        multDays.add(21845);
        multDays.add(21900);
        multDays.add(51715);
        multDays.add(51730);
        multDays.add(51745);
        multDays.add(51800);
        multDays.add(51815);
        multDays.add(51830);
        multDays.add(51845);
        multDays.add(51900);
        //Test full sched
        assertEquals(new ArrayList<Integer>(), g1.findCommonSched());
        //Test at day 3 7;45 free
        assertEquals(seven54to845, g2.findCommonSched());
        //Test at different days free
        assertEquals(multDays, g3.findCommonSched());
    }

    @Test
    void testMatchActivityLength() {
        ArrayList<ArrayList<Integer>> seven54to845 = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> min60val = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> g3test = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> slot1 = new ArrayList<Integer>(Arrays.asList(331, 332));
        ArrayList<Integer> slot2 = new ArrayList<Integer>(Arrays.asList(332, 333));
        ArrayList<Integer> slot3 = new ArrayList<Integer>(Arrays.asList(333, 334));
        ArrayList<Integer> min60 = new ArrayList<Integer>(Arrays.asList(331, 332, 333, 334));
        ArrayList<Integer> slot4 = new ArrayList<Integer>(Arrays.asList(269, 270, 271, 272, 273, 274, 275));
        ArrayList<Integer> slot5 = new ArrayList<Integer>(Arrays.asList(270, 271, 272, 273, 274, 275, 276));
        ArrayList<Integer> slot6 = new ArrayList<Integer>(Arrays.asList(569, 570, 571, 572, 573, 574, 575));
        ArrayList<Integer> slot7 = new ArrayList<Integer>(Arrays.asList(570, 571, 572, 573, 574, 575, 576));
        seven54to845.add(slot1);
        seven54to845.add(slot2);
        seven54to845.add(slot3);
        min60val.add(min60);
        g3test.add(slot4);
        g3test.add(slot5);
        g3test.add(slot6);
        g3test.add(slot7);
        g2.findCommonSched();
        g3.findCommonSched();
        assertEquals(seven54to845, g2.matchActivityLength(30));
        assertEquals(min60val, g2.matchActivityLength(60));
        assertEquals(new ArrayList<>(), g2.matchActivityLength(120));
        assertEquals(new ArrayList<>(), g1.matchActivityLength(120));
        assertEquals(g3test, g3.matchActivityLength(105));
        assertEquals(new ArrayList<>(), g3.matchActivityLength(9999999));
    }

    @Test
    void testStringAvailTimes() {
        g2.findCommonSched();
        ArrayList<String> stringTest
                = new ArrayList<String>(Arrays.asList("Meetup available at 7:45 until 8:15, "
                        + "2 day(s) from initialized date",
                "Meetup available at 8:00 until 8:30, "
                        + "2 day(s) from initialized date",
                "Meetup available at 8:15 until 8:45, "
                        + "2 day(s) from initialized date"));
        assertEquals(stringTest , g2.stringAvailTimes(g2.matchActivityLength(30)));
        g1.findCommonSched();
        assertEquals(new ArrayList<>(Arrays.asList("Unable for group meetup, please use different week.")) ,
                g1.stringAvailTimes(g1.matchActivityLength(30)));
        g3.findCommonSched();
        ArrayList<String> stringTest2
                = new ArrayList<String>(Arrays.asList("Meetup available at 17:15 until 19:00, "
                        + "1 day(s) from initialized date",
                "Meetup available at 17:30 until 19:15, "
                        + "1 day(s) from initialized date",
                "Meetup available at 17:15 until 19:00, "
                        + "4 day(s) from initialized date",
                "Meetup available at 17:30 until 19:15, "
                        + "4 day(s) from initialized date"));
        assertEquals(stringTest2 , g3.stringAvailTimes(g3.matchActivityLength(105)));
    }

    @Test
    void testPrintMember() {
        String testString = "A\n" +
                "Schedule data: \n" +
                "Day 1:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 2:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 3:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 4:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 5:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 6:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 7:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "B\n" +
                "Schedule data: \n" +
                "Day 1:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 2:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 3:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 4:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 5:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 6:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 7:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "C\n" +
                "Schedule data: \n" +
                "Day 1:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 2:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 3:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 4:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 5:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 6:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 7:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n";
        assertEquals(testString, g1.printGroupMembers());
        String testString2 = "A\n" +
                "Schedule data: \n" +
                "Day 1:\n" +
                "Free at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 2:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 3:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 4:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 5:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 6:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 7:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "B\n" +
                "Schedule data: \n" +
                "Day 1:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 2:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 3:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 4:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 5:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 6:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 7:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "C\n" +
                "Schedule data: \n" +
                "Day 1:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 2:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 3:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 4:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 5:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 6:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n" +
                "Day 7:\n" +
                "Busy at 00:00, Busy at 00:15, Busy at 00:30, Busy at 00:45, Busy at 1:00, Busy at 1:15, Busy at 1:30, Busy at 1:45, Busy at 2:00, Busy at 2:15, Busy at 2:30, Busy at 2:45, Busy at 3:00, Busy at 3:15, Busy at 3:30, Busy at 3:45, Busy at 4:00, Busy at 4:15, Busy at 4:30, Busy at 4:45, Busy at 5:00, Busy at 5:15, Busy at 5:30, Busy at 5:45, Busy at 6:00, Busy at 6:15, Busy at 6:30, Busy at 6:45, Busy at 7:00, Busy at 7:15, Busy at 7:30, Busy at 7:45, Busy at 8:00, Busy at 8:15, Busy at 8:30, Busy at 8:45, Busy at 9:00, Busy at 9:15, Busy at 9:30, Busy at 9:45, Busy at 10:00, Busy at 10:15, Busy at 10:30, Busy at 10:45, Busy at 11:00, Busy at 11:15, Busy at 11:30, Busy at 11:45, Busy at 12:00, Busy at 12:15, Busy at 12:30, Busy at 12:45, Busy at 13:00, Busy at 13:15, Busy at 13:30, Busy at 13:45, Busy at 14:00, Busy at 14:15, Busy at 14:30, Busy at 14:45, Busy at 15:00, Busy at 15:15, Busy at 15:30, Busy at 15:45, Busy at 16:00, Busy at 16:15, Busy at 16:30, Busy at 16:45, Busy at 17:00, Busy at 17:15, Busy at 17:30, Busy at 17:45, Busy at 18:00, Busy at 18:15, Busy at 18:30, Busy at 18:45, Busy at 19:00, Busy at 19:15, Busy at 19:30, Busy at 19:45, Busy at 20:00, Busy at 20:15, Busy at 20:30, Busy at 20:45, Busy at 21:00, Busy at 21:15, Busy at 21:30, Busy at 21:45, Busy at 22:00, Busy at 22:15, Busy at 22:30, Busy at 22:45, Busy at 23:00, Busy at 23:15, Busy at 23:30, Busy at 23:45, \n";
        g1.getMemberList().get(0).getMemberSchedule().get(0).setTimeSlot(0,0,false);
        assertEquals(testString2, g1.printGroupMembers());
    }

    @Test
    void testJsonObject() {
        ArrayList<Integer> availTimes = new ArrayList<>();
        availTimes.add(1);
        availTimes.add(2);
        g1.setAvailableTimes(availTimes);
        assertEquals("{\"groupName\":\"Group 1\",\"memberList\":[{\"memberScheudle\":[{\"schedu" +
                "leArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\"]},{\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"]},{\"scheduleArr\":[\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\"]},{\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"]},{\"scheduleArr\":[\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\"]},{\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\"]},{\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"]}],\"name\":\"A\"},{\"" +
                "memberScheudle\":[{\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"]},{" +
                "\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"]},{\"scheduleArr" +
                "\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"]},{\"scheduleArr\":[\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\"]},{\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"]},{\"scheduleArr\":[\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\"]},{\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\"]}],\"name\":\"B\"},{\"memberScheudle\":[{\"scheduleArr\":[\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\"]},{\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"]},{\"scheduleArr\":[" +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\"]},{\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"" +
                ",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"]},{\"scheduleArr\":[" +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true" +
                "\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"," +
                "\"true\",\"true\",\"true\"]},{\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"" +
                "true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\"]},{\"scheduleArr\":[\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"t" +
                "rue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tr" +
                "ue\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"tru" +
                "e\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\",\"true\"]}],\"name\":\"C" +
                "\"}],\"groupDesc\":\"Not free\",\"avaibleTimes\":[1,2],\"actTime1\":99999999,\"startDate\":" +
                "\"Jan 1\"}", g1.toJson().toString());
    }
}