import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;

public class BackEnd extends ServerResourceAccessible {
    // Use getServerStorageDir() as a default directory
    // TODO sub-program 1 ~ 4 :
    // Create helper funtions to support FrontEnd class
    ArrayList<Pair> post=new ArrayList<Pair>();


    public void addPost(Post apost, String id){
       post.add(new Pair(apost, id));
   }
   public ArrayList<Post> makePost(ArrayList<String> post_list, ArrayList<String>filelist){
        int j=0;
        ArrayList<Post> real_post=new ArrayList<>();
       for(String post: post_list){
           String[] post_array=post.split("\n");
           String[] year_time= post_array[0].split(" ");
           String[] year_month_day=year_time[0].split("/");
           int year=Integer.parseInt(year_month_day[0].trim());
           int month= Integer.parseInt(year_month_day[1].trim());
           int day=Integer.parseInt(year_month_day[2].trim());
           String[] hour_minute_second=year_time[1].trim().split(":");
           int hour=Integer.parseInt(hour_minute_second[0].trim());
           int minute=Integer.parseInt(hour_minute_second[1].trim());
           int second=Integer.parseInt(hour_minute_second[2].trim());
           LocalDateTime LDT= LocalDateTime.of(year, month, day,hour, minute, second);
           String content="";
           for (int i=2; i<post_array.length;i++){
               content+=post_array[i];
               content+="\n";
           }
           String ids[]=filelist.get(j).split("\\.");
           j++;
           int id=Integer.parseInt(ids[0].trim());
           Post final_post=new Post(id,LDT, post_array[1],content.trim());
           real_post.add(final_post);
       }
       return real_post;
   }
}


