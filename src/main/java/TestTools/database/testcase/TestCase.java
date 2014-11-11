package TestTools.database.testcase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by def on 03.11.14.
 */
public class TestCase {
    private Integer id;
    private Integer testSuiteId;
    private String name;
    private String description;
    private ArrayList<Integer> labelId;
    private Integer status;
    private String issue;

    public List<Integer> getLabelId() {
        return labelId;
    }

    public void setLabelId(ArrayList<Integer> labelId) {
        this.labelId = labelId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestSuiteId() {
        return testSuiteId;
    }

    public void setTestSuiteId(Integer testSuiteId) {
        this.testSuiteId = testSuiteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }


}
