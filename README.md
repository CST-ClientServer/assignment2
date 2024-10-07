# Assigment 2

## - How to run the project

1. clone this project `git clone git@github.com:CST-ClientServer/assignment2.git`

2. Use IntelliJ IDEA Ultimate

3. Press `Ctrl+Alt+Shift+S` (Project Structure Menu) and go to libraries

4. Click on the `+` button then `From Maven...` and search for `jackson-databind` and click on the magnifying glass

5. Click on the dropdown menu when the search is done and select `com.fasterxml.jackson.core:jackson-databind:2.15.2`

6. Check your newly downloaded file. It should bring up 3 classes: `jackson-annotations`, `jackson-core`, and
 `jackson-databind`. If you don't have all these files, then download them from https://mvnrepository.com/

7. Go to the Artifacts menu and select `assignment:war_exploded`

8. Create a folder named "lib" under the WEB-INF folder and drag `jackson-databind`(also `jackson-annotations` and
 `jackson-core` if available) from the assignment folder on the right into the newly created lib folder. Accept all
changes.

9. Press `Alt + U` (Run/Debug menu) and select Edit Configurations

10. Select Tomcat Server -> Local 

11. Make sure the URL is http://localhost:8082/assignment_war/ and the HTTP port is `8082`

12. Go to the Deployment tab and click on `+` then `Artifact...` then select both `assignment:war` and `assignment:war
exploded`. Accept all changes.

13. Enter to the assignment2-client folder

14. Run npm install `npm i` while in the assignment2-client terminal to install dependencies.

15. Run the Tomcat Server that you recently set up for this project.

16. Run the React app by typing `npm start` while in the assignment2-client terminal

17. For the ConsoleApp interaction, make assignment2-app/app/main/java a `src` folder and run Activity.java by pressing 
the play button beside the main function. You may switch ports in the UploadClient.java file by altering the static port
number(8081 or 8082).

18. Make sure you run your own Tomcat server at port 8081 because this project sets up Tomcat at port 8082.
