import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import java.net.URLEncoder;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Salesforce_Java_Integration {
	private Salesforce_Credentials creds;
    private String baseUri;
    private Header oauthHeader;
	
    public Salesforce_Java_Integration(final Salesforce_Credentials creds)
	{
		this.creds = creds;
    }
        
    public void login()
    {
        final String userName  = creds.getUserName();
		final String passWordAndToken  = creds.getPassWordAndToken();
		final String clientId     = creds.getClientId();
		final String clientSecret = creds.getClientSecret();
		final String loginEnvUrl  = creds.getLoginEnvUrl();
		final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";

		HttpResponse response = null;
		HttpClient httpclient = HttpClientBuilder.create().build();
		// Create login request URL
		String loginURL = "";
		try {
			loginURL = loginEnvUrl + GRANTSERVICE +
					"&client_id="+URLEncoder.encode(clientId, "UTF-8")+
					"&client_secret=" +clientSecret +"&username=" +
					URLEncoder.encode(userName, "UTF-8") +
				"&password=" +URLEncoder.encode(passWordAndToken , "UTF-8");

			HttpPost req = new HttpPost(loginURL);
			{
				// POST request to Login
				response = httpclient.execute(req);
				if(null!=response) {
					final int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == HttpStatus.SC_OK) {
						System.out.println("Login Successful");
					}
					else{
						System.out.println("Error encountered while logging in");
					}
					String getResult = null;
					getResult = EntityUtils.toString(response.getEntity());
					JSONObject jsonObject = null;
					String loginAccessToken = null;
					String loginInstanceUrl = null;
					jsonObject = (JSONObject)
                                                              new JSONTokener(getResult).nextValue();
					loginAccessToken = jsonObject.getString("access_token");
					loginInstanceUrl = jsonObject.getString("instance_url");
					this.baseUri = loginInstanceUrl;
					this.oauthHeader = new BasicHeader("Authorization", "Bearer "
					                                                    + loginAccessToken) ;
					// release connection
					req.releaseConnection();
				}
				else {
					System.out.println("Please check your internet connection!");
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

    public Salesforce_Query_Results query(final String query)
    {
        final String apiVersion = creds.getApiVersion();
        HttpResponse response = null;
		HttpClient httpclient = HttpClientBuilder.create().build();
		// Create query request URL
        String urlEncodedQuery = null;
		String queryURL = "";
        Salesforce_Query_Results results = null;
		try {

            urlEncodedQuery = URLEncoder.encode(query, "UTF-8");
            queryURL = this.baseUri + "/services/data/v"+ apiVersion +"/query/?q=" + urlEncodedQuery;

			HttpGet req = new HttpGet(queryURL);
            req.addHeader("Content-Type","application/json");
            req.addHeader(this.oauthHeader);
			{
				// POST request
				response = httpclient.execute(req);
				if(null!=response) {
					final int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == HttpStatus.SC_OK) {
						System.out.println("Query Successful");
					}
					else{
						System.out.println("Error encountered while running query");
					}
					String getResult = null;
					getResult = EntityUtils.toString(response.getEntity());
					JSONObject resp = null;

					resp = (JSONObject) new JSONTokener(getResult).nextValue();

                    results = new Salesforce_Query_Results(resp);

					// release connection
					req.releaseConnection();
				}
				else {
					System.out.println("Please check your internet connection !!!");
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
        return results;
    }
}