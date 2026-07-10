package test;

public abstract class Test {
    private int status; 
    private String message;

    public Test() {
        status = -1;
        message = "default was not started";
    }

    public abstract  void run();

    public int getCode() {
        return status;
    }

    public String getStatus() {
        return status <= 0 ? "Fail" : "Success";
    }

    public String getNameTest() {
        return this.getClass().getSimpleName();
    }

    public String getResult() {
        return message;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    protected void setFail() {
        status = 0;
    }

    protected void setSuccess() {
        status = 1;
    }
}
