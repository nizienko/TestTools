package TestTools.database;

import java.text.SimpleDateFormat;

/**
 * Created by def on 04.11.14.
 */
public abstract class AbstractMapper {
    protected SimpleDateFormat dateFormat;

    public AbstractMapper() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
