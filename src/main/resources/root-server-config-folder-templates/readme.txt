Environment Setup Instructions
==========================

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
            - Assuming you created a schema called 'widgetsDb' and your host name is 'localhost', the SQL necessary to
             grant all privs to your schema is:
                - GRANT ALL PRIVILEGES ON widgetsDb.* To '${databaseUsername}'@'localhost' IDENTIFIED BY '${databasePassword}';

Database Connection Pool Instructions
======================================
    - Edit the <your tomcat installation directory>/conf/context.xml file and paste following Resource entry
    within the <Context> tag.  (this points your app to your database)
    - Adjust these parameters as required

    <Resource name="${databaseJndiName }" auth="Container" type="javax.sql.DataSource"
            maxActive="8"
            maxWait="15000"
            removeAbandoned="true"
            removeAbandonedTimeout="60"
            username="${databaseUsername}"
            password="${databasePassword}"
            driverClassName="${databaseDriverClass}"
            url="${databaseUrl}" />

Intellij Instructions
==========================

- Setup Tomcat Server within Intellij
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
	- Invoke the REST health service at ( http://localhost:${localhostPort}/${contextRoot}/restcontroller/health ).
	- A status of 200 should be displayed.

Eclipse Instructions
==========================
- Right-click on the -web project and select Properties.
- Select the Web Project Settings property and enter ${contextRoot} in the Context root input box.
- Click Apply and Close.

- Create a Tomcat Server within Eclipse
    - Open the Server perspective by choosing Window -> Show View -> Other -> Server.
    - Right-click in the white space on the Server perspective and choose New -> Server.
    - Select the Apache server type and choose your Tomcat version.  It must be version 8 or higher.
    - Leave the Servers Host Name as localhost.
    - Provide a name for your Tomcat server in the Server Name input or accept the default name.
    - If Apache Tomcat has not been configured in Eclipse, then click the Configure Runtime Environments
    link located at the bottom-right of the window.
        - Click the Add button to add the Tomcat runtime.
        - Select the Apache server type and choose your Tomcat version.  It must be version 8 or higher. Click Next.
        - Click the Browse button and select the folder where your Apache Tomcat installation resides.
        - Choose the JRE.  It must be 1.8 or higher.
        - Click Finish.
        - Select the Apache Tomcat runtime environment and click Apply and Close.
    - Click Next.
    - Select the -web project from the Available side and click the Add button to move the -web project to the
    Configured side.
    - Click Finish.

- Configure your Tomcat Server within Eclipse
    - Within the Server perspective, double-click the Tomcat server that was added in the prior steps to open the
    server properties.
    - Ensure the HTTP/1.1 port is set to ${localhostPort} within the Ports section.
    - Click the Open launch configuration link located within the General Information section to open the Edit
    Configuration window.
    - Click the Arguments tab.
        - Within the "VM arguments text box, paste " -DLOG_PATH=${applicationLogFileLocation} -Druntime.environment=development" at the end of the existing arguments.
            - The -D tells the JVM to add a system property
            - LOG_PATH is the path on your disk to store the application log file
                - pick a path that is relevant to your computer.
                - you must manually create any folder that is in your path.
            - runtime.environment is the environment with which the tomcat server is running in. "development" is the right value for your local environment.
    - Click OK to close the Edit Configuration window.

- Start your local Tomcat server
	- Click the Start button within the Servers perspective. Wait for the server to start and ensure there are no
	errors displayed in the console.
	- Invoke the REST health service at ( http://localhost:${localhostPort}/${contextRoot}/restcontroller/health ).
	- A status of 200 should be displayed.
