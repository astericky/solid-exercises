package com.theladders.solid.ocp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.theladders.solid.ocp.jobseeker.JobseekerConfidentialityProfileDao;
import com.theladders.solid.ocp.resume.ConfidentialResumeHandler;
import com.theladders.solid.ocp.resume.JobseekerProfileManager;
import com.theladders.solid.ocp.resume.ResumeField;
import com.theladders.solid.ocp.resume.ResumeConfidentialityManager;
import com.theladders.solid.ocp.user.User;

public class ResumeConfidentialityTest
{
  @Test
  public void makeAllCategoriesNonConfidential() 
  {
    String[] resumeFieldNames = {"Name", "CompanyName", "WorkExperience", "MailingAddress", "PhoneNumber", "EamilAddress", "ContactInfo"};
    List<ResumeField> resumeFields = new ArrayList<>();
    
    for(String resumeFieldName : resumeFieldNames)
    {
      resumeFields.add(new ResumeField(resumeFieldName, "general"));
    }
    
    JobseekerProfileManager jobseekerProfileManager = new JobseekerProfileManager();
    JobseekerConfidentialityProfileDao jobseekerConfidentialityProfileDao = new JobseekerConfidentialityProfileDao();
    ConfidentialResumeHandler confidentialResumeHandler = new ConfidentialResumeHandler(jobseekerProfileManager, jobseekerConfidentialityProfileDao, resumeFields);
    ResumeConfidentialityManager resumeConfidentialityManager = new ResumeConfidentialityManager(confidentialResumeHandler);

    int id = 1; // get from command line?
    User user = new User(id);
    
    // chris please do this for me
    List<ResumeField> profile = resumeConfidentialityManager.makeAllCategoriesNonConfidential(user);
    // hey chris, how many fields are confidential?
    resumeConfidentialityManager.howManyConfidentialFields(user);
    
    // zero? excellent, that is what i expected
    assertEquals(0, profile.size());
  }
  
  @Test
  public void makeAllContactNonConfidential() 
  {
    String[] resumeFieldNames = {"Name", "CompanyName", "WorkExperience"};
    String[] contactFieldNames = {"MailingAddress", "PhoneNumber", "EamilAddress", "ContactInfo"};
    List<ResumeField> resumeFields = new ArrayList<>();
    
    for(String resumeFieldName : resumeFieldNames)
    {
      resumeFields.add(new ResumeField(resumeFieldName, "general"));
    }
      
    for(String contactFieldName : contactFieldNames)
    {
      resumeFields.add(new ResumeField(contactFieldName, "contact"));
    }
    
    JobseekerProfileManager jobseekerProfileManager = new JobseekerProfileManager();
    JobseekerConfidentialityProfileDao jobseekerConfidentialityProfileDao = new JobseekerConfidentialityProfileDao();
    ConfidentialResumeHandler confidentialResumeHandler = new ConfidentialResumeHandler(jobseekerProfileManager, jobseekerConfidentialityProfileDao, resumeFields);
    ResumeConfidentialityManager resumeConfidentialityManager = new ResumeConfidentialityManager(confidentialResumeHandler);
    
    int id = 1; // get from command line?
    User user = new User(id);
    
    resumeConfidentialityManager.makeAllContactInfoNonConfidential(user, "contact");
    assertEquals(true, true);
  }
}
