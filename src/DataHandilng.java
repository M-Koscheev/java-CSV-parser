import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataHandilng {
    public static void putData(List<String[]> lines, Connection conn) throws SQLException {
        Statement statement = conn.createStatement();

        for (String[] line: lines) {
            String country = line[4];
            statement.executeUpdate("INSERT INTO \"Countries\"(title) VALUES ('" + country + "') ON CONFLICT(title)" +
                        " DO UPDATE SET title=EXCLUDED.title RETURNING id", Statement.RETURN_GENERATED_KEYS);
            ResultSet res = statement.getGeneratedKeys();
            res.next();
            java.util.UUID country_id = res.getObject("id", java.util.UUID.class);

            if (line[1].charAt(0) == '"') {
                line[1] = line[1].substring(1, line[1].length() - 1);
            }
            statement.executeUpdate("INSERT INTO \"People\"(rank, name, networth, age, country_id) VALUES (" + 
                line[0] + ",$$" + line[1] + "$$," + line[2] + "," + line[3] + ",'" + country_id + "') ON CONFLICT(name)" +
                " DO UPDATE SET name=EXCLUDED.name RETURNING id", Statement.RETURN_GENERATED_KEYS);
            res = statement.getGeneratedKeys();
            res.next();
            java.util.UUID person_id = res.getObject("id", java.util.UUID.class);


            if (line[5].charAt(0) == '"') {
                line[5] = line[5].substring(1, line[5].length() - 1);
            }
            String[] sources = line[5].split(", ");
            for (String source: sources) {
                statement.executeUpdate("INSERT INTO \"Sources\"(title) VALUES ($$" + source + "$$) ON CONFLICT(title)" + 
                    " DO UPDATE SET title=EXCLUDED.title RETURNING id", Statement.RETURN_GENERATED_KEYS);
                res = statement.getGeneratedKeys();
                res.next();
                java.util.UUID source_id = res.getObject("id", java.util.UUID.class);
                statement.executeUpdate("INSERT INTO \"Source_Person\"(person_id, source_id) VALUES ('" + person_id + "','" + source_id + "') ON CONFLICT DO NOTHING");
            }


            if (line[6].charAt(0) == '"') {
                line[6] = line[6].substring(1, line[6].length() - 1);
            }
            String[] industries = line[6].split(", ");
            for (String industry: industries) {
                statement.executeUpdate("INSERT INTO \"Industries\"(title) VALUES ($$" + industry + "$$) ON CONFLICT(title)" +
                    " DO UPDATE SET title=EXCLUDED.title RETURNING id", Statement.RETURN_GENERATED_KEYS);
                res = statement.getGeneratedKeys();
                res.next();
                java.util.UUID industry_id = res.getObject("id", java.util.UUID.class);
                statement.executeUpdate("INSERT INTO \"Industry_Person\"(person_id, industry_id) VALUES ('" + person_id + "','" + industry_id + "') ON CONFLICT DO NOTHING");
            }
        }
        statement.close();
    }
}
