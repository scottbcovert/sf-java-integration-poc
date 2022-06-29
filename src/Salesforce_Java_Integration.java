import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import java.net.URLEncoder;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
public class Salesforce_Java_Integration {
	
	public Salesforce_Java_Integration(final Salesforce_Credentials creds)
	{
		final String baseUri;
		final Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
		final Header oauthHeader;
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

			System.out.println("loginURL---- "+loginURL);
			HttpPost httpPost = new HttpPost(loginURL);
			{
				// POST request to Login
				response = httpclient.execute(httpPost);
				if(null!=response) {
					final int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == HttpStatus.SC_OK) {
						System.out.println("Login Successful");
					}
					else{
						System.out.println("Error whie Login");
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
					baseUri = loginInstanceUrl;
					oauthHeader = new BasicHeader("Authorization", "OAuth "
					                                                    + loginAccessToken) ;
					// release connection
					httpPost.releaseConnection();
					System.out.println("oauthHeader===>>>"+oauthHeader);
					System.out.println("prettyPrintHeader===>>>"+prettyPrintHeader);
					System.out.println("baseUri===>>>"+baseUri);
				}
				else {
					System.out.println("Please check your internet connection !!!");
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}