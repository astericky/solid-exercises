package com.theladders.solid.ocp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.theladders.solid.ocp.jobseeker.JobseekerConfidentialityProfile;
import com.theladders.solid.ocp.jobseeker.JobseekerConfidentialityProfileDao;
import com.theladders.solid.ocp.resume.ConfidentialResumeHandler;
import com.theladders.solid.ocp.resume.JobseekerProfile;
import com.theladders.solid.ocp.resume.JobseekerProfileManager;
import com.theladders.solid.ocp.resume.ResumeField;
import com.theladders.solid.ocp.resume.ResumeConfidentialityManager;
import com.theladders.solid.ocp.user.User;

public class ResumeConfidentialityTest
{
  String[] resumeFieldNames = {"Name", "CompanyName", "WorkExperience"};
  String[] contactFieldNames = {"MailingAddress", "PhoneNumber", "EamilAddress", "ContactInfo"};
  List<ResumeField> resumeFields;

  
  User userUnderTest;
  private ResumeConfidentialityManager resumeConfidentialityManager;
  
  @Test
  public void makeAllCategoriesNonConfidential() 
  {
    assertFalse(0 == (int)resumeConfidentialityManager.numberOfConfidentialFields(userUnderTest));

    resumeConfidentialityManager.makeAllCategoriesNonConfidential(userUnderTest);
    
    assertEquals(0, (int)resumeConfidentialityManager.numberOfConfidentialFields(userUnderTest));
    assertEquals(0, (int)resumeConfidentialityManager.numberOfConfidentialContactFields(userUnderTest));
  }
  
  @Test
  public void makeAllContactNonConfidential() 
  {
    assertFalse(0 == (int)resumeConfidentialityManager.numberOfConfidentialContactFields(userUnderTest));
    
    resumeConfidentialityManager.makeAllContactInfoNonConfidential(userUnderTest);
 
    assertEquals(0, resumeConfidentialityManager.numberOfConfidentialContactFields(userUnderTest));
    assertEquals(3, resumeConfidentialityManager.numberOfConfidentialFields(userUnderTest));
  }
  
  @Before
  public void setup()
  {
    setupResumeFields();
    userUnderTest = new User(1);
    JobseekerProfileManager jobseekerProfileManager = new JobseekerProfileManager();
    JobseekerConfidentialityProfileDao jobseekerConfidentialityProfileDao = new TestJobseekerConfidentialityProfileDao(resumeFields);
        
    ConfidentialResumeHandler confidentialResumeHandler = new ConfidentialResumeHandler(jobseekerProfileManager, jobseekerConfidentialityProfileDao, resumeFields);
    resumeConfidentialityManager = new ResumeConfidentialityManager(confidentialResumeHandler);
  }
  
  private void setupResumeFields()
  {
    resumeFields = new ArrayList<>();
    
    for(String resumeFieldName : resumeFieldNames)
    {
      resumeFields.add(new ResumeField(resumeFieldName, "general"));
    }
      
    for(String contactFieldName : contactFieldNames)
    {
      resumeFields.add(new ResumeField(contactFieldName, "contact"));
    }
  }
  
  private static class TestJobseekerConfidentialityProfileDao extends JobseekerConfidentialityProfileDao
  {
    private List<ResumeField> fields;
    private Map<Integer, JobseekerConfidentialityProfile> profiles;
    
    public TestJobseekerConfidentialityProfileDao(List<ResumeField> fields)
    {
      this.fields = fields;
      profiles = new HashMap<>();
    }
    
    @Override
    public JobseekerConfidentialityProfile fetchJobSeekerConfidentialityProfile(int id)
    { 
      if (profiles.containsKey(id))
        return profiles.get(id);
      
      JobseekerConfidentialityProfile profile = createProfile();
      
      profiles.put(id, profile);
      
      return profile;
    }

    
    private JobseekerConfidentialityProfile createProfile()
    {
      JobseekerConfidentialityProfile profile = new JobseekerConfidentialityProfile();
      
      for(ResumeField resumeField : fields)
      {
        profile.addResumeField(resumeField);
      }
      
      return profile;
    }
  }
  
}
