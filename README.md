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

14. Run npm install
    `npm i`

15. Run the Tomcat Server (Ignore the initial placeholder form)

16. Run the project in dev
    `npm start`
