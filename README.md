# University-Educational-System

login page
In this section, professors, students and assistant professors can enter their accounts. This is done by ID
User and password are entered.
This page has the feature captcha.

- Captcha
On the login page, a captcha is displayed as an image, which contains a four-digit number. If the user enters the captcha correctly, if the username and password
If it was correct, he enters his account. Otherwise, an appropriate error message will be given and the captcha will be changed

main page
After the login page, you need to enter the main page.
Things to show:


For all users:

Current time (hour and day)
Exit button
Time of last entry
User photo
User name and email


From the student's point of view:

Educational status
Supervisor
Registration permit
Registration time
Registration and its sub-menus
Educational services and its sub-menus
Report matters and its sub-menus
User profile and its submenus


From the teachers' point of view:

Registration and repair matters
educational services
Records of courses and students
User profile
From the point of view of the teaching assistant:
In addition to having all the professors' menu, the vice-chancellor also has an option to register a new user
has it.


New user registration: ●

This option enables the Vice-Chancellor to introduce a new professor or student
to the system. The vice-chancellor is able to enter all user information and
assign a username and password for him.


Notes:

The user can return to the main page from all pages. ●
The user can exit the program at any time.


Registration matters


For students:
"List of courses and List of professors"

List of courses: ●
On this page, we show all the courses of the current semester and their information. Also, the student can
Apply different filters to see your desired courses. Filters can include: faculty name,
The number of units, professor's name, course level, course number, etc.

List of professors: ●
In this section, the student can see the list of courses, the list of professors, and a number of
Teachers' information can be seen. In addition to the professor's name, at least 3 items from: faculty and email
We show the professor's degree and position in the university and the room number and contact number.


For professors:
"List of courses and List of professors"

List of courses: ●
It is like students.

List of professors: ●
This section is like the "List of professors" section for students. ○
If this professor was the head of the faculty, he should be able to edit, delete or add professors of his faculty ○
The dean of the faculty is able to change the role of professors to assistant professors. If the faculty has ○
The head of the faculty ,the dean is not able to appoint a new vice-chancellor and must be


For the teaching assistant:

List of courses
List of professors
dismiss the previous vice-chancellor.


educational services


This menu is different for different users

From the perspective of an undergraduate student:

Weekly Schedule
List of student exams
requests:
     Request for a letter of recommendation
     Application for employment certificate
     minor
     Withdrawal from education
     
Weekly schedule ●

List of exams: ●
We show the list of student exams in chronological order.

Request for a letter of recommendation: ●
The student can apply for a recommendation letter to the relevant professor, and the result is the same
see page


From the perspective of a master's student:

Weekly Schedule
List of student exams
requests:
     Request for a letter of recommendation
     Application for employment certificate
     Request for accommodation
     Withdrawal from education
     
     
From the PhD student's point of view:

Weekly Schedule
List of student exams
requests:
     Application for employment certificate
     Withdrawal from education
     Request to defend the thesis
     
     
Records matters

The letter contains the following two general sections:
Provisional scores and Student's academic status

Provisional grades: ■

For students:

The student must be able to view the grades that have been temporarily recorded. ●
The student can file a protest for these grades. ●
The student can see the teacher's answer. ●
When the grade is removed from the temporary state and becomes registered, the student can no longer ●
See the grade in the list of temporary grades.


For the teacher:
The professor has seen the list of registered students for the course and can give a separate score for each of them.

The professor is able to edit each student's grade before recording the grades. ●
The professor cannot record provisional grades unless he has entered a provisional grade for everyone. ●
The professor is not able to consider a score more or less than the range of 0 to 20 for the student and otherwise
This appropriate error form should be shown to him.
If the grade entered by the professor has an accuracy of more than 0.25, the system automatically calculates the grade ●
rounds
The professor is able to observe the students' objections. ●
The professor is able to write an answer for each objection and edit the student's score. ●
The professor is able to finalize the grades. (After he registered temporarily.) ●


For the teaching assistant:

The vice-chancellor can see the recorded grades of all students in a class. ●
The VP is able to filter and see the provisional grades of a particular student. ●
The vice-chancellor is able to search based on the teacher's name and the grades recorded by him ●
to observe
The vice-chancellor can see the objections and the answers to the objections. ●
The teaching assistant is able to view a summary of the lesson. This summary includes course average, ●
The number of failed and accepted students and the grade point average of the course are without considering the failed students.


Student educational status

This section is implemented only for students and vice-chancellors of education.

For students:

