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

### Phase 4: Task 2

Sample read me where new group is added, members created, one is removed and one is edited.
The member "mem to remove" and "mem 2 to edit later" are the parts where a member is removed and edited.

Tue Apr 02 23:29:44 PDT 2024\
New app user initialized.\
Tue Apr 02 23:29:45 PDT 2024
Reading from file.\
Tue Apr 02 23:29:45 PDT 2024\
New app user initialized.\
Tue Apr 02 23:29:45 PDT 2024\
New group created test 1 rereedit\
Tue Apr 02 23:29:45 PDT 2024\
Changing activity time from 99999999 to 30 for group test 1 rereedit\
Tue Apr 02 23:29:45 PDT 2024\
New member created Bob\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@99b7a48\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@7e183cf3\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@7881dd2f\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@33eb7136\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@61f00439\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@5eb05350\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@28ae850c\
Tue Apr 02 23:29:45 PDT 2024\
New member created John\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@40a433ee\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@2194360f\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@7de2cbbe\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@21b8a7eb\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@4062cb71\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@40c6a93c\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@2b1235a1\
Tue Apr 02 23:29:45 PDT 2024\
New group added to list.\
Tue Apr 02 23:29:45 PDT 2024\
New group created Group 2\
Tue Apr 02 23:29:45 PDT 2024\
Changing activity time from 99999999 to 200 for group Group 2\
Tue Apr 02 23:29:45 PDT 2024\
New member created Bobgagain\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@4ff158d7\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@199e3b1b\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@6ce4ee5d\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@5661615d\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@1313bca6\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@7b3d1b3c\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@30e08400\
Tue Apr 02 23:29:45 PDT 2024\
New member created Johnagain\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@6dd660c8\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@63ae1758\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@70d914ea\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@442d5e75\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@1a23a618\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@9a476d3\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@31624d49\
Tue Apr 02 23:29:45 PDT 2024\
New group added to list.\
Tue Apr 02 23:29:45 PDT 2024\
New group created group 3\
Tue Apr 02 23:29:45 PDT 2024\
Changing activity time from 99999999 to 16 for group group 3\
Tue Apr 02 23:29:45 PDT 2024\
New member created member 1\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@1f9273e6\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@1806156a\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@323eea57\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@1195e15c\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@1d94bdcb\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@35114656\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@70b86ef1\
Tue Apr 02 23:29:45 PDT 2024\
New member created member 2\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@4e19febb\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@765b6856\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@4947b33d\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@7a3b9302\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@7b5646d8\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@56e2883c\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@22481786\
Tue Apr 02 23:29:45 PDT 2024\
New member created member3\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@4cd234e\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@4d64d589\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@f051771\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@6d13d530\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@295c8f\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@6b8a199e\
Tue Apr 02 23:29:45 PDT 2024\
Changing user time slot to [Ljava.lang.Boolean;@4c932501\
Tue Apr 02 23:29:45 PDT 2024\
New group added to list.\
Tue Apr 02 23:33:25 PDT 2024\
New group created New group to add for readme\
Tue Apr 02 23:33:25 PDT 2024\
Changing activity time from 99999999 to 35 for group New group to add for readme\
Tue Apr 02 23:33:26 PDT 2024\
New member created\
Tue Apr 02 23:33:30 PDT 2024\
Changing user time slot for 0:0 to false\
Tue Apr 02 23:33:31 PDT 2024\
Changing user time slot for 0:15 to false\
Tue Apr 02 23:33:31 PDT 2024\
Changing user time slot for 0:30 to false\
Tue Apr 02 23:33:32 PDT 2024\
Changing user time slot for 0:45 to false\
Tue Apr 02 23:33:33 PDT 2024\
Changing member name from  to mem 1\
Tue Apr 02 23:33:33 PDT 2024\
New member mem 1 added to group.\
Tue Apr 02 23:33:33 PDT 2024\
New member created\
Tue Apr 02 23:33:39 PDT 2024\
Changing user time slot for 0:0 to false\
Tue Apr 02 23:33:40 PDT 2024\
Changing user time slot for 0:15 to false\
Tue Apr 02 23:33:41 PDT 2024\
Changing user time slot for 0:30 to false\
Tue Apr 02 23:33:41 PDT 2024\
Changing user time slot for 0:45 to false\
Tue Apr 02 23:33:42 PDT 2024\
Changing member name from  to mem 2 to edit later\
Tue Apr 02 23:33:42 PDT 2024\
New member mem 2 to edit later added to group.\
Tue Apr 02 23:33:42 PDT 2024\
New member created\
Tue Apr 02 23:33:47 PDT 2024\
Changing member name from  to mem to remove\
Tue Apr 02 23:33:47 PDT 2024\
New member mem to remove added to group.\
Tue Apr 02 23:33:47 PDT 2024\
New member created\
Tue Apr 02 23:33:48 PDT 2024\
Member mem to remove removed from group.\
Tue Apr 02 23:33:49 PDT 2024\
Changing user time slot for 0:0 to false\
Tue Apr 02 23:33:49 PDT 2024\
Changing user time slot for 0:15 to false\
Tue Apr 02 23:33:50 PDT 2024\
Changing user time slot for 0:30 to false\
Tue Apr 02 23:33:51 PDT 2024\
Changing user time slot for 0:45 to false\
Tue Apr 02 23:33:53 PDT 2024\
Changing member name from mem 2 to edit later to mem 2 to edit later\
Tue Apr 02 23:33:53 PDT 2024\
Member mem 2 to edit later removed from group.\
Tue Apr 02 23:33:53 PDT 2024\
New member mem 2 to edit later added to group.\
Tue Apr 02 23:33:53 PDT 2024\
New member created\
Tue Apr 02 23:33:54 PDT 2024\
Finding common schedules. New avail times [10000, 10015, 10030, 10045] for group New group to add for readme\
Tue Apr 02 23:33:54 PDT 2024\
Matching group schedule to activity length for group New group to add for readme\
Tue Apr 02 23:33:55 PDT 2024\
New group added to list
Tue Apr 02 23:33:55 PDT 2024\
Writing to file\
Tue Apr 02 23:33:55 PDT 2024\
Genrating user JSON.\
Tue Apr 02 23:33:55 PDT 2024\
New app user initialized.

Process finished with exit code 0
