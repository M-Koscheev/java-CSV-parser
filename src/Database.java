import javax.activation.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

public class Database {
    private static final String url = "jdbc:postgresql://localhost:5432/JavaForbes?currentSchema=public&user=postgres&password=123678";
    private static PGSimpleDataSource dataSourse = null;
    public static PGSimpleDataSource createDataSource() {
        if (dataSourse == null) {
            dataSourse = new PGSimpleDataSource();
            dataSourse.setURL(url);
        }
        return dataSourse;
    }
}
