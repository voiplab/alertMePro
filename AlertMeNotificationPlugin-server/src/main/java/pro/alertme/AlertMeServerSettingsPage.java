package pro.alertme;

import pro.alertme.AlertMeServerSettings;
import jetbrains.buildServer.controllers.admin.AdminPage;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Nikita Rukavkov
 */
public class AlertMeServerSettingsPage extends AdminPage {

    private static final String PLUGIN_NAME = "AlertMe-Notifier";
    private static final String TAB_TITLE = "AlertMe Notifier";
    private static final String PAGE = "serverSettings.jsp";

    private AlertMeServerSettings configuration;

    public AlertMeServerSettingsPage(@NotNull PagePlaces pagePlaces, @NotNull PluginDescriptor descriptor,
                                     @NotNull AlertMeServerSettings configuration) {
        super(pagePlaces);
        this.configuration = configuration;
        setPluginName(PLUGIN_NAME);
        setTabTitle(TAB_TITLE);
        setIncludeUrl(descriptor.getPluginResourcesPath(PAGE));
        register();
    }

    @NotNull
    @Override
    public String getGroup() {
        return SERVER_RELATED_GROUP;
    }

    @Override
    public boolean isAvailable(@NotNull HttpServletRequest request) {
        return true;
    }

    @Override
    public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request) {
        super.fillModel(model, request);
        model.put(AlertMeServerSettings.SERVER_URL, configuration.getServerURL());
        model.put(AlertMeServerSettings.LICENSE_KEY, configuration.getLicenseKey());
        model.put(AlertMeServerSettings.SKYPE_CLIENT_ID, configuration.getSkypeClientId());
        model.put(AlertMeServerSettings.SKYPE_CLIENT_PASSWORD, configuration.getSkypeClientPassword());
    }

}