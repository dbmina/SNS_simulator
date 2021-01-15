import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.time.LocalDateTime;

public class FrontEnd {
    private UserInterface ui;
    private BackEnd backend;
    private User user;

    public FrontEnd(UserInterface ui, BackEnd backend) {
        this.ui = ui;
        this.backend = backend;
    }

    public boolean auth(String authInfo)  {
        // TODO sub-problem 1
        String[] str = null;
        if (authInfo != null) {
            str = authInfo.split("\n");
        }
        String password = "";
        ServerResourceAccessible sr = new ServerResourceAccessible();
        try {if(str!=null) {
            FileReader fr = new FileReader(sr.getServerStorageDir() + str[0] + "/password.txt");
            int i = fr.read();
            while (i != -1) {
                password += (char) i;
                i = fr.read();
            }
            fr.close();
        }
        } catch (IOException | NullPointerException e) {

        }
        this.user = new User(str[0], str[1]);
        if (password.equals(str[1])) {
            return true;
        }
        return false;
    }

    public void post(Pair<String, String> titleContentPair) {
        // TODO sub-problem 2

        ServerResourceAccessible sr = new ServerResourceAccessible();
        ArrayList<Integer> post_num = new ArrayList<Integer>();
        int max_num = 0;

        File path = new File(sr.getServerStorageDir());
        File[] files = path.listFiles();
        File[] small_files = null;
        File new_path = null;
        ArrayList<String> file_list = new ArrayList<String>();
        if (files!=null){
        for (File a : files) {
            new_path = new File(a.getPath() + "/post");
            small_files = new_path.listFiles();

            if (small_files != null) {
                for (File b : small_files) {

                    file_list.add(b.getName());
                }
            }}
        }

        while (file_list.contains(max_num + ".txt")) {
            max_num++;
        }


        FileWriter fw = null;
        try {
            File if_file = new File(sr.getServerStorageDir() + this.user.id + "/post/");
            if (if_file.listFiles() == null) {
                max_num = 0;
            }
            fw = new FileWriter(sr.getServerStorageDir() + this.user.id + "/post/" + max_num + ".txt");

            Post post = new Post(max_num, LocalDateTime.now(), titleContentPair.key, titleContentPair.value);
            backend.addPost(post, this.user.id);

            fw.write(post.getDate() + "\n" + post.getTitle() + "\n" + "\n" + post.getContent());
            fw.close();
        } catch (IOException e) {

        }

    }

