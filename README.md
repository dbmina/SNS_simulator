# SNS_simulator
This program simulates same functions of SNS using JAVA.

## Simulate a simple Social Network Service (SNS).      
#### console-based SNS system.            
A user can write posts, search them, and view the recommended friends' posts.          

## Components
<ul>
  <li> UserInterface </li>
  <li> FrontEnd </li>
  <li> BackEnd </li>
  <li> a few additional classes supporting these main classes</li>
</ul>

## Class UserInterface
<ul>
  <li> Provides interfaces to users.     </li>    
  <li> It does the authentication at the beginning of the application through the AuthView class. </li>     
  <li>After the login, the PostView class gets a command input from the user by String getUserInput(String prompt) method and calls the corresponding method of the FrontEnd class. </li>     
<li> The results of the commands, provided by the FrontEnd class should be displayed using the print methods of UserInterface in an appropriate form.      </li>
  <li>UserInterface.println : Equivalent to System.out.println   </li>
  <li> UserInterface.print : Equivalent to System.out.print     </li>    
  
  </ul>     

## Class FrontEnd
  <ul>
<li> It parses user commands/posts, and queries the BackEnd class (by calling the appropriate methods) to process them.  </li>
  </ul>
  
  
## Class BackEnd
  <ul>
<li> It receives the queries from the FrontEnd class, processes them, and returns the results.       </li>   
  <li> inherits the ServerResourceAccessible class.     </li>
  </ul>

## Class ServerResourceAccessible 
<ul>
<li> It has the member attribute named serverStorageDir, which specifies the path to the data directory.  </li  >                 
<li> All the necessary data (e.g., user information, friends information, user posts) are stored as files under this directory.    </li>     
</ul>

# Function 1: Authenticate         
<ul>
<li> method public boolean auth(String authInfo) of the FrontEnd class does authenticate the user.     </li>   
<li> it compares the input password with the password stored in the server to check its validity.  </li>      
  <li> Upon the program start, the console asks for the user id and the password.     </li>   
  <li>  The password of a user is stored at the path $(DATA_DIRECTORY)/(User ID)/password.txt; here, the (User ID) is the id of the user.      </li>   
  <li>  For the successful authentication, the input password and the stored password should be identical including white spaces.    </li>    
  <li>  If the login fails, the program terminates.  </li>     
</ul>

# Function 2: Post a User Article          
<ul>
<li> public void post(Pair<String, String> titleContentPair) method of the FrontEnd class for storing the written post in the server.     </li>
<li> When a user inputs the “post” command to the console, he can start writing a post with the title and content.  </li>       
  <li> The content of the post ends when the user inputs “Enter” twice.    </li>  
  <li> user’s post is stored at the path $(DATA_DIRECTORY)/(User ID)/post/(Post ID).txt.     </li>   
 <li> (User ID) is the user’s id used for the login, and the (Post ID) is the nonnegative integer assigned uniquely to each and every post in the $(DATA_DIRECTORY). The newly
assigned ID should be 1 + the largest post id in the entire posts in $(DATA_DIRECTORY) or 0 in case when $(DATA_DIRECTORY)/(User ID)/post/ is empty.    </li>            
</ul>


# Function 3: Recommend Friends’ Posts      
<ul>
<li> public void recommend() method of the FrontEnd class is used for printing the latest posts of the user’s friends.     </li>    
<li> This SNS service recommends a user the latest posts of her friends. When the user inputs the “recommend” command to the console, up to 10 latest posts of the friends
are displayed.     </li>
<li> The list of the user’s friends is stored at the path "$(DATA_DIRECTORY)/(User ID)/friend.txt". 
  <ul>
    <li> How do we decide those 10 posts to recommend?   </li>  
   <li>Sort posts by the created date specified in a post file in descending order (from latest to oldest).  </li>      
 <li> Select the first 10 posts from the sorted list.           </li>       
  </ul>
   </li> 
</ul>

# Function 4: Search Posts          
<ul>
<li>public void search(String command) method of the FrontEnd class is used for displaying up to 10 posts that contain at least one keyword.    </li>        
<li> It enables users to search for posts with multiple keywords. When the user inputs the “search” command along with a set of keywords, the console displays up to 10 posts containing the most number of keywords in descending order of the created date and time.         </li>     
<li> The command string starts with “search” followed by keywords.    </li>         
<li> Duplicate keywords are ignored. For example, the output of search hello hello should be identical to the output of search hello.    </li>       
 
</ul>
