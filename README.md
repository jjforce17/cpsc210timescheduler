# Time-Conflict-Solver-inator

## Do you and your group members have a busy schedule and find it hard to make time for the project?
Don't worry about it and just use the **Time-Conflict-Solver-inator**

*Note: name is prone to change*

*Note: yes this is a Phineas and Ferb reference*

**What is this app?**

It's an app that allows any group of people to find common free time between members, it is determined by the length of 
activity which can be split into multiple sessions or multiple activities. The app also uses direct input of unavailable
time to help identify gaps in busy schedules. Furthermore, it is most beneficial to larger groups and people with very 
busy schedules.

**Why make/use this app/inator instead of using something else the great Dr. Heinz Doofenshmirtz has created?**

Because with busy schedules it is easy to overlook small bits for free time. I myself have had this issue.
Moreover, trying to think and plan out schedules is hard without a central/well-made timetable 
(My calendars aren't in sync). 
With the app, you don't have to repetitively ask teammates "Are you available at **insert inconvenient time**?" 
This means less thinking about these things means more thinking about better things!
Which in turn maximizes time efficiency for increased inator creating!

Special Details
- Time slots will be split into 15 minutes for each day.
- Range for time slots will be limited to 7 days (prone to change).


## User Stories
- As a user, I want to be able to add a new group to my app. (Phase 1 done)
- As a user, I want to be able to add any number of members X to my current group Y. (Phase 1 done) (Multiea requirement)
- As a user, I want to be able to select unavailable/available time slots of my current user/member X. (Phase 1 done)
- As a user, I want to be able to select the duration of the group activity. (Phase 1 done)
- As a user, I want to be able to list all available activity times for the group. (Phase 1 done)
- As a user, I want to see if it is actually not possible for the group to meet. (Phase 1 done)
- As a user, I want to be able to list out all the members of a group. (List requirement) (Phase 1 done)
- As a user, I want to be able to create multiple group activities (same as multiple groups). (Phase 2 done)
- As a user, I want to be able to edit time slots of a member after available time slots have been shown. (Phase 2 done)
- As a user, I want to be able to choose to save or discard my new group. (Phase 2 done)
- As a user, I want to be able to view my old groups. (Phase 2 done)
- As a user, I want to be able to save my group edits. (Phase 2 done)

## Instructions for grader
**IMPORTANT NOTE: Make sure to load groups before creating a new user to not overwrite existing user data. there is a 
backup file in the data folder should you want to have existing user data when testing the app.**
- To start the GUI, run the Main class.
- In the main menu, you can see the calendar logo (image requirement)
- In the main menu, you can also LOAD the existing groups (load button requirement)
- After loading, you can view/edit the groups with the view/edit button.
- In the new window, you can choose to view group member, edit group details, remove group and save the data.
- If you remove, a success window will pop up with a smiley face image, close the window to go back to main menu
  (image requirement)
- In the main menu, you can add a new group by using the add group button. 
- The new window will prompt you to add group details. Appropriate error windows will pop up based on input validity.
  You can choose to discard the group at this stage.
- Continuing to member details with prompt a timetable and empty user slot.
- Editing the timetables by clicking them will set the user schedule. Type a name and click next member to save the
  current member. (add multiple X's to Y, add member to group)
- The user will then pop up on the right side of the page with apporiate functions to the member. 
  (Display the X's that have been added)
- Clicking the edit member button will change the timetable panel to that of the user selected and will allow you to
  change the name and schedule of a user. (X related action, edit an X that has been added to Y.)
- Clicking the remove member button will remove the selected member for the current group.
  (X related action, remove an X that has been added to Y)
- Click finish group once you have added all members and details.
- The new page allows you to discard the created group or save it to the user file (Save button requirement)
- Saving returns you to the main menu and displays a success screen with a smiley face. Close the window to return
  to main menu. (Image requirement)

### Requirement summary
- In the main menu, you can see the calendar logo (image requirement)
- If you remove, a success window will pop up with a smiley face image, close the window to go back to main menu
  (image requirement)
- Saving returns you to the main menu and displays a success screen with a smiley face. Close the window to return
  to main menu. (Image requirement)
- The new page allows you to discard the created group or save it to the user file (Save button requirement)
- In the main menu, you can also LOAD the existing groups (load button requirement)
- Editing the timetables by clicking them will set the user schedule. Type a name and click next member to save the
  current member. (add multiple X's to Y, add member to group)
- The user will then pop up on the right side of the page with apporiate functions to the member.
  (Display the X's that have been added)
- Clicking the edit member button will change the timetable panel to that of the user selected and will allow you to
  change the name and schedule of a user. (X related action, edit an X that has been added to Y.)
- Clicking the remove member button will remove the selected member for the current group.
  (X related action, remove an X that has been added to Y)