    public void recommend() {
        // TODO sub-problem 3
        ServerResourceAccessible sra = new ServerResourceAccessible();
        String friend_list = "";
        try {
            FileReader fr = new FileReader(sra.getServerStorageDir() + this.user.id + "/friend.txt");
            int i = fr.read();
            while (i != -1) {
                friend_list += (char) i;
                i = fr.read();
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] friendlist = friend_list.split("\n");


        ArrayList<String> tmparray=new ArrayList<String>();
        for(String c: friendlist){
            tmparray.add(c.trim());
        }
        File path = new File(sra.getServerStorageDir());
        File[] files = path.listFiles();
        File[] small_files = null;
        File new_path ;
        ArrayList<String> file_list = new ArrayList<String>();
        ArrayList<String> post_list = new ArrayList<String>();
        for (File a : files) {
            if (tmparray.contains(a.getName())){
                new_path = new File(a.getPath() + "/post");
                small_files = new_path.listFiles();

                if (small_files != null) {
                    for (File b : small_files) {

                        file_list.add(b.getName());
                        try {
                            String tmp="";
                            FileReader frrr=new FileReader(b.getPath());
                            int i=frrr.read();
                            while(i!=-1){
                                tmp+=(char)i;
                                i=frrr.read();
                            }
                            frrr.close();
                            post_list.add(tmp);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        ArrayList<Post> real_post=backend.makePost(post_list, file_list);
        int j=0;

Comparator<Post> comparator=new Comparator<Post>() {
    @Override
    public int compare(Post o1, Post o2) {
        String[] date=o1.getDate().split(" ");
        String[] time=date[0].split("/");
        int year1=Integer.parseInt(time[0]);
        int month1=Integer.parseInt(time[1]);
        int date1=Integer.parseInt(time[2]);
        String[] timing=date[1].split(":");
        int hour1=Integer.parseInt(timing[0].trim());
        int minute1=Integer.parseInt(timing[1].trim());
        int second1=Integer.parseInt(timing[2].trim());
        LocalDateTime LDT1= LocalDateTime.of(year1,month1,date1,hour1,minute1,second1);

        String[] date2=o2.getDate().split(" ");
        String[] time2=date2[0].split("/");
        int year2=Integer.parseInt(time2[0]);
        int month2=Integer.parseInt(time2[1]);
        int day2=Integer.parseInt(time2[2]);
        String[] timing2=date2[1].split(":");
        int hour2=Integer.parseInt(timing2[0].trim());
        int minute2=Integer.parseInt(timing2[1].trim());
        int second2=Integer.parseInt(timing2[2].trim());
        LocalDateTime LDT2= LocalDateTime.of(year2,month2,day2,hour2,minute2,second2);
        if( LDT1.isAfter(LDT2)){
            return -1;
        }
         if (LDT1.isBefore(LDT2)){
            return 1;
        }
         return 0;
    }
};
       Collections.sort(real_post, comparator);
       if (real_post.size()>10){
           for(int i=0; i<10; i++){
               ui.println(real_post.get(i));
           }
       }
       else{
           for(int i=0; i<real_post.size();i++){
               ui.println(real_post.get(i));
           }
       }

        }


    public void search(String command) {
        // TODO sub-problem 4
        String[] command_array = command.split(" ");
        ArrayList<String> keyword = new ArrayList<>();
        for (int i = 1; i < command_array.length; i++) {
            if (!keyword.contains(command_array[i])) {
                keyword.add(command_array[i].trim());
            }
        }
        ServerResourceAccessible sra = new ServerResourceAccessible();
        File path = new File(sra.getServerStorageDir());
        File[] files = path.listFiles();
        File[] small_files = null;
        File new_path;
        ArrayList<String> file_list = new ArrayList<String>();
        ArrayList<String> post_list = new ArrayList<String>();
        for (File a : files) {
            new_path = new File(a.getPath() + "/post");
            small_files = new_path.listFiles();

            if (small_files != null) {
                for (File b : small_files) {


                    try {
                        String tmp = "";
                        FileReader frrr = new FileReader(b.getPath());
                        int i = frrr.read();
                        while (i != -1) {
                            tmp += (char) i;
                            i = frrr.read();
                        }
                        frrr.close();
                        for (String post : keyword) {
                            if (tmp.contains(post)) {
                                post_list.add(tmp);
                                file_list.add(b.getName());
                                break;
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

        }

        ArrayList<Post> real_post = backend.makePost(post_list, file_list);
        Comparator<Post> comparator = new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                String title = o1.getTitle();
                String content = o1.getContent();
                int count1 = 0;
                int count2 = 0;
                for (String titlecom : title.split(" ")) {
                    for (String key : keyword) {
                        if (titlecom.trim().equals(keyword)) {
                            count1++;
                        }
                    }
                }
                for (String contentcom : content.split(" ")) {
                    for (String key : keyword) {
                        if (contentcom.trim().equals(keyword)) {
                            count1++;
                        }
                    }
                }
                String title2 = o2.getTitle();
                String content2 = o2.getContent();
                for (String titlecom : title2.split(" ")) {
                    for (String key : keyword) {
                        if (titlecom.trim().equals(keyword)) {
                            count2++;
                        }
                    }
                }
                for (String contentcom : content2.split(" ")) {
                    for (String ke2 : keyword) {
                        if (contentcom.trim().equals(keyword)) {
                            count2++;
                        }
                    }
                }
                if (count1<count2){
                    return -1;
                }
                else if (count1==count2){
                    String[] date=o1.getDate().split(" ");
                    String[] time=date[0].split("/");
                    int year1=Integer.parseInt(time[0]);
                    int month1=Integer.parseInt(time[1]);
                    int date1=Integer.parseInt(time[2]);
                    String[] timing=date[1].split(":");
                    int hour1=Integer.parseInt(timing[0].trim());
                    int minute1=Integer.parseInt(timing[1].trim());
                    int second1=Integer.parseInt(timing[2].trim());
                    LocalDateTime LDT1= LocalDateTime.of(year1,month1,date1,hour1,minute1,second1);

                    String[] date2=o2.getDate().split(" ");
                    String[] time2=date2[0].split("/");
                    int year2=Integer.parseInt(time2[0]);
                    int month2=Integer.parseInt(time2[1]);
                    int day2=Integer.parseInt(time2[2]);
                    String[] timing2=date2[1].split(":");
                    int hour2=Integer.parseInt(timing2[0].trim());
                    int minute2=Integer.parseInt(timing2[1].trim());
                    int second2=Integer.parseInt(timing2[2].trim());
                    LocalDateTime LDT2= LocalDateTime.of(year2,month2,day2,hour2,minute2,second2);
                    if( LDT1.isAfter(LDT2)){
                        return -1;
                    }
                    if (LDT1.isBefore(LDT2)){
                        return 1;
                    }
                    return 0;
                }
                return 1;
            }


        };
         Collections.sort(real_post, comparator);
         if (real_post.size()>10){
             for(int i=0; i<10; i++){
                ui.println(real_post.get(i).getSummary());
             }
         }
         else{
             for(int i=0; i<real_post.size(); i++){
           ui.println(real_post.get(i).getSummary());
             }
         }

    }
    User getUser(){
        return user;
    }
}
