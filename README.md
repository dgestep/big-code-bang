# The Big Code Bang Project

The Big Code Bang project is a tool that generates the project structure and the code for the Model and View 
components of a web application.  A typical web application involves a database layer, an application logic layer, a REST service 
layer, security, and a view layer. Best practice suggests that each of these layers be decoupled in order to allow flexibility with 
regards to the implementation details. Big Code Bang generates the projects and code architected in a manner that can
 be built upon and adheres to best practices.  Run Big Code Bang and... bing bang boom, you have a runnable 
 application separated into two projects; one project containing your application/database logic and another project 
 containing your View logic.  Go ahead and give it a bang! :-)

## Prequisites
To run the Big Code Bang, you will need the following :

* The Java Developer Kit (JDK), version 8 or higher (to check, use java -version).
* A database management system (DBMS). The code is generated to interact with a [MySQL](https://www.mysql.com/) database. These instructions 
assume MySQL is your DBMS.
* A Java servlet container. These instructions assume [Apache Tomcat](http://tomcat.apache.org/) version 8+ is your 
Java servlet container.
* [NodeJS](https://nodejs.org). (to check, use node -v)
* [Node Package Manager (NPM)](https://www.npmjs.com/). (to check, use npm -v)

## Setup
The Big Code Bang project uses the [Gradle Build Tool](https://gradle.org/) to execute. It is not necessary to 
install Gradle because it is embedded within the Big Code Bang project. The Big Code Bang project is powered by Groovy 
scripts. Gradle ships with its own Groovy library, therefore Groovy does not need to be installed. Any existing 
Groovy installation is ignored by Gradle. Gradle uses whatever JDK it finds in your path. Alternatively, you can set
 the JAVA_HOME environment variable to point to the installation directory of the desired JDK.

* Install the Java Developer Kit, version 8 or higher.  The JAVA_HOME environment variable is expected to be set 
after this installation.
* Install MySQL version 5+. The MySQL service needs to be running when the Big Code Bang project is executing.
* Install Apache Tomcat version 8+.
* Clone or download this project.

## Configuring the Big Code Bang

### Server Side Configuration

_resources/server_project.properties_

| Property | Description | Example Values |
| :------- |:------|:------|
| project_base_folder | The file system path where the Server project will be generated by Big Code Bang. If any folder contained within the path does not exist on the file system, the folder will be created. | "/temp/BigCodeBang" (replace with your file system structure) |
| top_level_domain | The top level domain of the company that owns this application. This value, along with the company_name and product_name property values, is used when creating the java package structure for the generated code. | "com" or "edu" or "org" |
| company_name | The company name that owns the generated application.  This value, along with the top_level_domain and product_name property values, is used when creating the java package structure for the generated code. | "esteptek" |
| product_name | The name of the product or application. This value, along with the top_level_domain and company_name property values, is used when creating the java package structure for the generated code. | "bigcodebang" |
| company_name_long | The english name for the company that owns the generated code. This value will appear in generated gradle scripts. | "Estep Technologies" |
| java_version | The minimum version of java the application is written for. Can not be less that 1.8. | "1.8" |
| mail_host_name | The host server for sending emails. | "localhost" |
| mail_from_address | The email address that will be used by the application when sending automated emails. | "app@yourdomain.com" |
| database_jndi_name | The JNDI name the application uses to connect to the database connection pool. | "jdbc/bigcodebang" |
| database_driver_class | The database driver used to get a connection. | "com.mysql.cj.jdbc.Driver" |
| database_url | The URL to the database used to get a connection. | "jdbc:mysql://localhost/bigcodebang" |
| database_username | The database user name used to get a connection. | "my_db_user" |
| database_password | The database user password used to get a connection. | "changeme" |
| code_author | The name to assign as the author of all generated code. | "John Doe" |
| web_war_name | The name of the generated WAR file which is used to serve up the REST services. | "myapp.war" |
| application_log_file_location | The location for the application log file. | "/Users/dougestep/temp/logs" |


### View Side Configuration

_resources/view_project.properties_

| Property | Description | Example Values |
| :------- |:------|:------|
| project_base_folder | The file system path where the View project will be generated by Big Code Bang. If any folder contained within the path does not exist on the file system, the folder will be created. | "/Users/dougestep/temp/BigCodeBang" |
| view_root_folder_name | The root folder of the project. | "esteptek-bcb-view" |
| application_title | The value assigned to the title of the web application. | "Big Code Bang Sample Application" |
| context_root | The context root for the web application. | "bcb" |
| localhost_port | The port used when running the application locally in the IDE. | "8081" |
| server_port | The port used when running the application on remote web server. | "80" |
| server_host_name | The host name used when running the app on a remote server. | "mydomain.com" |

## Executing The Big Code Bang

To run the Big Code Bang, do the following :
* Open a command prompt or terminal and CD into the folder where the Big Code Bang project was cloned or downloaded. 
You should be in the same folder as the build.gradle file.
* type "gradle generateProjects" to initiate a Big Code Bang, generating your server and view projects.

```groovy
cd /path/to/BigCodeBang
gradle generateProjects
```

* type "gradle deleteProjects" to delete the projects generated by the Big Code Bang.

```groovy
cd /path/to/BigCodeBang
gradle deleteProjects
```

* type "gradle generateViewProject" to initiate a Big Code Bang, generating only the view project.

```groovy
cd /path/to/BigCodeBang
gradle generateViewProject
```

 