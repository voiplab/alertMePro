TeamCity AlertMePro Notification Plugin
====================================

Description
-----------
AlertMePro is TeamCity Notification plugin which allows you to receive notifications from your TeamCity Server to Skype™

Installation
------------
To install plugin [download zip archive](https://github.com/voiplab/alertMePro/releases) it and copy it to Teamcity \<data directory\>/plugins (it is $HOME/.BuildServer/plugins under Linux and C:\Users\<user_name>\.BuildServer\plugins under Windows).
For more information, take a look at [official documentation](https://confluence.jetbrains.com/display/TCD10/Installing+Additional+Plugins)

Configuration
-------------
For more information, take a look at [AlertMePro documentation](https://alertme.pro/installation/)

Open AlertMePro main settings page. Follow the link http(s)://YOUR_TEAMCITY_WEB_UI/admin/admin.html?item=AlertMe-Notifier
1.1. Enter API Server parameter https://api.alertme.pro.
1.2. Enter API Key If you have already purchased the Subscription. If you want to get a trial version of the plugin left this field blank.
1.3. Enter Skype Bot Client Id and Skype Bot Client Password. (Step 2.3)

![settings main](https://alertme.pro/wp-content/uploads/2016/10/Screenshot-at-2016-10-17-174126-768x466.png)

2.1. Configuration is accessible from user preferences, in `Notification Rules` tabs. Select `AlertMe-Notifier` sub item.

![settings notification](https://alertme.pro/wp-content/uploads/2016/10/Screenshot-at-2016-10-17-174140-1024x697.png)

License
-------
[Apache](https://github.com/voiplab/alertMePro/blob/master/LICENSE.txt)
