package client.util;

import java.util.HashMap;

public class Request {
    public RequestType requestType;

    public HashMap<String, Object> data;

    public Request() {}

    public Request(RequestType requestType) {
        this.requestType = requestType;

        data = new HashMap<>();
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void addData(String dataName, Object data) {
        this.data.put(dataName, data);
    }

    public Object getData(String dataName) {
        return this.data.get(dataName);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestType=" + requestType +
                ", data=" + data +
                '}';
    }
}