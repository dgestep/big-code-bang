# The Big Code Bang Project

I have architected and developed many enterprise applications throughout my career. I discuss my past work on my 
[online resume](http://dougestep.com/resume/). I leverage the knowledge and experiences from my past projects and 
work with every new project that I architect and develop, piecing together the initial commit with a common 
architecture and libraries that have worked well for me in the past. Regardless of the numerous applications I have 
developed, the initial setup and commit always seems to take too long. The Big Code Bang project was created to 
expedite the process of creating an enterprise application, using a proven architecture that can be built upon. I 
created the Big Code Bang Project to leverage these same technologies and libraries, generating the source code for both
 the server side and client side projects.  The result of executing the Big Code Bang is a working web application 
 using the latest/greatest technology stack, ready to be imported into your IDE and checked into your code repository. 
 

The generated source code is yours to build upon, saving weeks, if not months of time and money. 

**Go ahead and give it a BIG bang! :-)**


--Doug Estep


## Architecting Enterprise Applications

Architecting an enterprise web project can be complex and involves many decision points such as the following:

* A server platform -- Linux, Windows, etc.  Clustered environment or single server?  If clustered then a load 
balancer will be needed (F5 Big-IP, etc.).
* Deciding on a server-side software technology stack such as Java, .NET, NodeJS, etc.  This decision may be based on
 your server platform.  For example, if you want a .NET technology stack then it most likely needs to run on Windows.
  (.NET core runs on any OS). If you choose a Java technology stack then you have library choices, such as Spring, EJB, 
  etc.
* Deciding on a software platform for the view into your web application.  Do you want a single page application or a
 traditional request/response application?  If you want a single page application, then you have framework choices; 
 Angular JS, JQuery, Backbone, etc., or a controller stack such as Spring MVC in Java or a .NET backed controller.  
 If you choose the single page application approach then there are more tools to decide on and learn; Bower, Gulp, 
 NodeJS, Node Package Manager (NPM), and more.  (Lot's of moving parts for sure).
* Security technologies.  Authentication and authorization.  LDAP, Active Directory, emdedded 
authentication/authorization, the honor system -- kidding :-).
* Logging technologies, such as Log4j or Log4N (.NET), etc.
* Database? Structured data or big data? An ORM such as Hibernate or EclipseLink for Java or the entity 
 framework for .NET. Or... go it old school and do straight JDBC or ODBC (don't mock... sometimes there's a valid 
 use-case for old school).
* What application server will your web application run in?  Apache Tomcat, IBM Websphere, Oracle Weblogic, JBoss, IIS 
for .NET, etc.

Once you have decided on the technology stack, the next step is to setup the tools and processes necessary for 
the standard software development lifecycle stuff:

* Setup a build script to build your application, including source code checks for style, code analyzers such as PMD 
and FindBugs, and tools to report code coverage.  The choices differ based on your choice of server side technologies
 to which your application will be written.
* Setting up a continuous integration environment such as Jenkins, etc.
* Picking a code repository, such as GIT, Subversion, Team Foundation Server (TFS), etc.
* Deciding on a tool for bug and issue tracking and project management such as Jira by Atlassian, etc.
* Training developers on the tools and technologies that you have selected.

As an architect, doing your due diligence on each of these decision points is crucial for a successful and secure 
application.  However, doing so usually takes a considerable amount of time and money.

## The Power of the Big Code Bang

Although I have done some work using Microsoft's technologies, a big portion of my career has been using Java. I have
 done some with with NodeJS on the server as well.  Any of these choices are fine for the server-side and all have 
 their pros and cons.  The initial rollout of the Big Code Bang project is using Java as the server-side technology 
 of choice.  I intend to create a .NET and NodeJS implementation of the server-side in future versions.
 
The common architecture and libraries the Big Bang Project uses/generates are described below:

