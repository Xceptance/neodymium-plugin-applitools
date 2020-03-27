package util.applitools;

import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Mutable;

@LoadPolicy(LoadType.MERGE)
@Sources(
{
  "file:config/dev-applitools.properties", "file:config/applitools.properties"
})

public interface ApplitoolsConfiguration extends Mutable
{

    @Key("applitools.apiKey")
    String apiKey();

    @Key("applitools.projectName")
    String projectName();

    @Key("applitools.matchLevel")
    @DefaultValue("STRICT")
    String matchLevel();

    @Key("applitools.throwException")
    String throwException();

    @Key("applitools.batch")
    String batch();

    String setProperty(String key, String value);
}
