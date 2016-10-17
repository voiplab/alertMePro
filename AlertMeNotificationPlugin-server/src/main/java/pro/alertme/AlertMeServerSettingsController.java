package pro.alertme;

import com.google.common.base.Strings;
import com.thoughtworks.xstream.XStream;
import jetbrains.buildServer.controllers.AjaxRequestProcessor;
import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.ServerPaths;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Nikita Rukavkov
 */
public class AlertMeServerSettingsController extends BaseController {

    private static final String CONFIG_FILE = "AlertMeServerConfig.xml";
    private static final String CONTROLLER_PATH = "/saveSettings.html";
    private String configFilePath;
    private AlertMeServerSettings configuration;

    public AlertMeServerSettingsController(@NotNull SBuildServer server, @NotNull ServerPaths serverPaths,
                                           @NotNull WebControllerManager manager,
                                           @NotNull AlertMeServerSettings configuration) throws IOException {
        manager.registerController(CONTROLLER_PATH, this);
        configFilePath = (new File(serverPaths.getConfigDir(), CONFIG_FILE)).getCanonicalPath();
        this.configuration = configuration;
    }

    public void initialize() {
        File file = new File(configFilePath);
        if (file.exists()) {
            try {
                loadConfiguration();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            try {
                saveConfiguration();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Nullable
    @Override
    protected ModelAndView doHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        new AjaxRequestProcessor().processRequest(httpServletRequest, httpServletResponse, new AjaxRequestProcessor.RequestHandler() {
            @Override
            public void handleRequest(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull org.jdom.Element element) {
                handleServerConfigurationChange(httpServletRequest);
            }
        });
        return null;
    }

    private void handleServerConfigurationChange(HttpServletRequest request) {
        String serverURL = request.getParameter(AlertMeServerSettings.SERVER_URL);
        String licenseKey = request.getParameter(AlertMeServerSettings.LICENSE_KEY);
        String skypeClientID = request.getParameter(AlertMeServerSettings.SKYPE_CLIENT_ID);
        String skypeClientPassword = request.getParameter(AlertMeServerSettings.SKYPE_CLIENT_PASSWORD);

        boolean isError = false;

        if (!Strings.isNullOrEmpty(serverURL)) {
            configuration.setServerURL(serverURL);
        } else {
            isError = true;
        }

        configuration.setLicenseKey(licenseKey);

        if (!Strings.isNullOrEmpty(skypeClientID)) {
            configuration.setSkypeClientId(skypeClientID);
        }

        if (!Strings.isNullOrEmpty(skypeClientPassword)) {
            configuration.setSkypeClientPassword(skypeClientPassword);
        }


        if (!isError) {
            try {
                saveConfiguration();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            getOrCreateMessages(request).addMessage("settingsMessage","Settings successfully saved.");
        } else {
            getOrCreateMessages(request).addMessage("settingsMessage","Please fill in all fields correctly!");
        }
    }

    private void saveConfiguration() throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(getClass());
        File file = new File(configFilePath);
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        xstream.toXML(configuration, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private void loadConfiguration() throws IOException {
        XStream xstream = new XStream();
        xstream.setClassLoader(getClass().getClassLoader());
        xstream.processAnnotations(AlertMeServerSettings.class);
        FileReader fileReader = new FileReader(configFilePath);
        AlertMeServerSettings loadedConfig = (AlertMeServerSettings) xstream.fromXML(fileReader);
        fileReader.close();

        // set configuration
        configuration.setServerURL(loadedConfig.getServerURL());
        configuration.setLicenseKey(loadedConfig.getLicenseKey());
        configuration.setSkypeClientId(loadedConfig.getSkypeClientId());
        configuration.setSkypeClientPassword(loadedConfig.getSkypeClientPassword());
    }

}