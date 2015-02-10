package TestTools.database.testexecution;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by def on 03.11.14.
 */
public class TestExecution {
    private Integer id;
    private Integer testCaseId;
    private String testCaseIssue;
    private Integer statusId;
    private Date executionDt;
    private Integer buildExecutionId;
    private String testCaseName;
    private String ptojectName;
    private String versionName;
    private String buildName;
    private String executionName;
    private String comment;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:SS dd-MM");


    public TestExecution() {

    }

    public TestExecution(Integer testCaseId, Integer statusId, Integer buildExecutionId) {
        this.testCaseId = testCaseId;
        this.statusId = statusId;
        this.buildExecutionId = buildExecutionId;
        this.executionDt = new Date();
    }

    @Override
    public String toString(){
        if (executionDt != null) {
            if ("Ad hoc".equals(buildName)) {
                return DATE_FORMAT.format(executionDt) + "(" + versionName + ")";
            }
            else {
                return DATE_FORMAT.format(executionDt) + "(" + versionName + "-" + buildName + ")";
            }
        }
        else {
            return "-";
        }

    }

    public Integer getBuildExecutionId() {
        return buildExecutionId;
    }

    public void setBuildExecutionId(Integer buildExecutionId) {
        this.buildExecutionId = buildExecutionId;
    }

    public String getTestCaseIssue() {
        return testCaseIssue;
    }

    public void setTestCaseIssue(String testCaseIssue) {
        this.testCaseIssue = testCaseIssue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(Integer testCaseId) {
        this.testCaseId = testCaseId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Date getExecutionDt() {
        return executionDt;
    }

    public void setExecutionDt(Date execution_dt) {
        this.executionDt = execution_dt;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getPtojectName() {
        return ptojectName;
    }

    public void setPtojectName(String ptojectName) {
        this.ptojectName = ptojectName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getExecutionName() {
        return executionName;
    }

    public void setExecutionName(String executionName) {
        this.executionName = executionName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
