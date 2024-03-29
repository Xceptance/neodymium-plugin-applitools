package util.applitools;

import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Mutable;

import com.applitools.eyes.MatchLevel;

@LoadPolicy(LoadType.MERGE)
@Sources(
{
  "${applitools.temporaryConfigFile}", "system:properties",
  "system:env", "file:config/dev-applitools.properties", "file:config/applitools.properties"
})

public interface ApplitoolsConfiguration extends Mutable
{
    @Key("applitools.apiKey")
    String applitoolsApiKey();

    @Key("applitools.projectName")
    String projectName();

    @Key("applitools.matchLevel")
    @DefaultValue("STRICT")
    MatchLevel matchLevel();

    @Key("applitools.throwException")
    @DefaultValue("false")
    boolean throwException();

    @Key("applitools.hideCaret")
    @DefaultValue("true")
    boolean hideCaret();

    @Key("applitools.waitBeforeScreenshot")
    @DefaultValue("100")
    int waitBeforeScreenshot();

    @Key("applitools.batch")
    String batch();
}
