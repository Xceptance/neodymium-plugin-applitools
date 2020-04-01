package util;

import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Mutable;

@LoadPolicy(LoadType.MERGE)
@Sources(
{
  "file:config/dev-credentials.properties"
})

public interface Credentials extends Mutable
{
    @Key("apiKey")
    String applitoolsApiKey();

    @Key("username")
    String username();

    @Key("password")
    String password();
}
