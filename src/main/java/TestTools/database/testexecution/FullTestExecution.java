package TestTools.database.testexecution;

/**
 * Created by nizienko on 05.11.14.
 */
public class FullTestExecution {
    private String name;
    private String ptoject;
    private String version;
    private String build;
    private String execution;
    private String date;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPtoject() {
        return ptoject;
    }

    public void setPtoject(String ptoject) {
        this.ptoject = ptoject;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
