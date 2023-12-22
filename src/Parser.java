import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
    private static final String path = "./lib/Forbes.csv";

    public static List<String[]> Parse() {
        ArrayList<String[]> records = new ArrayList<String[]>();
        String[] record = new String[7];
        Scanner scan;
        try {
            scan = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return records;
        }
        
        boolean first = true;
        while (scan.hasNext()) {
            record = scan.nextLine().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            if (first) {
                first = false;
                continue;
            }
            for (int i = 0; i < record.length; i++) {
                record[i] = record[i].trim();
            }
            records.add(record);
        }
        return records;

    }
}
