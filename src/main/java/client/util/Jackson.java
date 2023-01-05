package client.util;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Jackson {
    public static ObjectMapper getNetworkObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
        objectMapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper;
    }

    public static void writeOnFile(String value, String fileName, String attribute) {
        JSONObject jsonObject = getjsonOfFile(fileName);
        jsonObject.put(attribute, value);

        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            out.write(jsonObject.toJSONString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static JSONObject getjsonOfFile(String fileName) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(fileName)) {
            Object object = jsonParser.parse(fileReader);
            return (JSONObject) object;
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
//        String s=FileConverter.encode("D://maryam//phase2-files");

    }
}