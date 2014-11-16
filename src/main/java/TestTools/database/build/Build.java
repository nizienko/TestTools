package TestTools.database.build;

import java.util.Date;

/**
 * Created by def on 30.10.14.
 */
public class Build {

    public Build() {

    }

    public Build(Integer versionId, String name, String description, Date givenDt, Date estimatedDt, Date finishedDt) {
        this.versionId = versionId;
        this.name = name;
        this.description = description;
        this.givenDt = givenDt;
        this.estimatedDt = estimatedDt;
        this.finishedDt = finishedDt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
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

    public Date getGivenDt() {
        return givenDt;
    }

    public void setGivenDt(Date givenDt) {
        this.givenDt = givenDt;
    }

    public Date getEstimatedDt() {
        return estimatedDt;
    }

    public void setEstimatedDt(Date estimatedDt) {
        this.estimatedDt = estimatedDt;
    }

    public Date getFinishedDt() {
        return finishedDt;
    }

    public void setFinishedDt(Date finishedDt) {
        this.finishedDt = finishedDt;
    }

    private Integer id;
    private Integer versionId;
    private String name;
    private String description;
    private Date givenDt;
    private Date estimatedDt;
    private Date finishedDt;

    @Override
    public String toString() {
        return this.getName();
    }
}
