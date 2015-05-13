# rdbms-trucktable
Java application with GUI interacting with MySQL database. 

<h2>Language written in:</h2>
<p>Java, SQL</p>

<h2>Compilation Platform:</h2>
<p>Linux / MAC OS X / Netbeans</p>

<h2>Description:</h2>
<p>The goal of this programming project is to design and create a working Java application to access and update the Truck table in a Relational Database System (RDBMS). The RDBMS I am using is MySQL. The functionalities I must include in this program are the ability to view records, update records, delete records, insert a new record, and clear the records in the Truck table. The primary key used for the database is the VIN# - an unique truck identifier. </p>
<p>The biggest problems I've encountered is designing an minimalistic, efficient graphic-user interface. Since the users may not readily know what are in the table or know the VIN# to use, I think this will be a problem. To remedy this flaw, I wanted to add something compact to display the VIN# existing in the table. I concluded that a JcomboBox which functions like a drop-down menu box, is the best route to go. The Jcombobox will be placed right next to the VIN# input box, to complement it. I even added a function to select from the Jcombobox and automaticly be set to the VIN# input box. </p>
<p>To accommodate unexpected events. For example, the user may not have used the Jcombobox to determine if a record exists before using the update, view, or delete functions. To avoid unwanted events, I added a nifty method called a peek method. Before running any of the update, view, or delete functions when chosen by the user, the program will first peek at the table to see if the record matching the inputted VIN# exists or not. If the record does not exists, then a JoptionPane will appear displaying an error dialog message.</p>
<p>MySQL was a great tool to use and Java works well in combination. As the first time creating and using a database system, this programming project provides adequate hands-on practicing. </p>

<h2>Application Checklist:</h2>
 1.	Is your database created correctly? 		Yes
 2.	Can you create the UI?		Yes
 3.	Can you connect to the database?		Yes
 4.	Is the View button implemented correctly?		Yes
 5.	Is the Insert button implemented correctly?		Yes
 6.	Is the Update button implemented correctly?		Yes
 7.	Is the Clear button implemented correctly?		Yes
 8.	Did you include instructions on how to use your program? 		Yes, See 'Usage'

<h2>Deployment Guide</h2>	
 1.	How to create the database.	
	Use the codes given in the .sql file included, or just use “CREATE DATABASE Dealership”
 2.	How to create the tables.
	Use the codes given in the .sql file included, or just use “CREATE TABLE Truck”
 3.	How to create the RDBMS.
	Install MySQL or something similar, create the database and 2 or more tables, and assign primary keys to the records in the tables. Create any table using foreign keys.

 
<h2>Usage:</h2>
 1.	Open the Project file named “SQLDatabase” in NetBeans.
 2.	Run the program named “myDatabase”.
 3.	When the GUI is running, enter the VIN# into the input box and press a button on the left side. Follow and enter any prompts when prompted. 

<h2>Screen shots:</h2>
<p>GUI View:
<img src="https://github.com/netlams/rdbms-trucktable/blob/master/ScreenShots/1.default.jpg" alt="Default"></p>
<p>Record Selection:
<img src="https://github.com/netlams/rdbms-trucktable/blob/master/ScreenShots/2.selection.jpg" alt="Record Selection"></p>
<p>View a record:
<img src="https://github.com/netlams/rdbms-trucktable/blob/master/ScreenShots/3.view.jpg" alt="Record View"></p>
<p>Update a record:
<img src="https://github.com/netlams/rdbms-trucktable/blob/master/ScreenShots/4.update.jpg" alt="Record Update"></p>
<p>SQL Table:
<img src="https://github.com/netlams/rdbms-trucktable/blob/master/ScreenShots/7.sqltable.jpg" alt="sql table" width="400" height="300"></p>
