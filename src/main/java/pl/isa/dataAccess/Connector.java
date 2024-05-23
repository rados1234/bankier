package pl.isa.dataAccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Connector {
    /**
     * class to provide access to data
     * at this phase, we're writing objects in JSON format to a .txt file
     */

    private static final String USERS_FILE = "users.txt";
    private static final Path PATH_TO_USER_FILE = Paths.get(System.getProperty("user.dir"), USERS_FILE);

    public Connector() {
        File database = new File(PATH_TO_USER_FILE.toString());
        if (!database.exists()){
            try{
                database.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean save(Object o){
        /**
         * method stub for saving to database
         */
        ObjectToJson objectToJson = new ObjectToJson();
        String jSonString = objectToJson.serialize(o);
        return saveJson(jSonString);
    }

    public boolean saveJson(String json) {
        /**
         * method to store json in a text file
         * @param json
         * is the content to be saved into DB (txt file)
         *
         * @return true if success, false otherwise
         * see debug logs for details
         *
         */
        try {
            return (PATH_TO_USER_FILE == Files.writeString(PATH_TO_USER_FILE, json, StandardOpenOption.APPEND));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String read()  {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader buffer = new BufferedReader(
                new FileReader(PATH_TO_USER_FILE.toString()))) {
            String str;
            while ((str = buffer.readLine()) != null) {
                builder.append(str).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
