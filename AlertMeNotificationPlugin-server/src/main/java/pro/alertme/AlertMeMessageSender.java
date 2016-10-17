package pro.alertme;

import com.google.common.base.Strings;
import jetbrains.buildServer.serverSide.SRunningBuild;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.xerces.impl.dv.util.Base64;
import java.io.*;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Nikita Rukavkov
 */
public class AlertMeMessageSender {
    private static final String BUILD_FULL_NAME_KEY = "BUILD_FULL_NAME_KEY";
    private static final String BUILD_STATUS_KEY = "BUILD_STATUS_KEY";
    private static final String TYPE = "AlertMeNotifier";

    public static void sendMessage(AlertMeServerSettings serverSettings, SRunningBuild runningBuild, String skype_id, String message) {
        try {

            String api_key = (Strings.isNullOrEmpty(serverSettings.getLicenseKey()) ? "TRIAL" : serverSettings.getLicenseKey());
            String skype_client_id = serverSettings.getSkypeClientId();
            String skype_client_password = serverSettings.getSkypeClientPassword();
            String api_server = serverSettings.getServerURL();

            if (Strings.isNullOrEmpty(skype_client_id) || Strings.isNullOrEmpty(skype_client_password) || Strings.isNullOrEmpty(api_server))
            {
               return;
            }

            String call_url="/queue/add/"+api_key+"/" + skype_id + "/" + Base64.encode((message).getBytes()) + "/" + skype_client_id + "/" +skype_client_password;

            if(api_server.startsWith("https://"))
                loadJSON_HTTPS(api_server,call_url);

            if (api_server.startsWith("http://"))
                loadJSON_HTTP(api_server,call_url);
        }
        catch (Exception e) {
            //logger.error(e.getMessage(), e);
        }
    }

    /*public static void appendToFile(Exception e) {
        try {
            FileWriter fstream = new FileWriter("c:\\exception.txt", true);
            BufferedWriter out = new BufferedWriter(fstream);
            PrintWriter pWriter = new PrintWriter(out, true);
            e.printStackTrace(pWriter);
        }
        catch (Exception ie) {
            throw new RuntimeException("Could not write Exception to file", ie);
        }
    }*/

    public static void loadJSON_HTTPS(String server, String server_url) throws IOException {
        String httpsURL = server + server_url;
        URL myurl = new URL(httpsURL);
        HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
        InputStream ins = con.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins);
        BufferedReader in = new BufferedReader(isr);

        String inputLine;

        while ((inputLine = in.readLine()) != null)
        {
//            System.out.println(inputLine);
        }

        in.close();
    }
    public static void loadJSON_HTTP(String server, String server_url) throws IOException {
        String httpsURL = server + server_url;
        URL myurl = new URL(httpsURL);
        HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
        InputStream ins = con.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins);
        BufferedReader in = new BufferedReader(isr);

        String inputLine;

        while ((inputLine = in.readLine()) != null)
        {
//            System.out.println(inputLine);
        }

        in.close();
    }

}