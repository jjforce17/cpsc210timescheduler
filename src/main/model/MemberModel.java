package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;

//Class for member. Contains member name and schedule for the week.
// Has functions to edit the schedule of inidivudal day.
public class MemberModel implements Writable {
    private String name;
    private ArrayList<DayScheduleModel> memberSchedule;

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Initializes a member model with a list of 7 free day schedules.
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
        EventLog.getInstance().logEvent(new Event("New member created " + name));
    }

    //Getters and Setters
    public String getName() {
        return this.name;
    }

    public void setName(String n) {
        EventLog.getInstance().logEvent(new Event("Changing member name from " + name + " to " + n));
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
        EventLog.getInstance().logEvent(new Event("Changing schedule on day " + day + " for member "
                + this.name));
        this.memberSchedule.set(day - 1, daySched);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns the DaySchedule of day number specified in parameter.
    public DayScheduleModel getSpecificDay(int day) {
        //EventLog.getInstance().logEvent(new Event("Getting schedule on day " + day + " for member "
        //        + this.name));
        //This function shows what day is being received from which member, this is usally called when editing a
        //time schedule, so we can see which user and day it's from, but would take initialization a few hundred lines.
        return this.memberSchedule.get(day - 1);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Parses a member model object into a JSON Object.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("memberScheudle", schedToJson());
        json.put("name", name);
        return json;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: parses the list of day schedule models in memb memberSchedule into a JSON Array.
    private JSONArray schedToJson() {
        JSONArray jsonArray = new JSONArray();

        for (DayScheduleModel d : memberSchedule) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }
}
