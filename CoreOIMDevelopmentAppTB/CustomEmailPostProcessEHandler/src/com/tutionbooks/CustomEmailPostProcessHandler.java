package com.tutionbooks;
import java.io.Serializable;
import java.util.HashMap;

import oracle.adf.share.logging.ADFLogger;

import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.platform.Platform;
import oracle.iam.platform.context.ContextAware;
import oracle.iam.platform.entitymgr.EntityManager;
import oracle.iam.platform.kernel.spi.PostProcessHandler;
import oracle.iam.platform.kernel.spi.PreProcessHandler;
import oracle.iam.platform.kernel.spi.ValidationHandler;
import oracle.iam.platform.kernel.vo.AbstractGenericOrchestration;
import oracle.iam.platform.kernel.vo.BulkEventResult;
import oracle.iam.platform.kernel.vo.BulkOrchestration;
import oracle.iam.platform.kernel.vo.EventResult;
import oracle.iam.platform.kernel.vo.Orchestration;
public class CustomEmailPostProcessHandler implements PostProcessHandler {
    private ADFLogger eventLogger=ADFLogger.createADFLogger(CustomEmailPostProcessHandler.class);
    public CustomEmailPostProcessHandler() {
        super();
    }
    private String getParamaterValue(HashMap<String, Serializable> parameters, String key) {
        String value = (parameters.get(key) instanceof ContextAware)
        ? (String) ((ContextAware) parameters.get(key)).getObjectValue()
        : (String) parameters.get(key);
        return value;
      }

    @Override
    public EventResult execute(long processId, long eventId, Orchestration orchestration) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                eventLogger.entering(methodName, "params :["+processId+","+eventId+"]");
                EntityManager mgr=Platform.getService(EntityManager.class);
                HashMap map = orchestration.getParameters();
                String email=getParamaterValue(map, UserManagerConstants.AttributeName.EMAIL.getId());
                if(email==null||email.isEmpty()){
                        String firstName=getParamaterValue(map, UserManagerConstants.AttributeName.FIRSTNAME.getId());
                        String lastName=getParamaterValue(map, UserManagerConstants.AttributeName.LASTNAME.getId());
                        String generatedEmail=generateEmail(firstName,lastName);
                        HashMap modifyMap=new HashMap();
                        modifyMap.put(UserManagerConstants.AttributeName.EMAIL.getId(),generatedEmail);
                        try {
                            mgr.modifyEntity(orchestration.getTarget().getType(), orchestration.getTarget().getEntityId(), modifyMap);
                        }catch (Exception e){
                            eventLogger.severe("[" + methodName +  "] Error occured in updating user" + e.getMessage());
                            } 
                    }
                // asynch events must return null
                        return null;
    }
    private String generateEmail(String firstName, String lastName) {
            return firstName+lastName+"@tutionbooks.com";
        }

    @Override
    public boolean cancel(long l, long l1, AbstractGenericOrchestration abstractGenericOrchestration) {
        return false;
    }

    @Override
    public void initialize(HashMap<String, String> hashMap) {
    }

    @Override
    public void compensate(long l, long l1, AbstractGenericOrchestration abstractGenericOrchestration) {
    }

    @Override
    public BulkEventResult execute(long l, long l1, BulkOrchestration bulkOrchestration) {
        return null;
    }
}
