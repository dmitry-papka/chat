package lv.dmppka.model;

public class ErrorModel {

    public static ErrorModel USERNAME_EXISTS = new ErrorModel(1, "User with this username already exists.");
    public static ErrorModel CONNECTION_LIMIT = new ErrorModel(2, "Connection limit is reached.");

    private int id;
    private String message;

    ErrorModel(int id, String message) {
        this.setId(id);
        this.setMessage(message);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
