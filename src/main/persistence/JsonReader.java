package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

//CODE MOSSTLY FROM https://github.com/stleary/JSON-java
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //MODIFY THIS!!!!!!!
    //
    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AppUser read() throws IOException {
        EventLog.getInstance().logEvent(new Event("Reading from file."));
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAppUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses appUser from JSON object and return it.
    private AppUser parseAppUser(JSONObject objVal) {
        AppUser au = new AppUser();
        addGroups(au, objVal);
        return au;
    }

    // EFFECTS: call addGroup() for eveytime time a group is detected in the JSON object.
    private void addGroups(AppUser au, JSONObject objVal) {
        JSONArray jsonArray = objVal.getJSONArray("groupList");
        for (Object json : jsonArray) {
            JSONObject nextGroup = (JSONObject) json;
            addGroup(au, nextGroup);
        }
    }

    // MODIFIES: au
    // EFFECTS: parses group data, adds group to au.
    private void addGroup(AppUser au, JSONObject curGroup) {
        String groupName = curGroup.getString("groupName");
        String groupDesc = curGroup.getString("groupDesc");
        String startDate = curGroup.getString("startDate");
        GroupModel newGroup = new GroupModel(groupName, groupDesc, startDate);
        int actTime1 = curGroup.getInt("actTime1");
        newGroup.setActTime1(actTime1);
        JSONArray availableTimes = curGroup.getJSONArray("avaibleTimes");
        parseAvailTimes(newGroup, availableTimes);
        JSONArray memberList = curGroup.getJSONArray("memberList");
        addMembers(newGroup, memberList);
        au.addToGroupList(newGroup);
    }

    // MODIFIES : g
    // EFFECTS: parses int array of available time and assigns it to g.
    private void parseAvailTimes(GroupModel g, JSONArray atArray) {
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        for (int i = 0; i < atArray.length(); i++) {
            tempList.add(atArray.getInt(i));
        }
        g.setAvailableTimes(tempList);
    }

    // MODIFIES : g
    // EFFECTS: parses list of memebers and assigns it to g.
    private void addMembers(GroupModel g, JSONArray memList) {
        ArrayList<MemberModel> tempList = new ArrayList<MemberModel>();
        for (int i = 0; i < memList.length(); i++) {
            JSONObject nextMember = memList.getJSONObject(i);
            tempList.add(parseMember(nextMember));
        }
        g.setMemberList(tempList);
    }

    // EFFECTS: parses the member object from the JSON objects and returns it.
    private MemberModel parseMember(JSONObject memObj) {
        String name = memObj.getString("name");
        JSONArray memSched = memObj.getJSONArray("memberScheudle");
        MemberModel m = new MemberModel(name);
        m.setMemberSchedule(parseMemberSched(memSched));
        return m;
    }

    // EFFECTS: parses schedules of memebers and returns it.
    private ArrayList<DayScheduleModel> parseMemberSched(JSONArray memSched) {
        ArrayList<DayScheduleModel> memSchedule = new ArrayList<DayScheduleModel>();
        for (int i = 0; i < memSched.length(); i++) {
            JSONObject scheduleArrObj = memSched.getJSONObject(i);
            JSONArray scheduleArr = scheduleArrObj.getJSONArray("scheduleArr");
            memSchedule.add(parseSchedArr(scheduleArr));
        }
        return memSchedule;
    }

    // EFFECTS: parses daily schedule of a member and returns it.
    private DayScheduleModel parseSchedArr(JSONArray schedArr) {
        DayScheduleModel dsm = new DayScheduleModel();
        Boolean[] tempBool = new Boolean[96];
        for (int i = 0; i < schedArr.length(); i++) {
            String boolString = schedArr.getString(i);
            Boolean boolVal = Boolean.parseBoolean(boolString);
            tempBool[i] = boolVal;
        }
        dsm.setScheduleArr(tempBool);
        return dsm;
    }
}
