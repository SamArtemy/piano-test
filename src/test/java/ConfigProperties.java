import org.aeonbits.owner.Config;

public interface ConfigProperties extends Config {

    @DefaultValue("api.private.co")
    @Key("private_api")
    String apiPrivate();

    @DefaultValue("public.co")
    @Key("public_api")
    String apiPublic();

}
