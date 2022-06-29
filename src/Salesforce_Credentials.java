public class Salesforce_Credentials {
    private String userName;
    private String passWord;
    private String securityToken;
    private String passWordAndToken;
    private String clientId;
    private String clientSecret;
    private String loginEnvUrl;

    public Salesforce_Credentials(final String userName, final String passWord, final String securityToken, final String clientId, final String clientSecret, final String loginEnvUrl) {
        this.userName = userName;
        this.passWord = passWord;
        this.securityToken = securityToken;
        this.passWordAndToken = passWord + securityToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.loginEnvUrl = loginEnvUrl;
    }

    public String getUserName() { return this.userName; }
    public String getPassWord() { return this.passWord; }
    public String getSecurityToken() { return this.securityToken; }
    public String getPassWordAndToken() { return this.passWordAndToken; }
    public String getClientId() { return this.clientId; }
    public String getClientSecret() { return this.clientSecret; }
    public String getLoginEnvUrl() { return this.loginEnvUrl; }
}
