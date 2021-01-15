# SNS_simulator
This program simulates same functions of SNS using JAVA.

Objectives: Implement a simple Social Network Service (SNS).      
Descriptions: We are going to build a simple console-based SNS system.            
A user can write posts, search them, and view the recommended friends' posts.          

The application mainly consists of 3 classes: UserInterface , FrontEnd , and BackEnd (along with a few additional classes supporting these main classes).       
● The UserInterface class provides interfaces to users.         
○ It does the authentication at the beginning of the application through the AuthView class.      
○ After the login, the PostView class gets a command input from the user by String getUserInput(String prompt) method and calls the corresponding method of the FrontEnd class.     
○ The results of the commands, provided by the FrontEnd class should be displayed using the print methods of UserInterface in an appropriate form.      
■ UserInterface.println : Equivalent to System.out.println      
■ UserInterface.print : Equivalent to System.out.print         
● The FrontEnd class parses user commands/posts, and queries the BackEnd class (by calling the appropriate methods) to process them.    
● The BackEnd class receives the queries from the FrontEnd class, processes them, and returns the results.          
○ The BackEnd class inherits the ServerResourceAccessible class.        
○ The ServerResourceAccessible class has the member attribute named serverStorageDir, which specifies the path to the data directory.                     
All the necessary data (e.g., user information, friends information, user posts) are stored as files under this directory.         

○ Note that the serverStorageDir value ends with “/”.          
○ In subsequent descriptions, we will describe the data directory path as the $(DATA_DIRECTORY).         


● The program should do nothing in case of any exception (e.g., non-existence of the user id).      
● Default test cases specified in this document are provided in Test.java . You can compare your result from Test.java with the expected outputs in this document.     


# Function 1: Authenticate         

Objective: Implement the method public boolean auth(String authInfo) of the FrontEnd class to authenticate the user.        
In particular, it compares the input password with the password stored in the server to check its validity.        
Description: Upon the program start, the console will ask for the user id and the password.        
● The password of a user is stored at the path $(DATA_DIRECTORY)/(User ID)/password.txt; here, the (User ID) is the id of the user.     
● Assume that all the names of the direct child directories of $(DATA_DIRECTORY) are valid user ids.    
● Assume that every $(DATA_DIRECTORY)/(User ID) has a password.txt.     
● For the successful authentication, the input password and the stored password should be identical including white spaces.        
● If the login fails, the program terminates.       

# Function 2: Post a User Article          

Objective: Implement the public void post(Pair<String, String> titleContentPair) method of the FrontEnd class to store the written post in the server.     
Description: When a user inputs the “post” command to the console, he can start writing a post with the title and content.         
The content of the post ends when the user inputs “Enter” twice.      
● Store the user’s post at the path $(DATA_DIRECTORY)/(User ID)/post/(Post ID).txt.      
(User ID) is the user’s id used for the login, and the (Post ID) is the nonnegative integer assigned uniquely to each and every post in the $(DATA_DIRECTORY). The newly
assigned ID should be 1 + the largest post id in the entire posts in $(DATA_DIRECTORY) or 0 in case when $(DATA_DIRECTORY)/(User ID)/post/ is empty.    
● Assume all the $(DATA_DIRECTORY)/(User ID) has a directory named post, and each post directory has at least one post.       
● The content of the post should not include the trailing empty line.
● Used the LocalDateTime class, and LocalDateTime.now to get the current date and time.           


# Function 3: Recommend Friends’ Posts      

Objective: Implement the public void recommend() method of the FrontEnd class to print the latest posts of the user’s friends.         
Description: Our SNS service recommends a user the latest posts of her friends. When the user inputs the “recommend” command to the console, up to 10 latest posts of the friends
should be displayed.     
● The list of the user’s friends is stored at the path "$(DATA_DIRECTORY)/(User ID)/friend.txt".     
● You need to look at all the posts of the friends and print up to 10 posts with the latest created dates.     
● How do we decide those 10 posts to recommend?     
○ Sort posts by the created date specified in a post file in descending order (from latest to oldest).     
○ Select the first 10 posts from the sorted list.         
● Assume the created date and time of each post is unique. No two posts have the same created date and time.      
● To compare two LocalDateTime class instances, I used the isAfter , isBefore , isEqual , compareTo methods of the LocalDateTime class.    
● Assume all the friend ids on the friend.txt are valid, and the corresponding folders exist in the $(DATA_DIRECTORY).       
● The post should be printed as a format of the toString method of the Post class, not the getSummary method, with the UserInterface.println method.      

# Function 4: Search Posts          

Objective: Implement the public void search(String command) method of the FrontEnd class to display up to 10 posts that contain at least one keyword.      
Description: Our SNS service enables users to search for posts with multiple keywords. When the user inputs the “search” command along with a set of keywords, the console should display up to 10 posts containing the most number of keywords in descending order of the created date and time.         
● The range of the search is the entire posts of all users (NOT friends only) in the $(DATA_DIRECTORY).       
● The command string starts with “search” followed by keywords.         
● Two keywords are separated with space(‘ ’). The newline should not be considered as a keyword.        
● Duplicate keywords should be ignored. For example, the output of search hi hi should be identical to the output of search hi .      
● You should count the number of occurrences of the exact keyword from the title and the content of the post.    
● More specific details for the keyword matching:     
○ It should be a case-sensitive comparison.           
○ You should only count the word that is identical to the provided keyword. You don’t need to consider the word that has the given keyword as a substring.       
● How do we decide those posts to show?
○ Sort the candidate posts based on two criteria.            
First, sort by the number of occurrences of the keywords in descending order. When multiple posts have the same number of occurrences, sort them by the created date and time of the post in descending order (from latest to oldest).        
○ Select up to 10 posts from the beginning of the sorted list.         
● The posts should be displayed using the format provided by the getSummary method of the Post class, not the toString method.    
