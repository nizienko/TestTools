package TestTools.database.version;

/**
 * Created by def on 30.10.14.
 */
public class Version {

    public Version() {

    }

    public Version(Integer projectId, String name, String description) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    private Integer id;
    private Integer projectId;
    private String name;
    private String description;

    @Override
    public String toString() {
        return this.getName();
    }
}
