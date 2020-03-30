package util;

import static com.google.common.base.Strings.isNullOrEmpty;

import org.aeonbits.owner.ConfigFactory;

public class Credentials
{
    private CredentialsContainer credentials = ConfigFactory.create(CredentialsContainer.class);

    public String getApiKey()
    {
        if (isNullOrEmpty(credentials.apiKey()))
        {
            return System.getenv("API_KEY");
        }
        return credentials.apiKey();
    }

    public String getUsername()
    {
        if (isNullOrEmpty(credentials.username()))
        {
            return System.getenv("API_USERNAME");
        }
        return credentials.username();
    }

    public String getPassword()
    {
        if (isNullOrEmpty(credentials.password()))
        {
            return System.getenv("API_PASSWORD");
        }
        return credentials.password();
    }
}
