<%@ include file="/include.jsp"%>

<script type="text/javascript">
  function saveSettings(form)
  {
     var server_url = form.server_url.value;
     var license_key = form.license_key.value;
     var skype_client_id = form.skype_client_id.value;
     var skype_client_password = form.skype_client_password.value;

     BS.ajaxRequest($('saveSettingsForm').action, {
        parameters: 'server_url=' + server_url + '&license_key=' + license_key + '&skype_client_id=' + skype_client_id + '&skype_client_password=' + skype_client_password,
        onComplete: function(transport) {
        if (transport.responseXML) {
                  $('saveSettingsContainer').refresh();
              }
            }
        });
  }
</script>

<bs:refreshable containerId="saveSettingsContainer" pageUrl="${pageUrl}">
<c:url var="actionUrl" value="/saveSettings.html"/>
<form action="${actionUrl}" id="saveSettingsForm" method="POST" >
<h2>Common settings
    <a id="" class="helpIcon" onclick="BS.Util.showHelp(event, 'https://alertme.pro/installation/', {width: 0, height: 0}); return false"
            style="" href="https://alertme.pro/installation/" title="View help" showdiscardchangesmessage="false">
        <i class="icon icon16 tc-icon_help_small"></i>
    </a>
</h2>
<table class="runnerFormTable">
      <tbody>
      <tr>
        <th><label for="API Server">API Server:<span class="mandatoryAsterix" title="Mandatory field">*</span></label></th>
        <td><input type="text" name="server_url" value="${server_url}" class="textField" /></td>
      </tr>
      <tr>
        <th><label for="License Key">API Key:</label></th>
        <td>
            <input type="text" name="license_key" value="${license_key}" class="textField" />
            <span class="smallNote">Left this field blank to use the plugin in trial mode</span>
        </td>
        </tr>
      </tbody>
</table>

<h2>Personal Skype Bot Settings
    <a id="" class="helpIcon" onclick="BS.Util.showHelp(event, 'https://alertme.pro/skype-teamcity-personal-bot-creating/', {width: 0, height: 0}); return false"
            style="" href="https://alertme.pro/skype-teamcity-personal-bot-creating/" title="View help" showdiscardchangesmessage="false">
        <i class="icon icon16 tc-icon_help_small"></i>
    </a>
</h2>

<table class="runnerFormTable">
      <tbody>
        <tr>
            <th><label for="Client ID">Skype Bot ID:<span class="mandatoryAsterix" title="Mandatory field">*</span></label></th>
            <td><input type="text" name="skype_client_id" value="${skype_client_id}" class="textField" /></td>
        </tr>
        <tr>
            <th><label for="Client Password">Skype Bot Password:<span class="mandatoryAsterix" title="Mandatory field">*</span></label></th>
            <td><input type="text" name="skype_client_password" value="${skype_client_password}" class="textField" /></td>
        </tr>
      </tbody>
</table>
<input type="button" value="Save" class="btn btn_primary submitButton" onClick="saveSettings(this.form)"/>

</form>
<bs:messages key="settingsMessage"/>
</bs:refreshable>