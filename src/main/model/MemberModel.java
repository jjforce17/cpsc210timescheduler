package model;

import java.util.ArrayList;
import java.util.Arrays;

public class MemberModel {
    private String name;
//    private int memberId;
    private ArrayList<DayScheduleModel> memberSchedule;

    public MemberModel(String n) {
        this.name = n;
        DayScheduleModel d1 = new DayScheduleModel();
        DayScheduleModel d2 = new DayScheduleModel();
        DayScheduleModel d3 = new DayScheduleModel();
        DayScheduleModel d4 = new DayScheduleModel();
        DayScheduleModel d5 = new DayScheduleModel();
        DayScheduleModel d6 = new DayScheduleModel();
        DayScheduleModel d7 = new DayScheduleModel();
        this.memberSchedule = new ArrayList<DayScheduleModel>();
        memberSchedule.add(d1);
        memberSchedule.add(d2);
        memberSchedule.add(d3);
        memberSchedule.add(d4);
        memberSchedule.add(d5);
        memberSchedule.add(d6);
        memberSchedule.add(d7);
    }

    //Getters and Setters
    public String getName() {
        return this.name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public ArrayList<DayScheduleModel> getMemberSchedule() {
        return memberSchedule;
    }

    public void setMemberSchedule(ArrayList<DayScheduleModel> ms) {
        this.memberSchedule = ms;
    }

    //Methods
    //REQUIRES: 0 < day < 8
    //MODIFIES: this
    //EFFECTS: Finds the date selected in the parameter
    //Changes the schedule of specified day to one in parameter
    public void setSpecificDay(int day, DayScheduleModel daySched) {
        this.memberSchedule.set(day - 1, daySched);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns the DaySchedule of day number specified in parameter.
    public DayScheduleModel getSpecificDay(int day) {
        return this.memberSchedule.get(day - 1);
    }
    
}
