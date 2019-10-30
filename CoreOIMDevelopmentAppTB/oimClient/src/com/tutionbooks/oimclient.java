package com.tutionbooks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import javax.security.auth.login.LoginException;
import java.util.Set;
import oracle.iam.identity.exception.AccessDeniedException;
import oracle.iam.identity.exception.NoSuchOrganizationException;
import oracle.iam.identity.exception.NoSuchRoleException;
import oracle.iam.identity.exception.NoSuchUserException;
import oracle.iam.identity.exception.OrganizationAlreadyDeletedException;
import oracle.iam.identity.exception.OrganizationDisableException;
import oracle.iam.identity.exception.OrganizationDisableSubOrgsExistException;
import oracle.iam.identity.exception.OrganizationDisableSubOrgsUsersExistException;
import oracle.iam.identity.exception.OrganizationDisableUsersExistException;
import oracle.iam.identity.exception.UserAlreadyExistsException;
import oracle.iam.identity.exception.UserCreateException;
import oracle.iam.identity.exception.UserDeleteException;
import oracle.iam.identity.exception.UserDisableException;
import oracle.iam.identity.exception.UserEnableException;
import oracle.iam.identity.exception.UserLockException;
import oracle.iam.identity.exception.UserLookupException;
import oracle.iam.identity.exception.UserManagerException;
import oracle.iam.identity.exception.UserModifyException;
import oracle.iam.identity.exception.UserUnlockException;
import oracle.iam.identity.exception.ValidationFailedException;
import oracle.iam.identity.orgmgmt.api.OrganizationManager;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.OIMClient;
//
import oracle.iam.identity.exception.OrganizationManagerException;

import oracle.iam.identity.orgmgmt.api.OrganizationManager;
import oracle.iam.identity.orgmgmt.vo.Organization;
//
import oracle.iam.identity.exception.RoleAlreadyExistsException;
import oracle.iam.identity.exception.RoleCreateException;
import oracle.iam.identity.exception.RoleGrantException;
import oracle.iam.identity.exception.SearchKeyNotUniqueException;
import oracle.iam.identity.exception.ValidationFailedException;
import oracle.iam.identity.rolemgmt.api.RoleManager;
import oracle.iam.identity.rolemgmt.vo.Role;
//
import oracle.iam.platform.OIMClient;

//import oracle.iam.selfservice.exception.UserLookupException;

public class oimclient {
    
   UserManager userManager;	
    OrganizationManager organizationmanager;
    
    
    public oimclient() {
        super();
    }

