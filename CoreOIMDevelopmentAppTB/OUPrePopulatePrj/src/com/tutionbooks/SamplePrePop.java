package com.tutionbooks;
import java.util.logging.Level;

import oracle.iam.identity.exception.UserSearchException;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.api.UserManagerConstants;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.Platform;
import oracle.iam.platform.authz.exception.AccessDeniedException;
import oracle.iam.platform.entitymgr.vo.SearchCriteria;
import Thor.API.Exceptions.tcAPIException;
import Thor.API.Exceptions.tcColumnNotFoundException;
import Thor.API.Exceptions.tcFormNotFoundException;
import Thor.API.Exceptions.tcInvalidLookupException;
import Thor.API.Exceptions.tcInvalidValueException;
import Thor.API.Exceptions.tcNotAtomicProcessException;
import Thor.API.Exceptions.tcProcessNotFoundException;
import Thor.API.Exceptions.tcRequiredDataMissingException;
import Thor.API.Exceptions.tcVersionNotDefinedException;
import Thor.API.Exceptions.tcVersionNotFoundException;
import Thor.API.Operations.tcFormDefinitionOperationsIntf;
import Thor.API.Operations.tcFormInstanceOperationsIntf;
import Thor.API.Operations.tcLookupOperationsIntf;
import Thor.API.tcResultSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
public class SamplePrePop {
    public SamplePrePop() {
        super();
    }
    public String getLookupDecode(String lookupName, String codeKey) throws tcAPIException,
                                                               tcInvalidLookupException,
                                                               tcAPIException,
                                                               tcColumnNotFoundException {
              
                          final String methodName = ":: getLookupValues ::";
                         
                          tcLookupOperationsIntf lookupIntf = null;
                          lookupIntf = Platform.getService(tcLookupOperationsIntf.class);

                          String decodeKey = "";

                       
                  tcResultSet lookupResultSet;
                  lookupResultSet = lookupIntf.getLookupValues(lookupName);
                  for (int i = 0; i < lookupResultSet.getRowCount(); i++) {
                                          lookupResultSet.goToRow(i);
                      if (!codeKey.equals(lookupResultSet.getStringValue("Lookup Definition.Lookup Code Information.Code Key"))) {
                      } else {
                                                  decodeKey = lookupResultSet.getStringValue("Lookup Definition.Lookup Code Information.Decode");
                                          }
                  }
                              
                         
                    
                           
                      return decodeKey;
                
                  }
}
