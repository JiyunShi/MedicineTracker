package com.example.niceday.medicinetracker;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by NiceDay on 2017-07-19.
 */

public class JSONHelper {

    public static final String FILE_NAME  = "db_users.json";
    public static final String TAG = "JSONHelper";

    public static boolean updateDB(Context context, List<User> userList, String currentUser){

        Users userdb = new Users();
        userdb.setUsers(userList);
        userdb.setCurrentUserName(currentUser);


        Gson gson = new Gson();
        String jsonString = gson.toJson(userdb);


        FileOutputStream fileOutputStream = null;
        File file = new File(FILE_NAME);
        try {
            fileOutputStream =context.openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }










    public static Users getDB(Context context){

        FileReader reader = null;

        try {
            File file = new File(context.getFilesDir()+"/"+FILE_NAME);
            reader = new FileReader(file);
            Gson gson = new Gson();
            Users userdb = gson.fromJson(reader, Users.class);
            return userdb;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader !=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public static User getCurrentUser(Context context){
        Users userdb = getDB(context);
        List<User> userlist = userdb.getUsers();
        String currentUser = userdb.getCurrentUserName();
        for(int i=0;i<userlist.size();i++){
            if(userlist.get(i).getName().equals(currentUser)){
                return userlist.get(i);
            }
        }
        return null;
    }



    static class Users{
        List<User> users;
        String currentUserName = "";

        public String getCurrentUserName() {
            return currentUserName;
        }

        public void setCurrentUserName(String currentUserName) {
            this.currentUserName = currentUserName;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }
    }


}