    public static void main(String[] args) {
       oimclient oimclient = new oimclient();
       oimclient.OIMConnection();
        
            //oimclient.createuser("rajidm");
            //oimclient.disableuser("rajidm");
              //oimclient.enableuser("rajidm");
                //oimclient.lockUser("rajidm");
                //oimclient.unlockUser("rajidm");
                //oimclient.resetPassword("rajidm");
                //oimclient.modifyUser("rajidm");
                //oimclient.deleteUser("rajidm");
            //oimclient.createOrganization();
            oimclient.disableOrganization("DesignDepartment");
           
             
            }
    public void OIMConnection() {       //Function to connect to OIM
        
    Hashtable<Object, Object> env = new Hashtable<Object, Object>();
            env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL, "weblogic.jndi.WLInitialContextFactory");
            env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, "t3://idm.oraclefusion4all.com:14000");        //OIM Host Connection details
             
            System.setProperty("java.security.auth.login.config", "D:\\OIMDevelopmentBatch\\designconsole\\config\\authwl.conf");   //Update path of authwl.conf file according to your environment
            System.setProperty("OIM.AppServerType", "wls");  
            System.setProperty("APPSERVER_TYPE", "wls");
            oracle.iam.platform.OIMClient oimClient = new oracle.iam.platform.OIMClient(env);
     
             try {                        
                   oimClient.login("xelsysadm", "Abcd1234".toCharArray());         //Update password of Admin with your environment password
                   System.out.print("Successfully Connected with OIM ");
                 } catch (LoginException e) {
                   System.out.print("Login Exception"+ e);
                }            
              
        userManager = oimClient.getService(UserManager.class);
        organizationmanager=oimClient.getService(OrganizationManager.class);


        
    }
    
    public void createOrganization(){
         try {
      
             Organization org=new Organization("DesignDepartment");
             org.setAttribute("Organization Name", "DesignDepartment");                   //Name of Organization 
             org.setAttribute("Organization Customer Type", "Department");        //Type of Oragnization 
             organizationmanager.create(org);
             System.out.print("\nOrganization got Created... \n");
              
         } catch (OrganizationManagerException e) {
             e.printStackTrace();
         }
         catch (AccessDeniedException e) {
                     e.printStackTrace();
                 }
         
     }
    
    public void disableOrganization(String org){
         try {
      
            
             organizationmanager.disable(org,true);
             System.out.print("\nOrganization got Disabled... \n");
              
         } catch (OrganizationDisableException e) {
             e.printStackTrace();
         }
         catch (AccessDeniedException e) {
                     e.printStackTrace();
                 } catch (NoSuchOrganizationException e) {
        } catch (OrganizationAlreadyDeletedException e) {
        } catch (OrganizationDisableSubOrgsExistException e) {
        } catch (OrganizationDisableSubOrgsUsersExistException e) {
        } catch (OrganizationDisableUsersExistException e) {
        }

    }

    public void createuser(String userId) {
        HashMap<String, Object> userAttributeValueMap = new HashMap<String, Object>();
                            userAttributeValueMap.put("act_key", new Long(1));
                        userAttributeValueMap.put("User Login", userId);
                        userAttributeValueMap.put("First Name", "Rajesh");
                        userAttributeValueMap.put("Last Name", "IDM");
                        userAttributeValueMap.put("Email", "rajesh@tutionbooks.com");
                        userAttributeValueMap.put("usr_password", "Oracle123");
                        userAttributeValueMap.put("Role", "OTHER");
                        User user = new User("Rajesh", userAttributeValueMap);
                try {
                    userManager.create(user);
                    System.out.println("\nUser got created....");
                } catch (ValidationFailedException e) {
                    e.printStackTrace();
                } catch (UserAlreadyExistsException e) {
                    e.printStackTrace();
                } catch (UserCreateException e) {
                    e.printStackTrace();
                }
        
    }

    public void disableuser(String userId)
        {
                try {
                            userManager.disable(userId, true);
                            System.out.print("\n Disabled user Successfully");
                        } catch (ValidationFailedException e) {
                            e.printStackTrace();
                        } catch (UserDisableException e) {
                            e.printStackTrace();
                        } catch (NoSuchUserException e) {
                            e.printStackTrace();
                        }
            }
        public void enableuser(String userId)
        {
                try {
                            userManager.enable(userId, true);
                            System.out.print("\n Enabled user Successfully");
                        } catch (ValidationFailedException e) {
                            e.printStackTrace();
                        } catch (UserEnableException e) {
                            e.printStackTrace();
                        } catch (NoSuchUserException e) {
                            e.printStackTrace();
                        }
            }
        public void resetPassword(String userId) {
            try{
                userManager.resetPassword(userId, true, true);
                System.out.print("\n User Password reset was done Successfully");
                }
            catch (UserManagerException e){
                e.printStackTrace();     
            }
        }
        public void lockUser(String userId){
            try{
                userManager.lock(userId, true);
                System.out.print("\n User locked Successfully");
            
            }
            catch(NoSuchUserException e){
                e.printStackTrace();
            }
            catch(UserLockException e){
                e.printStackTrace();
            }
            catch(ValidationFailedException e){
                e.printStackTrace();
            }
        }
        public void unlockUser(String userId){
            try{
                userManager.unlock(userId, true);
                System.out.print("\n User unlocked Successfully");
            
            }
            catch(NoSuchUserException e){
                e.printStackTrace();
            }
            catch(UserUnlockException e){
                e.printStackTrace();
            }
            catch(ValidationFailedException e){
                e.printStackTrace();
            }
        }
        public void modifyUser(String userId){
             
            try {            
            
                  HashMap<String, Object> userAttributeValueMap = new HashMap<String, Object>();
                   userAttributeValueMap.put("act_key", new Long(2));
                   userAttributeValueMap.put("User Login", userId);
                   userAttributeValueMap.put("First Name", "Rajesh");
                   userAttributeValueMap.put("Last Name", "OIM");
                   userAttributeValueMap.put("Email", "manager@tutionbooks.com");               
                   User retrievedUser = searchUser(userId);
                   User user = new User(retrievedUser.getEntityId(),userAttributeValueMap);                   
                   
                   userManager.modify(user);
                   System.out.println("\nUpdated user datails.. \n");
                 
              } catch (ValidationFailedException e) {
                e.printStackTrace();
              } catch (UserModifyException e) {
                e.printStackTrace();
              } catch (NoSuchUserException e) {
                e.printStackTrace();
              } 
        }
        public User searchUser(String userId) {
         
                Set<String> resAttrs = new HashSet<String>();
                User user = null;
                try {
                      user = userManager.getDetails(userId, resAttrs, true);
                }      
                catch(UserLookupException e){
                    e.printStackTrace();
                }
                catch(NoSuchUserException e1){
                    e1.printStackTrace();
                }
                return user;            
                }
        public void deleteUser(String userId)  {
            try {
                userManager.delete(userId, true);
                System.out.println("\nDeleted User... \n");
            } catch (ValidationFailedException e) {
                e.printStackTrace();
            } catch (UserDeleteException e) {
                e.printStackTrace();
            } catch (NoSuchUserException e) {
                e.printStackTrace();
            }
        }
        
}
