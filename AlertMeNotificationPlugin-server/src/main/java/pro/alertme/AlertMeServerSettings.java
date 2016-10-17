package pro.alertme;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Nikita Rukavkov
 */
@XStreamAlias("alertmepro_plugin_server")
public class AlertMeServerSettings {

    public static final String LICENSE_KEY = "license_key";
    public static final String SERVER_URL = "server_url";
    public static final String SKYPE_ALIAS = "SKYPE_ALIAS";
    public static final String SKYPE_CLIENT_ID = "skype_client_id";
    public static final String SKYPE_CLIENT_PASSWORD = "skype_client_password";

    @XStreamAlias("server_url")
    private String serverURL = "https://api.alertme.pro";

    @XStreamAlias("license_key")
    private String license_key = "";

    @XStreamAlias("skype_client_id")
    private String skype_client_id = "";

    @XStreamAlias("skype_client_password")
    private String skype_client_password = "";

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getLicenseKey() {
        return license_key;
    }

    public void setLicenseKey(String license_key) {
        this.license_key = license_key;
    }

    public String getSkypeClientId() {
        return skype_client_id;
    }

    public void setSkypeClientId(String skype_client_id) {
        this.skype_client_id = skype_client_id;
    }

    public String getSkypeClientPassword() {
        return skype_client_password;
    }

    public void setSkypeClientPassword(String skype_client_password) {
        this.skype_client_password = skype_client_password;
    }
}