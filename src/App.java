import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(final String[] args)
	{
		@SuppressWarnings("unused")		
        Dotenv dotenv = Dotenv.load();
        final String userName = dotenv.get("USERNAME");
        final String passWord = dotenv.get("PASSWORD");
        final String securityToken = dotenv.get("SECURITY_TOKEN");
        final String clientId = dotenv.get("CLIENT_ID");
        final String clientSecret = dotenv.get("CLIENT_SECRET");
        final String loginEnvUrl = dotenv.get("LOGIN_ENV_URL");
        Salesforce_Credentials creds = new Salesforce_Credentials(userName, passWord, securityToken, clientId, clientSecret, loginEnvUrl);        
        Salesforce_Java_Integration sf = new Salesforce_Java_Integration(creds);
        sf.login();
	}
}
