package com.tutionbooks;
import java.io.*;
import java.util.HashMap;
import oracle.iam.api.OIMService;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.vo.UserManagerResult;
import oracle.iam.platform.Platform;
import oracle.iam.scheduler.vo.TaskSupport;

public class DisableUsersFromCsvST extends TaskSupport {
    public DisableUsersFromCsvST() {
        super();
    }

    @Override
    public void execute(HashMap hashMap) {
        String className = "::DisableUsersFromCsvST::";
               String methodName = "::execute::";
               UserManagerResult DisableUserEntityId = null;
               String csvFilePath = (String)hashMap.get("CSV Location");
               String delimiter = (String)hashMap.get("File Delimiter");
               System.out.println((new StringBuilder(String.valueOf(className))).append(methodName).append("File Path:::").append(csvFilePath).toString());
               System.out.println((new StringBuilder(String.valueOf(className))).append(methodName).append("Delimiter:::").append(delimiter).toString());
               try
               {
                   FileReader file_reader = new FileReader(csvFilePath);
                   BufferedReader actlFileBufRdr = new BufferedReader(file_reader);
                   String newLine;
                   while((newLine = actlFileBufRdr.readLine()) != null) 
                   {
                       if(newLine.trim().length() != 0)
                       {
                           String array[] = newLine.split(delimiter);
                           String adid = array[0];
                           System.out.println((new StringBuilder(String.valueOf(className))).append(methodName).append("::::UserID::::").append(adid).toString());
                           UserManager UserIntf;
                           UserIntf = (UserManager)Platform.getService(UserManager.class);
                           DisableUserEntityId = UserIntf.disable(adid, true);
                           System.out.println((new StringBuilder(String.valueOf(className))).append(methodName).append("::::User Disabled::::EntityID::::").append(DisableUserEntityId).toString());
                       }
                   }
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
    }

    @Override
    public HashMap getAttributes() {
        return null;
    }

    @Override
    public void setAttributes() {
    }
}
