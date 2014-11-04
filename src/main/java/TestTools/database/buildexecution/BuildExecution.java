package TestTools.database.buildexecution;

/**
 * Created by def on 03.11.14.
 */
public class BuildExecution {
    private Integer id;
    private Integer buildId;
    private String name;

    public BuildExecution() {

    }

    public BuildExecution(Integer buildId, String name) {
        this.name = name;
        this.buildId = buildId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuildId() {
        return buildId;
    }

    public void setBuildId(Integer buildId) {
        this.buildId = buildId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
