# nafee-SubBook https://youtu.be/qnj-RjghDqs

# APK File for installation: nafee-SubBook/app/build/outputs/apk/debug/app-debug.apk
#Developer typed sections of the code are:
# 1. Four .java files (2 Activities, 1 class and one CustomAdapter) in the folder: nafee-SubBook/app/src/main/java/com/example/nafeekhan/firstapp/
# 2. Three .xml layout files located in the folder: nafee-SubBook/app/src/main/res/
# 3. One .xml manifest file.
# build.gradle files.



####App has two screens.###
#Initial Screen is a ListView display list of subscriptions, with an "Add" button at the bottom and a textview on top
#***Note: I tested on list item clicks for when the list view is full.. I did not test listview click for when list view is empty. When list was empty I tested the Add button.
# showing total monthly expenses.
#If Add button is clicked user is taken to second screen
#Second screen has two 'modes' editing and not editing.
########When not editing the screen shows the details of the item that was clicked.
###########long with the decision to either delete it or edit it. If edit is clicked (or if add was clicked on the first screen) User is taken straight to "editing" mode
########In editing mode the screen displaying the details becomes editable, the Edit and Delte button dissappear and the Submit button appears.
#When the submit button is clicked the 2nd activity screen returns the result to the main screen and the main screen adapter updates the screen.

#From what I tested - my app calculates Totalmonthly,
                    - it has a functional Add, Edit and Delete. feature.
***Note: I was unable to get functional I/O methods working.


                                              
