package com.theladders.solid.ocp;

import java.util.ArrayList;
import java.util.List;

import com.theladders.solid.ocp.jobseeker.JobseekerConfidentialityProfileDao;
import com.theladders.solid.ocp.resume.ConfidentialResumeHandler;
import com.theladders.solid.ocp.resume.JobseekerProfileManager;
import com.theladders.solid.ocp.resume.ResumeConfidentialityManager;
import com.theladders.solid.ocp.resume.ResumeField;
import com.theladders.solid.ocp.user.User;

public class App
{
  public static void main(String[] args)
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
    
    resumeConfidentialityManager.makeAllCategoriesNonConfidential(user);
  }
}
