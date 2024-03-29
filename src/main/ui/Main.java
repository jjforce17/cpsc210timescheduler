package ui;

//Initalizes UI by creating new instance of ScheduleApp.
public class Main {

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: main function
    public static void main(String[] args) {
        try {
            new MainWindow();
           // new ScheduleApp();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}