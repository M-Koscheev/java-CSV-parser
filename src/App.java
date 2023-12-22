import java.sql.Statement;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.postgresql.ds.PGSimpleDataSource;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        PGSimpleDataSource dataSource = Database.createDataSource();
        Connection conn = dataSource.getConnection();
        
        // PreparedStatement stmt = conn.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'");
        // ResultSet rs = stmt.executeQuery();
        // ResultSet rs = conn.getMetaData().getTables(null, null, "TABLE_NAME", null);
        // while (rs.next()) {
        //     System.out.println(rs.getString(3));
        // }

        List<String[]> lines = Parser.Parse();
        DataHandilng.putData(lines, conn);
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT (title) FROM \"Sources\"" );
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        rs.close();
    }
}
