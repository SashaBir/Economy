package Game;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSONHandler {

    private static final String FILE_NAME = "data.json";

    public Data TryFromJSON(Context context) {

        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME)) {
            return new Gson().fromJson(new InputStreamReader(fileInputStream), Data.class);
        }
        catch (IOException ex){
            return null;
        }
    }

    public boolean TryToJSON(Context context, Data data) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(data);

        try(FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
