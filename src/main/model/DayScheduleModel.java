package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

//Class for schedule of a day. Stores the array of schedule slots and functions to modify it.
public class DayScheduleModel implements Writable {
    private Boolean[] scheduleArr;

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: initializes a day schedule where all the slots are free.
    public DayScheduleModel() {
        //Creates blank schedule of 96 15 minute slots for 24 horus of the day,
        //false means time is free.
        //NOTE: slot 0 represent 00:00 to 00:15 time slot 1, this.scheduelArr[0]
        //final slot/slot96/this.scheduleArr[95] represent 23:45 to 00:00
        //Represent time in 24hr.
        this.scheduleArr = new Boolean[96];
        for (int i = 0; i < 96; i++) {
            this.scheduleArr[i] = false;
        }
    }

    //Getters and Setters
    public Boolean[] getScheduleArr() {
        return this.scheduleArr;
    }

    public void setScheduleArr(Boolean[] nsa) {
        EventLog.getInstance().logEvent(new Event("Changing user time slot to " + nsa.toString()));
        //Replace to string to toJson to show whic data, will be super long so opt to use to string to show it is
        //actually being changed to a new object.
        this.scheduleArr = nsa;
    }

    //Methods
    //REQUIRES: 0 <= timeHR <= 23, tiemMin = 0,15,30,45
    //MODIFIES: this
    //EFFECTS: Finds the time slots which the hour specifies,
    //Sets the time slot to specified status
    //timeHr and timeMin represent the start of the time slot, so 07,15 means time slot 07:15 to 07:30
    public void setTimeSlot(int timeHr, int timeMin, Boolean status) {
        EventLog.getInstance().logEvent(new Event("Changing user time slot for " + String.valueOf(timeHr) + ":"
                                        + String.valueOf(timeMin) + " to " + String.valueOf(status)));
        int slotNum = (timeHr * 60 + timeMin) / 15;
        this.scheduleArr[slotNum] = status;
    }

    //REQUIRES: 0 <= timeHR <= 23, tiemMin = 0,15,30,45
    //MODIFIES: this
    //EFFECTS: Finds the time slots which the hour specifies,
    //Sets the time slot and the next 3 slots to status.
    public void setHourSlot(int timeHr, int timeMin, Boolean status) {
        EventLog.getInstance().logEvent(new Event("Changing user hour slot for " + String.valueOf(timeHr) + ":"
                + String.valueOf(timeMin) + " to " + String.valueOf(status)));
        int slotNum = (timeHr * 60 + timeMin) / 15;
        for (int i = slotNum; i <= slotNum + 3; i++) {
            this.scheduleArr[i] = status;
        }
    }

    //REQUIRES: 0 <= timeHR <= 23, tiemMin = 0,15,30,45
    //MODIFIES:
    //EFFECTS: returns value of the status in said time slot
    public Boolean getTimeSlot(int timeHr, int timeMin) {
        int slotNum = (timeHr * 60 + timeMin) / 15;
        return this.scheduleArr[slotNum];
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: Set time from 9:00pm to midnight as busy
    public void setNightBusy() {
        //EventLog.getInstance().logEvent(new Event("Setting night schedule to busy"));
        for (int i =  84; i <= 95; i++) {
            this.scheduleArr[i] = true;
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: Set time from 9:00pm to midnight as busy
    public void setMorningBusy() {
        //EventLog.getInstance().logEvent(new Event("Setting morning scheudle to busy"));
        for (int i =  0; i <= 35; i++) {
            this.scheduleArr[i] = true;
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets full day as busy
    public void setFullDayBusy() {
        //EventLog.getInstance().logEvent(new Event("Setting full day schedule to busy"));
        for (int i = 0; i < 96; i++) {
            this.scheduleArr[i] = true;
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets full day as free
    public void setFullDayFree() {
        //EventLog.getInstance().logEvent(new Event("Setting full day schedule to free"));
        for (int i = 0; i < 96; i++) {
            this.scheduleArr[i] = false;
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: Compares current dayScheudleModel has same schedule parameter.
    //Return true if same, false is not equal.
    public Boolean dayEquals(DayScheduleModel d) {
        for (int i = 0; i < 96; i++) {
            if (!(d.getScheduleArr()[i] == this.scheduleArr[i])) {
                return false;
            }
        }
        return true;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: parses a scheduler arr object into a JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("scheduleArr", schedBoolToJson());
        return json;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: parses the values in scheduleArr into a JSON Array
    private JSONArray schedBoolToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Boolean b : scheduleArr) {
            jsonArray.put(b.toString());
        }

        return jsonArray;
    }
}
