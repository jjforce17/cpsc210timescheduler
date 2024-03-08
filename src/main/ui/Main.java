package ui;

//Initalizes UI by creating new instance of ScheduleApp.
public class Main {

    public static void main(String[] args) {
        try {
            new ScheduleApp();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


//REQUIRES:
//MODIFIES: this
//EFFECTS: