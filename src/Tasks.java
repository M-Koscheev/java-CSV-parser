import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


public class Tasks {
    public static void buildGraph(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();

        ResultSet res = statement.executeQuery("SELECT (SUM(networth), title) FROM \"People\" \r\n" + //
                "\tJOIN \"Countries\" ON \"People\".country_id = \"Countries\".id\r\n" + //
                "\tGROUP BY title;");

        DefaultPieDataset<String> dataset = new DefaultPieDataset<String>();

        while (res.next()) {
            String row = res.getString("row");
            String[] columns = row.substring(1, row.length() - 1).split(",");
            if (columns[1].charAt(0) == '"') {
                columns[1] = columns[1].substring(1, columns[1].length() - 1);
            }
            dataset.setValue(columns[1], Double.parseDouble(columns[0]));
        }
        JFreeChart chart = ChartFactory.createPieChart("Капитал участников Forbes по странам", dataset);
        ChartFrame frame1 = new ChartFrame(null, chart);
        frame1.setVisible(true);
        frame1.setSize(1200, 800);

        statement.close();
    }

    public static void findYongestFrench(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();

        ResultSet res = statement.executeQuery("SELECT name FROM \"People\" "  +
                "JOIN \"Countries\" ON \"People\".country_id = \"Countries\".id " +
                "WHERE title = 'France' AND networth > 10 " + 
                "ORDER BY age " + 
                "LIMIT 1");
        res.next();
        String name = res.getString("name");
        System.out.println("Самый молодой миллиардер из Франции, капитал которого превышает 10 млрд. - " + name + '\n');
        statement.close();
    }

    public static void findUSAEnergyRichest(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();

        ResultSet res = statement.executeQuery("SELECT name, \"Sources\".title AS source FROM " +
            "(SELECT \"People\".id, \"People\".name, \"People\".networth, \"Countries\".title as country, " +
                "\"Industry_Person\".industry_id, \"Industries\".title AS industry FROM \"People\" " +
                "JOIN \"Countries\" ON \"People\".country_id = \"Countries\".id " +
                "JOIN \"Industry_Person\" ON \"People\".id = \"Industry_Person\".person_id " +
                "JOIN \"Industries\" ON \"Industry_Person\".industry_id = \"Industries\".id " +
                "WHERE \"Countries\".title = 'United States' AND \"Industries\".title = 'Energy' " +
                "ORDER BY \"People\".networth DESC " +
                "LIMIT 1) " +
        "JOIN \"Source_Person\" ON id = \"Source_Person\".person_id " +
        "JOIN \"Sources\" ON \"Source_Person\".source_id = \"Sources\".id");

        res.next();
        System.out.println("Имя самого богатого бизнесмена из США - " + res.getString("name") + " , его компания - " + res.getString("source"));
        statement.close();
    }
}