### Server Side REST Service Layer
* [Spring](https://spring.io/) - an application framework and inversion of control container for the Java platform. 
Spring is used to aid in the creation of the REST services, controlling Security, and database access using Spring 
ORM and Spring JDBC.
* [EclipseLink](http://www.eclipse.org/eclipselink) - an open source Eclipse Persistence Services Project from the 
Eclipse Foundation. The software provides an extensible framework that allows Java developers to interact with 
various data services, including databases, web services, Object XML mapping (OXM), and Enterprise Information 
Systems (EIS).
* [Gradle Build Tool](https://gradle.org/) an open source build automation system that builds upon the concepts of 
Apache Ant and Apache Maven.  Gradle is used for its dependency management and build capabilities.
* [MySQL](https://www.mysql.com/) an open source relational database management system (RDBMS) based on Structured 
Query Language (SQL).  Any database vendor can be used with the Big Code Bang.  I chose MySQL because of its ease of 
use.  If you are using a different database vendor, you will need to adjust the SQL create scripts to adhere to your 
DBMS syntax and adjust the JPA adapter settings in the generated model-sprint-context XML file.
* [Apache Tomcat](http://tomcat.apache.org/) version 8+.  Used to deploy a Web ARchive (WAR) file to serve up REST 
services.  You're not tied to Tomcat.  You can use any application server.  I chose Tomcat because of it's ease of 
use. I describe how to setup a database connection pool in Tomcat.  If you choose a different application server, you
 will need to setup your database connection pool using the instructions provided by your application server. 
* JUnit - a unit testing framework for the Java programming language.

### Client Side Layer
* A single page application using [Angular 4](https://angular.io/) and [Angular Material Design](https://material.angular.io/)
* [Typescript](https://www.typescriptlang.org/) - a typed superset of JavaScript that compiles to plain JavaScript
* [Twitter Bootstrap](http://getbootstrap.com/) - an open source front-end web framework for designing websites and 
web applications.
* Cascading Style Sheets (CSS) - a style sheet language used for describing the presentation of a document written in
 a markup language.
* Hypertext Markup Language (HTML)

## Prequisites
To run the Big Code Bang, you will need the following :

* The Java Developer Kit (JDK), version 8 or higher (to check, use java -version).
* A database management system (DBMS). The code is generated to interact with a [MySQL](https://www.mysql.com/) database. These instructions 
assume MySQL is your DBMS.
* A Java servlet container. These instructions assume [Apache Tomcat](http://tomcat.apache.org/) version 8+ is your 
Java servlet container.
* [NodeJS](https://nodejs.org). (to check, use node -v)
* [Node Package Manager (NPM)](https://www.npmjs.com/). (to check, use npm -v)
* [Gradle Build Tool](https://gradle.org/)

## Setup
* Install the Java Developer Kit, version 8 or higher.  The JAVA_HOME environment variable is expected to be set 
after this installation.
* Install MySQL version 5+. The MySQL service needs to be running when the Big Code Bang project is executing.
* Install Apache Tomcat version 8+.
* Clone or download this project.
* Download latest version of the [Gradle Build Tool](https://gradle.org/) and unzip the gradle archive to a location 
on your disk.  Make a note of the location because you will need it later.  

## Configuring the Big Code Bang
The Big Code Bang project generates two applications; a server side WAR project which serves up REST services
 and an HTTP client side application used to display the view into the application.  Each application requires 
 configuration in order to properly generate the applications.  Below are the possible configuration properties for 
 each application.

### Server Side Configuration
_resources/server_project.properties_

The properties below are used to generate the source code for the server-side projects.  The values supplied are 
examples. It is expected that you provide your own values for your project.

**Note:** All properties are String values and must be surrounded by double quotes.

| Property | Description | Example Values |
| :------- |:------|:------|
| project_base_folder | The file system path where the Server project will be generated by Big Code Bang. If any folder contained within the path does not exist on the file system, the folder will be created. | "/temp/wiley-app" (replace with your file system structure) |
| top_level_domain | The top level domain of the company that owns this application. This value, along with the company_name and product_name property values, is used when creating the Java package structure for the generated code. Because this value is used in the Java package structure, it may be adjusted to adhere to Java package naming standards. | "com" or "edu" or "org" |
| company_name | The company name that owns the generated application.  This value, along with the top_level_domain and product_name property values, is used when creating the Java package structure for the generated code. Because this value is used in the Java package structure, it may be adjusted to adhere to Java package naming standards. | "acme" |
| product_name | The name of the product or application. This value, along with the top_level_domain and company_name property values, is used when creating the Java package structure for the generated code. Because this value is used in the Java package structure, it may be adjusted to adhere to Java package naming standards. | "roadrunner" |
| company_name_long | The english name for the company that owns the generated code. This value will appear in generated gradle scripts. | "Acme Corporation" |
| java_version | The minimum version of java the application is written for. Can not be less that 1.8. | "1.8" |
| mail_host_name | The host server for sending emails. | "localhost" |
| mail_from_address | The email address that will be used by the application when sending automated emails. | "app@mydomain.com" |
| database_jndi_name | The JNDI name the application uses to connect to the database connection pool. | "jdbc/widgetsDb" |
| database_driver_class | The database driver used to get a connection. | "com.mysql.cj.jdbc.Driver" |
| database_url | The URL to the database used to get a connection. | "jdbc:mysql://localhost/widgetsDb" |
| database_username | The database user name used to get a connection. | "wiley" |
| database_password | The database user password used to get a connection. | "super-genius" |
| code_author | The name to assign as the author of all generated code. | "Wiley Coyote" |
| web_war_name | The name of the generated WAR file which is used to serve up the REST services. | "wiley.war" |
| application_log_file_location | The location for the application log file. | "/temp/logs" |
| gradle_version | The gradle version you are using. | "3.1" |


### View Side Configuration
_resources/view_project.properties_

The properties below are used to generate the source code for the client-side project.  The values supplied are 
examples. It is expected that you provide your own values for your project.

**Note:** All properties are String values and must be surrounded by double quotes.

| Property | Description | Example Values |
| :------- |:------|:------|
| project_base_folder | The file system path where the View project will be generated by Big Code Bang. If any folder contained within the path does not exist on the file system, the folder will be created. | "/temp/wiley-app" |
| view_root_folder_name | The root folder of the project. | "wiley-view" |
| application_title | The value assigned to the title of the web application. | "Wiley Coyote Road Runner Widgets" |
| context_root | The context root for the web application. | "wiley" |
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

The generated source code is located at the location that you provided in the _project_base_folder_ 
properties within the _resources/server_project.properties_ and _resources/view_project.properties_ files.

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

## The Model Projects

### Intellij Instructions

The following describe how to import the Model and REST service projects into the [Intellij IDE](https://www.jetbrains.com/idea/) by JetBrains.

***

* Open Intellij and close all opened projects.
* Choose "Import Project"
* Browse and select the folder where the model project was generated. Using the property values contained within the above server.properties, the model project is located in the /temp/wiley-app/acme-roadrunner folder.
* Select the "Import project from external model" option and select the Gradle model.  Click Next.
* Ensure the Gradle home location is the location that you downloaded it at in the Setup instructions (above). Ensure the Gradle JVM points to your JDK location.
* Click Finish
* Wait until the "Gradle Project Data to Import" window appears and ensure all projects are checked. Click OK.
* Select the "Build/Build Project" menu choice to compile and build the generated application.  It should compile and build successfully if you have followed these instructions correctly.
* Expand the root project and open the config/readme.txt file and follow the instructions to continue setting up the application in Intellij.  These instructions were generated to match your specified server configuration values. (You can skip the "Import Projects" section since you have just done that).

## Quality Assurance Checks

### Unit Tests

Each generated project contains a suite of unit tests which exercise the code and establish a base to which future 
unit tests can be built. The "-data" project is the project that reads/writes to the database.  A suite of database 
unit test have been written. In order for these tests to successfully connect to the database, the database 
connection information must be validated as correct compared to how you created the database.  To validate this 
information, do the following:

* Expand the "-data" project and open the src/test/resources/test-model-spring-context.xml file.
* Locate the "dataSource" bean entry and validate that the connection information is correct.  If it is not correct, 
the unit tests that interact with the database will fail.

Execute the following from the command prompt to execute the unit tests:

```groovy
gradle test
```

### Checkstyle
_config/checkstyle/proj-checkstyle.xml_

Checkstyle is a static code analysis tool used in software development for checking if Java source code complies with
 coding rules.  The generated code conforms to the checkstyle rules defined in the _config/checkstyle/proj-checkstyle
 .xml_ file.  The Gradle build script invokes the checkstyle rules when building the WAR file.  If the source code 
 violates any checkstyle rules then the build will fail.

You can run this task manually by executing this command:

```groovy
gradle checkstyleMain
```

The checkstyle report is generated in the build/reports/checkstyle folder.


### PMD
_config/ruleset/proj-pmd-rules.xml_

PMD is a static Java source code analyzer. It uses rule-sets to define when a piece of source is erroneous. PMD 
includes a set of built-in rules and supports the ability to write custom rules. The generated code conforms to the 
PMD rules defined in the _config/ruleset/proj-pmd-rules.xml_ file.  The Gradle build script invokes the 
PMD rules when building the WAR file.  If the source code violates any PMD rules then the build will fail.

You can run this task manually by executing this command:

```groovy
gradle pmdMain
```

The PMD report is generated in the build/reports/pmd folder.

### Findbugs

FindBugs is an open source static code analyser created by Bill Pugh and David Hovemeyer which detects possible bugs 
in Java programs. The generated code conforms to the default set of findbug rules.  The Gradle build script invokes 
the findbug rules when building the WAR file.  If the source code violates any findbug rules then the build will fail.

You can run this task manually by executing this command:

```groovy
gradle findbugsMain
```

The findbugs report is generated in the build/reports/findbugs folder.

### Code Coverage

Code coverage is a measure used to describe the degree to which the source code of a program is executed when a 
particular test suite runs. The Gradle build script includes tasks to build a code coverage report.

```groovy
gradle test jacocoTestReport
```

The coverage report is generated in the build/reports/coverage folder.

## Build the REST Service WAR

* Open a Terminal from your operating system and navigate to the folder where the model project was generated. Using 
the property values contained within the above server.properties, the model project is located in the /temp/wiley-app/acme-roadrunner folder. If you are using Intellij then you can open a terminal window within Intellij.  This will place you in the correct location to run the build. 
* Execute the build using Gradle

```groovy
gradle clean build 
```

* The above command removes any past build data and rebuilds the WAR file, executing the unit tests, checkstyle, PMD, 
findbugs, and code coverage utilities.  With the exception of the code coverage tool, if any tool reports a failure, 
the build will not complete successfully.
* The WAR will be generated in the "-web" project within the build/libs folder.

## The View Project

### WebStorm Instructions

The following describe how to import the View project into the [WebStorm IDE](https://www.jetbrains.com/webstorm/) by 
JetBrains.

***

* Open WebStorm and close all opened projects.
* Click Open and navigate to the folder location where the View project was generated. Using the property values 
contained within the above view.properties file, the view project is located in the /temp/wiley-app/wiley-view folder.
* Click the Open button to open the project in WebStorm.
* Open the terminal view in WebStorm.
* Using the Node Package Manager, install all dependencies described in the package.json file.

```npm
npm install
```

* Using the Node Package Manager, update all dependencies to the latest version.

```npm
npm update --save
```

* From the terminal, execute the production build to build the distribution.

```
ng build --env=prod
```
This will create a dist folder at the root of the project containing the production deployment artifacts.

* From the terminal, execute the local server to run the application locally in the browser.
```
ng serve
```

* From within Intellij, start the REST service local server.
* Open a browser and navigate to ( http://localhost:4200 ) to login into the application.
* From the login page, enter _me@gmail.com_ as the email address, _DietCoke1_ as the password, and click Login.
* You are now logged in as Admin.  Click the _Admin Admin_ menu choice and choose _User Profile_.
* Change the first and last names to your name and change the email address to your email address. Click Save.
* Click your name on the menu and choose _User Administration_.  This screen allows you to search, add, and change 
user profiles.
* Click your name on the menu and choose _Change Password_.  This screen allows you to change your current password.
* Click your name on the menu and choose _Logout_.  You are logged out and taken back to the login page.

**Congratulations!** You now have a working application that you can build upon.  

Good luck with your project!


Doug Estep