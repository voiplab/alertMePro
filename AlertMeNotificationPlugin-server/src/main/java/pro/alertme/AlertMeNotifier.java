package pro.alertme;

import com.google.common.base.Strings;
import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.Build;
import jetbrains.buildServer.notification.NotificationRulesManager;
import jetbrains.buildServer.notification.Notificator;
import jetbrains.buildServer.notification.NotificatorRegistry;
import jetbrains.buildServer.responsibility.ResponsibilityEntry;
import jetbrains.buildServer.responsibility.TestNameResponsibilityEntry;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.serverSide.mute.MuteInfo;
import jetbrains.buildServer.serverSide.problems.BuildProblemInfo;
import jetbrains.buildServer.tests.TestName;
import jetbrains.buildServer.users.NotificatorPropertyKey;
import jetbrains.buildServer.users.PropertyKey;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.vcs.VcsRoot;
import org.jetbrains.annotations.NotNull;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.io.IOException;
import java.util.*;

/**
 * Created by Nikita Rukavkov
 */
public class AlertMeNotifier implements Notificator {

    private static final Logger LOGGER = Logger.getInstance(AlertMeNotifier.class.getName());

    public static final String TYPE = "AlertMeNotifier";
    private static final String TYPE_NAME = "AlertMe-Notifier";
    private static final PropertyKey SKYPE_ALIAS_KEY = new NotificatorPropertyKey(TYPE, AlertMeServerSettings.SKYPE_ALIAS);
    private AlertMeServerSettings serverSettings;

    public AlertMeNotifier(NotificatorRegistry notificatorRegistry, NotificationRulesManager notificationRulesManager, @NotNull AlertMeServerSettings serverSettings) throws IOException {

        ArrayList<UserPropertyInfo> userProps = new ArrayList<UserPropertyInfo>();
        userProps.add(new UserPropertyInfo(AlertMeServerSettings.SKYPE_ALIAS, "Skype ID"));
        notificatorRegistry.register(this, userProps);
        this.serverSettings = serverSettings;
    }

    public String getDelay(SRunningBuild sRunnerBuild)
    {
        PeriodFormatter durationFormatter = new PeriodFormatterBuilder()
                .printZeroRarelyFirst()
                .appendHours()
                .appendSuffix(" hour", " hours")
                .appendSeparator(" ")
                .printZeroRarelyLast()
                .appendMinutes()
                .appendSuffix(" minute", " minutes")
                .appendSeparator(" and ")
                .appendSeconds()
                .appendSuffix(" second", " seconds")
                .toFormatter();

        Duration buildDuration = new Duration(1000*sRunnerBuild.getDuration());
        return  durationFormatter.print(buildDuration.toPeriod());
    }

    @Override
    public void notifyBuildStarted(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        String message = String.format("Project '%s' build started. %s" , sRunningBuild.getFullName(), this.formatNotificationBuildID(sRunningBuild));
        this.sendMessageToSkype(sRunningBuild, sUsers, message);
    }

    @Override
    public void notifyBuildSuccessful(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        String message = String.format("Project '%s' build successfully in %s. %s" , sRunningBuild.getFullName(), getDelay(sRunningBuild), this.formatNotificationBuildID(sRunningBuild));
        this.sendMessageToSkype(sRunningBuild, sUsers, message);
    }

    @Override
    public void notifyBuildFailed(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        String message = String.format("Project '%s' build failed! ( %s ) %s" , sRunningBuild.getFullName(),getDelay(sRunningBuild), this.formatNotificationBuildID(sRunningBuild));
        this.sendMessageToSkype(sRunningBuild, sUsers, message);
    }

    @Override
    public void notifyBuildFailedToStart(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        String message = String.format("Project '%s' build failed to start. %s" , sRunningBuild.getFullName(), this.formatNotificationBuildID(sRunningBuild));
        this.sendMessageToSkype(sRunningBuild, sUsers, message);
    }

    @Override
    public void notifyBuildFailing(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        String message = String.format("Project '%s' build failing. %s" , sRunningBuild.getFullName(), this.formatNotificationBuildID(sRunningBuild));
        this.sendMessageToSkype(sRunningBuild, sUsers, message);
    }

    @Override
    public void notifyBuildProbablyHanging(SRunningBuild sRunningBuild, Set<SUser> sUsers) {
        String message = String.format("Project '%s' probably hanging. %s" , sRunningBuild.getFullName(), this.formatNotificationBuildID(sRunningBuild));
        this.sendMessageToSkype(sRunningBuild, sUsers, message);
    }

    private String formatNotificationBuildID(SRunningBuild sRunningBuild)
    {
        return String.format("[Build ID: #%s]" , sRunningBuild.getBuildId());
    }

    private void sendMessageToSkype(SRunningBuild sRunningBuild, Set<SUser> sUsers, String message)
    {
        for (SUser user : sUsers) {
            String alias = user.getPropertyValue(SKYPE_ALIAS_KEY);
            if (!Strings.isNullOrEmpty(alias)) {
                AlertMeMessageSender.sendMessage(serverSettings, sRunningBuild, alias, message);
            }
        }
    }

    @Override
    public void notifyLabelingFailed(Build build, VcsRoot vcsRoot, Throwable throwable, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyResponsibleChanged(SBuildType sBuildType, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyResponsibleAssigned(SBuildType sBuildType, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyResponsibleChanged(TestNameResponsibilityEntry testNameResponsibilityEntry, TestNameResponsibilityEntry testNameResponsibilityEntry1, SProject sProject, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyResponsibleAssigned(TestNameResponsibilityEntry testNameResponsibilityEntry, TestNameResponsibilityEntry testNameResponsibilityEntry1, SProject sProject, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyResponsibleChanged(Collection<TestName> collection, ResponsibilityEntry responsibilityEntry, SProject sProject, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyResponsibleAssigned(Collection<TestName> collection, ResponsibilityEntry responsibilityEntry, SProject sProject, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyBuildProblemResponsibleAssigned(Collection<BuildProblemInfo> collection, ResponsibilityEntry responsibilityEntry, SProject sProject, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyBuildProblemResponsibleChanged(Collection<BuildProblemInfo> collection, ResponsibilityEntry responsibilityEntry, SProject sProject, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyTestsMuted(Collection<STest> collection, MuteInfo muteInfo, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyTestsUnmuted(Collection<STest> collection, MuteInfo muteInfo, SUser sUser, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyBuildProblemsMuted(Collection<BuildProblemInfo> collection, MuteInfo muteInfo, Set<SUser> sUsers) {
        
    }

    @Override
    public void notifyBuildProblemsUnmuted(Collection<BuildProblemInfo> collection, MuteInfo muteInfo, SUser sUser, Set<SUser> sUsers) {
        
    }

    @NotNull
    @Override
    public String getNotificatorType() {
        return TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return TYPE_NAME;
    }

}