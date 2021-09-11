package hungnguyen2809.learnspring.common;

public class ResponseObject {
    private int status;
    private boolean error;
    private String message;
    private Object data;

    public ResponseObject() {}

    public ResponseObject(int status, boolean error, String message, Object data) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public static ResponseObject success(Object data){
        return new ResponseObject(200, false, "Successfully", data);
    }

    public static ResponseObject success(Object data, String message){
        return new ResponseObject(200, false, message, data);
    }

    public static ResponseObject success(Object data, String message, int status){
        return new ResponseObject(status, false, message, data);
    }

    public static ResponseObject error(Object data){
        return new ResponseObject(200, false, "Failed", data);
    }

    public static ResponseObject error(Object data, String message){
        return new ResponseObject(200, false, message, data);
    }

    public static ResponseObject error(Object data, String message, int status){
        return new ResponseObject(status, false, message, data);
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
