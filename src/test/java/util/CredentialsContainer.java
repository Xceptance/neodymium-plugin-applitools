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

interface CredentialsContainer extends Mutable
{
    @Key("applitools.apiKey")
    String applitoolsApiKey();

    @Key("applitools.username")
    String username();

    @Key("applitools.password")
    String password();
}
