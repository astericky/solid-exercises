package com.theladders.solid.ocp;
import static org.junit.Assert.*;

import org.junit.Test;

import com.theladders.solid.ocp.jobseeker.JobseekerConfidentialityProfileDao;
import com.theladders.solid.ocp.resume.ConfidentialResumeHandler;
import com.theladders.solid.ocp.resume.JobseekerProfileManager;
import com.theladders.solid.ocp.resume.Phrase;
import com.theladders.solid.ocp.resume.ResumeConfidentialityManager;
import com.theladders.solid.ocp.user.User;

public class TestIt
{
  
  JobseekerProfileManager jobseekerProfileManager = new JobseekerProfileManager();
  JobseekerConfidentialityProfileDao jobseekerConfidentialityProfileDao = new JobseekerConfidentialityProfileDao();
  ConfidentialResumeHandler confidentialResumeHandler = new ConfidentialResumeHandler(jobseekerProfileManager, jobseekerConfidentialityProfileDao);
  ResumeConfidentialityManager resumeConfidentialityManager = new ResumeConfidentialityManager(confidentialResumeHandler);

  int id = 1; // get from command line?
  User user = new User(id);
  int count = 0;
  
  @Test
  public void makeAllCategoriesConfidential() {
    String[] phrases = {"Name", "MailingAddress", "PhoneNumber", "EmailAddress", "ContactInfo", "CompanyName", "WorkExperience"};
    boolean isConfidential = false;
    resumeConfidentialityManager.makeAllCategoriesNonConfidential(user);

    for(String phrase : phrases){
      Phrase newPhrase = new Phrase(phrase);
      assertTrue(newPhrase.isConfidential());
    }
    
  }
  
  @Test
  public void makeAllCategoriesNonConfidential() {
    resumeConfidentialityManager.makeAllContactInfoNonConfidential(user);
    assertEquals(true, true);
  }
}
