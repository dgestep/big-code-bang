Environment Setup Instructions
==========================

Gradle Setup
	- download latest version of Gradle and unzip the gradle archive to a location on your disk.  Remember the location.

- Apache Tomcat Server Setup
	- go to tomcat.apache.org and download the Tomcat 8 distribution (requires at least Tomcat 8).
	    - at the time of this writing, this was at ( http://tomcat.apache.org/download-80.cgi )
		- Choose the correct "Core" distribution for you laptop
			- Mac's uses the tar.gz distribution
	- Unzip the download to your disk (remember the location)

- Database Setup
    - Download the community edition of MySQL. At the time of this writing, that was at ( https://dev.mysql.com/downloads/ ).
        - download the MySQL Community Server and follow installation instructions
        - You can optionally download and install the MySQL Workbench client to be used to create and query your databases.
    - Once you have MySQL installed and running, do the following:
        - Create a new schema. Use whatever schema name you want.
        - Open an SQL window and run the mysql-import.sql script located in the config folder located at the root of
        this project.  This script creates the minimum database tables needed by the generated application.
        - Open an SQL window and create the '${databaseUsername}' user.
            - Assuming you created a schema called 'myCoolDb' and your host name is 'localhost', the SQL necessary to
             grant all privs to your schema is:
                - GRANT ALL PRIVILEGES ON myCoolDb.* To '${databaseUsername}'@'localhost' IDENTIFIED BY
                '${databasePassword}';

- Insomnia Setup (optional)
    - Download the Insomnia tool to be used to test the REST web services.
    - At the time of this writing, Insomnia can be found at ( https://insomnia.rest/download/ ).
    - Install and start Insomnia
    - Import the insomnia workspace file located in the src/test/resources/ folder within the web project.
    - Use insomnia to test all generated rest services.


Intellij Instructions
==========================

Import Projects
	- Open Intellij IDEA and choose "Import Project"
	- Browse and select the ${rootFolderName} folder
	- Select the "Import project from external model" option and select the Gradle model.  Click Next.
	- Ensure the Gradle home location is the location that you downloaded it at in the above steps.
	- Click Finish
	- Ensure all projects are checked on the "Gradle Project Data to Import" window. Click OK.

- Setup Tomcat Server within Intellij
    - Define your local tomcat database connection pool
        - Edit the <your tomcat installation directory>/conf/context.xml file and paste following Resource entry
        within the <Context> tag.  (this points your app to your database)
        - Adjust these parameters as you wish

        <Resource name="${databaseJndiName }" auth="Container" type="javax.sql.DataSource"
                maxActive="8"
                maxWait="15000"
                removeAbandoned="true"
                removeAbandonedTimeout="60"
                username="${databaseUsername}"
                password="${databasePassword}"
                driverClassName="${databaseDriverClass}"
                url="${databaseUrl}" />

	- Choose "Run -> Edit Configurations..." from the top menu.
	- Expand the "Defaults" element and choose "Tomcat Server".
		- Click the + icon at the top-left of the window to add a new Local Tomcat
			- Select "Tomcat Server -> Local"
		- Provide a name for your Tomcat server.
		
		- If Tomcat hasn't been configured within Intellij, 
			- click the "Configure..." button and browse to the root location where you unzipped the apache tomcat distribution.
			- Click OK.
		- Choose your tomcat installation within the "Application server" dropdown.
		- Uncheck the "After launch" checkbox so that it doesn't open your browser every time you start the server.
		- Assign System Properties to your Tomcat Server
			- Within the "VM options text box, paste "-DLOG_PATH=${applicationLogFileLocation} -Druntime.environment=development" into the text box.
				- The -D tells the JVM to add a system property
				- LOG_PATH is the path on your disk to store the application log file 
					- pick a path that is relevant to your computer. 
					- you must manually create any folder that is in your path. 
				- runtime.environment is the environment with which the tomcat server is running in. "development" is the right value for your local environment.
		- Ensure the JRE is 1.8 or higher.
	- Within the Deployment tab
		- Click the + button at the bottom-left of the box labeled, "Deploy at the server startup" and choose "Artifact"
		- Choose the exploded version of the ${webWarName} file" option to be deployed at server startup and click
		 OK.
		- enter /${contextRoot} in the "Application context" box.
	- Click OK to save the server.
	- You should see an "Application Servers" tab appear at the bottom of your IDE.
- Start your local Tomcat server
	- Within the "Application Servers" (appears at the bottom-left), click the Run icon.
	- Invoke REST health service at : http://localhost:8080/${contextRoot}/restcontroller/health
	-    ( adjust the URL if you assigned a different port )
	- Should see status of 200 and your host name displayed
