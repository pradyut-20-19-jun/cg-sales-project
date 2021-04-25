# cg-sales-project

A.  Database Setup:
    Import shema to mysql database. File name: sales_management_schema.sql 

B. Project Setup/Run:
   1. Update database user and password from application.properties
   2. Update log file name from application.properties
   3. Install Maven by command: sudo apt install maven
   4. Go to Project Dir.
   5. Run folowing command to build Project.
   
      Used to clean:                        mvn clean
      Used to download lib for first time:  mvn install
      Used to compile:                      mvn compile
      Used to test:                         mvn test
      Used to create jar file:              mvn package
      
   6. Go to target folder by: cd target 
   7. To find jar:            ls 
   8. java -jar [jar file name].jar
      Exam: java -jar SalesManagement-0.0.1-SAPSHOT.jar
   
