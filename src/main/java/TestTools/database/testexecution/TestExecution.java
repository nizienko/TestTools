package TestTools.database.testexecution;

import java.util.Date;

/**
 * Created by def on 03.11.14.
 */
public class TestExecution {
    private Integer id;
    private Integer testCaseId;
    private Integer statusId;
    private Date execution_dt;

    public Integer getBuildExecutionId() {
        return buildExecutionId;
    }

    public void setBuildExecutionId(Integer buildExecutionId) {
        this.buildExecutionId = buildExecutionId;
    }

    private Integer buildExecutionId;

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

    public Date getExecution_dt() {
        return execution_dt;
    }

    public void setExecution_dt(Date execution_dt) {
        this.execution_dt = execution_dt;
    }
}
