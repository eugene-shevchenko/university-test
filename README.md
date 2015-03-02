# university-test
University Test Application

The server 2 - students

Each second there is a chanse of 30% that a student will born
The student has a lot of questions:   http://pastebin.com/raw.php?i=An0nmxYf
The student will ask for a new philosopher from the school
The student will ask the philosopher random question once a 100 millisec, 
until  the philosopher will answer 3 different answers to one of the questions
After the student abandon the philosopher he ask for a new one
After 3 philosophers the student will commit suicide 
Students server  has a web page shows list of students what philosophers it has in the past and why the student fired them.



The server 1  - philosophers school

In the school live 10 philosophers: http://pastebin.com/raw.php?i=ePQtKSxu
It is possible to order a philosopher from the school when needed.
Every philosopher knows cool magic answers http://pastebin.com/raw.php?i=PNHh4Wfm
A philosopher server has a web page to show all philosophers what questions he was asked and what answers gave to what question


Protocol (json rpc)

GET_PHIL - student asking for a new philosopher 
ASK_PHIL - student asking a question
FIRE_PHIL - student fires a philosopher



Implementation

using spring boot for the servers
using json rpc for dialog between servers
using spring MVC for web page
in memory H2 db for storage
