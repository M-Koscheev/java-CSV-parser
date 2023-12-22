import java.util.List;
import java.sql.Connection;
import org.postgresql.ds.PGSimpleDataSource;

public class App {
    public static void main(String[] args) throws Exception {
        PGSimpleDataSource dataSource = Database.createDataSource();
        Connection conn = dataSource.getConnection();

        List<String[]> lines = Parser.Parse();
        DataHandilng.putData(lines, conn);

        Tasks.buildGraph(conn);
        Tasks.findYongestFrench(conn);
        Tasks.findUSAEnergyRichest(conn);
        conn.close();
    }
}
