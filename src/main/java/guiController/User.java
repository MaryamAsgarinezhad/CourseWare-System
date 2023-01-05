package guiController;

import org.json.simple.JSONObject;

public class User {
    private static User instance=null;
    private JSONObject jsonObject=new JSONObject();

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public User(JSONObject jsonObject){
        this.jsonObject=jsonObject;
    }

    public static User getInstance(JSONObject jsonObject)
    {
        if (instance == null){
            instance = new User(jsonObject);
        }

        return instance;
    }

    public static void setInstance(User instance) {
        User.instance = instance;
    }
}