The student is able to see the number of units he has passed. ●
The student is able to view the final recorded grades in this section. ) Lessons that ●
(N/A, they have not yet registered their final score. You can view it as
The student can see his grade point average, which is calculated in a weighted form, in this section. ●


For the teaching assistant:

The vice president of education is able to see all the things that the student sees. ●
The vice-chancellor is able to search based on the student's name and student number among the available data
to search


User profile

For students:

first name and last name
National Code
student number
phone number
E-mail
Total Average
College
Supervisor
entering year
Photo of the student
degree (bachelor's, master's, doctorate)
Status (currently studying, graduated or withdrawing from studies)


For the teacher:

first name and last name
National Code
master's number
phone number
E-mail
College
room number
Master's photo
Professor degree (including three degrees: assistant professor, associate professor, full professor)


For all users:

Any user can change the theme.


Notes:
Note that both types of users (students, professors) must be able to enter their e-mail address and mobile phone number to change.
The teacher and student photo section should be empty if there is no photo of them.


Change password page

If more than three hours have passed since the user's last login, a password change page will appear that the user have to change password.
Notes:
On this page, the user's previous password and new password are requested. ●
The user will not be allowed to enter until he/she successfully passes this page.


Project network structure:
The entire structure of the project is based on the network and client-server structure (including the existence of the concept Authentication token and ping,And the lack of direct access of clients to each other and the separate communication of each One with the server so that several clients can run and enter the program at the same time) has been implemented.

● When the network connection is interrupted, the client should be notified automatically
and the program without closing, have the ability to run some offline capabilities.
In the following, more detailed explanations regarding the offline mode.

●The presence of a button to refresh the program or any other function is not allowed within the project. Program at any moment automatically
reads the information from the server and executes it in the client. So if there is one change from another user, for example, registering a student's score by proffessor, this change should be seen by the second user during automatic refresh.

Unit selection page:
The ability to add, delete and edit lessons was defined for the assistant professor in the previous phase. In this phase, some new information should be added to the lesson when it is added right in the middle of the teaching assistant. When defining a course, the name of the course, the course professor(s), the course assistants, course code, prerequisites and co-requisites, the section and capacity of the course, as well as the class and exam time, must be entered and registered. Then on the main page on the students' side, The unit selection menu should be defined by the student. This menu is always hidden except when it is defined for the unit selection by the vice-chancellor.

To implement the unit selection menu, you must also save the entry year of each student. The Vice-Chancellor of Education can determine the time of choosing a unit for different students based on several filters (year of entry of the student, degree of the student, etc.) The user must be able to obtain a number of units in the times specified by the vice-chancellor so that the following conditions are met.

We will have 2 sections on this page. In a section, a student can choose a faculty and only the courses of that faculty will be displayed. The courses that should be shown should be able to be sorted based on at least 3 characteristics (such as exam time, alphabetical order of course names and course section). In the other section, the list of suggested courses that the student should take should be shown based on the fact that they have passed the prerequisites, have an empty capacity, and the student's degree is compatible with the field. In the continuation of this section, the marked courses of the student can also be seen.

In front of each lesson that the student has not taken, there are 3 options of marking/unmarking, taking the lesson, and requesting the assistant to take this course. be sent to the vice president of the student's faculty and appear in the vice president's message section. At the end of the same message, the vice-chancellor should be able to reject or accept this request and the lesson will be taken for the student.

Also, for courses that the student has not taken yet, there should be 3 options of marking/unmarking, deleting, and changing the group. By clicking on changing the group, if the course contained only one group, a message stating that the operation cannot be performed. be displayed to the student, and if another group is available, in a small message box, it will show the list of the rest of the course groups, if the student clicks on it, the group will be changed for him, and if the capacity of the group The student's choice (to change the group) was complete, the same message should be sent to the vice president.

In every type of taking and changing the group, there is a series of errors that should be prevented from taking or changing the course group and the appropriate error message should be shown to the student.
Types of errors:
●The course capacity has been completed.
●The prerequisite is not met. 
●Class time may interfere with other subjects. 
●Exam time may interfere with other courses. 
●Only one course can be taken from education courses. 

Message page:
Requests received from other system users and system notifications are shown to the user in this section. Then the user can accept or reject the request.
Descriptions of how to send requests and system notifications are given in other sections. It should be noted that all educational and student requests to professors, vice presidents, and presidents should be displayed in their messages section and their responses should also be displayed in the messages section of the student page.

Messenger page:
The messenger page, which we must access through the main page, includes two sections:
Chats ●
The list of user chats is displayed in this section. In each item, the name of the chat contact and the last chat message should be specified. Also, this list is sorted based on the last message that was given. After selecting any chat, the user enters the chat room of that conversation and he should be able to see all the messages exchanged so far in the correct chronological order. Also, the other party's name and photo should be displayed at the top of the chat room.
The user must be able to send a message to the other person.
This message can be an audio file,Photo, PDF or video and the time of sending each message must be clear. Note that the messages for both parties must be sent simultaneously and in harmony.

Create a chat ●
Every user in the system should be able to create a new chat. After entering this page, the student will see a list of students of his/her field and entrance and his/her supervisor and can send a message to one of them or collectively to a part of them or all of them. Also, if a student wants to send a message to a teacher or a student who is not in this list, he/she requests to send a message by giving the student or professor code of the addressee. This request should be sent to his contact list immediately. When the professor enters this page, he will see a list of students who are his supervisor, and in the same way, he can send messages to them in the above three modes. The vice-chancellor and the dean of the college should send a message to all the students of the college in the same way. Mr. Mohseni also has the list of the entire university students and can also send them messages in three ways.

New users of the program:
:EDU Admin ●
We have a user in the system with code 1 who is supposed to respond to students' problems. When a user is connected to the network, he can message this user and explain his problems. As long as the connection is not established, this message is saved and immediately after connecting to the network, the message must be sent to the administrator. Note that if you exit the program, this message should still be saved and sent after connecting.

Mr. Mohseni: ●
On its main page, there is only one section for searching for students and one section for sending messages to students with a series of special filters (with at least 3 characteristics). In the section of searching for students, a list opens that includes all students in the order of their entry year. In each item, the student's name and number and the degree level of each student are specified.
At the top of this list, there is a textbox that whenever Mr. Mohseni enters the digits of the student number, the list below it should be updated immediately or with a pause, and all the students whose student numbers apply to those digits should appear. (It should be noted that there should not be any key to search and by writing a student number, only one student with a specific student number is available in the list below)

Teaching assistant: ●
Teaching assistants are the same students who are added to the software classroom in a different way, which is explained.


Course ware:
Add a student to the lesson page: ●
If a student is added to the course after the unit selection deadline, the course instructor can add the student to the course page by entering the student's student number in the section of the course page. Also, the professor can add a student as a student or teaching assistant. After being added to the class (either automatically or manually by the teacher), a notification from the system must be sent to the user in the "Messages" section. This notification is done by the system and it is clear that The user has been added to the class as a student or as a teaching assistant (by adding a teaching assistant, the student must be added to the list of teaching assistants for the desired course).

Lesson page: ●
After entering the main page, the student can see the menu of the courseware, in the category of my courses, he can see the courses he has taken and the class created for them.
By clicking on any lesson, you will be directed to the page of that lesson, where you can see the teaching calendar of the lesson, which includes the end of the exercises. Then he can view the educational materials, exercises and exams and enter each one. For the teacher, the pages are designed in exactly the same way, but above each section (educational materials, exercises, and exams), the teacher should also see a create option, but the teaching assistant should not have the create key.

Create lesson page: ●
Each lesson is automatically created after it is defined, and a page is created for it in the courseware, which the corresponding professor can view on his own page. Immediately after taking a course in the student's choice of unit (after finalizing the unit selection and removing unauthorized courses), the course page should be added to the courseware. Also, if the course is deleted, this page should also be deleted from the list of pages.

Educational materials: ●
When the teacher wants to create a new educational material, by clicking on the create button, we first get the name of the educational material from the teacher. Then a page will open with 2 options to add media file and add text. Every time you add a new item, at the bottom of it there is an option to add a new item and the final registration of the content, which can add new text or media if you add a new content. The teacher should be able to add a maximum of 5 items to the training page.
After completion, the educational content page should be shown to the student and the professor in the same way. But on the page of educational material for the teacher, there should be three options to change, delete and add each item and one option at the top of the entire section to delete the educational material.
The training assistant does not have the section to delete the item, but it can change it.

Exercise: ●
After the exercise is created by the teacher, the exercise opening time, closing time, time to upload the exercise without deducting the score, the name of the exercise, description and PDF file of the exercise, as well as the allowed file type, which is divided into two text or media categories , to be taken from the teacher. After creating, on the exercise page, the professor can see the files or texts sent by the students and the time they were sent, and if the upload model was media, he can download them and assign a grade to each of them in the section of the same student. to give
By entering this page, the student can see the file and explanation of the questions, delivery status, grade, opening and closing time of the exercise and the submission deadline without deducting the grade.
In one place of this page, depending on the exercise model, a text can be uploaded as a written answer or answer media.
In this section, the teaching assistant can also see the list of students, but he cannot see their name and student number and sees their characters as *. But he can see the answers and grade them.

Comprehensive educational calendar: ●
In this courseware section, both students and professors can see the end of the exercises (without deducting the grade) of each course that is on their course page, as well as the end of the semesters of the courses they have.

Offline mode:
It may not be possible to connect to the server due to some errors, such as the server being off, or the server address being wrong, etc. It is also possible that due to sudden problems on the server side, such as disconnection between the server and the database, the server may be disconnected suddenly, and as a result, the connection between the client and the server will suffer. In these two modes, your program should be able to do some things offline.
If the connection is not established or the user's connection is interrupted, the user must be informed about this. There should also be a button that the user can click to retry to connect to the server and notify the user.
Also, in the offline mode, a message about being offline should always be displayed to the user.

Some features of the program should be available offline when the user is not connected to the server. The features that the program is expected to be able to do in offline mode are as follows:

1. Home page information (from the perspective of all users)
2. Educational services: (from the point of view of professors and all students of all levels)
Weekly program ●
List of exams ●
3. Report matters: (from the point of view of students of all levels)
Educational status ●
4. User profile (from the point of view of all users)
All the cases of the previous phase, except for making changes in the information ●
5. View previous messages up to at least the last ten messages (if any) exchanged in each chat room (from the view of all users)
The user cannot send a message in this mode and can only view.
