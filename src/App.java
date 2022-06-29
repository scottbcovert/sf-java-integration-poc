import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(final String[] args)
	{
		Dotenv dotenv = Dotenv.load();
        final String userName = dotenv.get("USERNAME");
        final String passWord = dotenv.get("PASSWORD");
        final String securityToken = dotenv.get("SECURITY_TOKEN");
        final String clientId = dotenv.get("CLIENT_ID");
        final String clientSecret = dotenv.get("CLIENT_SECRET");
        final String loginEnvUrl = dotenv.get("LOGIN_ENV_URL");
        final String apiVersion = dotenv.get("API_VERSION");
        final String soqlQuery = dotenv.get("SOQL_QUERY");
        Salesforce_Credentials creds = new Salesforce_Credentials(userName, passWord, securityToken, clientId, clientSecret, loginEnvUrl, apiVersion);
        Salesforce_Java_Integration sf = new Salesforce_Java_Integration(creds);
        sf.login();
        Salesforce_Query_Results results = sf.query(soqlQuery);
        System.out.println("Records:");
        System.out.println(results.getRecords());
        System.out.println("");
        System.out.println("Total Number of Records:");
        System.out.println(results.getSize());
	}
}
