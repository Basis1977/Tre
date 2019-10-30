package com.tutionbooks;
import oracle.iam.notification.api.NotificationService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.security.auth.login.LoginException;
import oracle.iam.notification.exception.EventException;
import oracle.iam.notification.exception.MultipleTemplateException;
import oracle.iam.notification.exception.NotificationException;
import oracle.iam.notification.exception.NotificationResolverNotFoundException;
import oracle.iam.notification.exception.TemplateNotFoundException;
import oracle.iam.notification.exception.UnresolvedNotificationDataException;
import oracle.iam.notification.exception.UserDetailsNotFoundException;
import oracle.iam.notification.vo.NotificationEvent;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.Platform;
public class CustomEmailProcessTask {
    public CustomEmailProcessTask() {
        super();
    }
    private NotificationService notificationService;
        public OIMClient getOIMClient(String oimUserName, String oimPassword,
                    Object oimURL) throws LoginException {
                java.util.Hashtable env = new java.util.Hashtable();
                env.put(oracle.iam.platform.OIMClient.JAVA_NAMING_FACTORY_INITIAL,
                        oracle.iam.platform.OIMClient.WLS_CONTEXT_FACTORY);
                env.put(oracle.iam.platform.OIMClient.JAVA_NAMING_PROVIDER_URL, oimURL);
                oracle.iam.platform.OIMClient client = new oracle.iam.platform.OIMClient(
                        env);
                client.login(oimUserName, oimPassword.toCharArray());
                return client;
            }

            public void sendEmail(String managerLogin, String beneficiaryID) throws LoginException,
                    UserDetailsNotFoundException, EventException,
                    UnresolvedNotificationDataException, TemplateNotFoundException,
                    MultipleTemplateException, NotificationResolverNotFoundException,
                    NotificationException {
                OIMClient oimClient = getOIMClient("xelsysadm", "Abcd1234",
                        "t3://idm.oraclefusion4all.com:14000");
                oracle.iam.notification.api.NotificationService notsvc = oimClient
                        .getService(oracle.iam.notification.api.NotificationService.class);
                oracle.iam.notification.vo.NotificationEvent notevent = new oracle.iam.notification.vo.NotificationEvent();
                String[] receiverUserIds = { managerLogin, beneficiaryID };
                notevent.setUserIds(receiverUserIds);
                notevent.setTemplateName("CustomEmailTemplateOnCreateUser");
                java.util.HashMap templateParams = new java.util.HashMap();
                templateParams.put("userId", beneficiaryID);
                templateParams.put("managerId", managerLogin);
                notevent.setSender(null);
                notevent.setParams(templateParams);
                System.out.println("Sending Email");
                notsvc.notify(notevent);
            }
}
