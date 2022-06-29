import org.json.JSONArray;
import org.json.JSONObject;

public class Salesforce_Query_Results {
    private int size;
    private JSONArray records;

    public Salesforce_Query_Results(final JSONObject results) {
        this.size = results.getInt("totalSize");
        this.records = results.getJSONArray("records");
    }

    public int getSize() { return this.size; }
    public JSONArray getRecords() { return this.records; }
}
