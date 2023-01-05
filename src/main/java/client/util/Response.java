package client.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Response {
    public ResponseStatus status;
    public HashMap<String, Object> data;

    public List<HashMap<String ,Object>> answer=new ArrayList<>();
    public String errorMessage;

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public HashMap<String, Object> getData() {
        return data;
    }


// Another field for messages from server to client

    public Response() {}

    public void setAnswer(List<HashMap<String, Object>> answer) {
        this.answer = answer;
    }

    public List<HashMap<String, Object>> getAnswer() {
        return answer;
    }

    public Response(ResponseStatus status) {
        this.status = status;
        data = new HashMap<>();
    }

    public Response(ResponseStatus status,List<HashMap<String ,Object>> answer) {
        this.status = status;
        this.answer=answer;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void addData(String dataName, Object data) {
        this.data.put(dataName, data);
    }

    public Object getData(String dataName) {
        return this.data.get(dataName);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}