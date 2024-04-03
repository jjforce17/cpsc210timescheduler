package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class AppUser implements Writable {

    private ArrayList<GroupModel> groupsList;

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Initializes a new app user with now group lists.
    public AppUser() {
        groupsList = new ArrayList<GroupModel>();
        EventLog.getInstance().logEvent(new Event("New app user initialized."));
    }

    //getters and setters
    public void setGroupsList(ArrayList<GroupModel> glist) {
        groupsList = glist;
    }

    public ArrayList<GroupModel> getGroupsList() {
        return groupsList;
    }

    //Methods
    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Adds a group model to the groupList
    public void addToGroupList(GroupModel g) {
        groupsList.add(g);
        EventLog.getInstance().logEvent(new Event("New group added to list."));
    }

    public void removeGroupFromList(GroupModel g) {
        EventLog.getInstance().logEvent(new Event("Group " + g.getGroupName() + " removed from user list."));
        groupsList.remove(g);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: parses a JSON Object of the current appUser
    @Override
    public JSONObject toJson() {
        EventLog.getInstance().logEvent(new Event("Genrating user JSON."));
        JSONObject json = new JSONObject();
        json.put("groupList", groupsListToJson());
        return json;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Parses the GroupModel's in grouplist to a JSON Array.
    private JSONArray groupsListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (GroupModel g : groupsList) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }

}
