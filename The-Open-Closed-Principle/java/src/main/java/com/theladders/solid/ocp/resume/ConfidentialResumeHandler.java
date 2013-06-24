package com.theladders.solid.ocp.resume;

import java.util.ArrayList;
import java.util.List;

import com.theladders.solid.ocp.jobseeker.JobseekerConfidentialityProfile;
import com.theladders.solid.ocp.jobseeker.JobseekerConfidentialityProfileDao;
import com.theladders.solid.ocp.user.User;

public class ConfidentialResumeHandler
{
  private final JobseekerProfileManager            jobSeekerProfileManager;
  private final JobseekerConfidentialityProfileDao jobseekerConfidentialityProfileDao;
  private final List<ResumeField> resumeFields;

  public ConfidentialResumeHandler(JobseekerProfileManager jobseekerProfileManager,
                                   JobseekerConfidentialityProfileDao jobseekerConfidentialityProfileDao,
                                   List<ResumeField> resumeFields)
  {
    this.jobSeekerProfileManager = jobseekerProfileManager;
    this.jobseekerConfidentialityProfileDao = jobseekerConfidentialityProfileDao;
    this.resumeFields = resumeFields;
  }
  
  /**
   * 
   * Both violate the open closed principle because you have to modify these methods
   * every time there is a new confidentialPhraseCategory that needs to be added.
   */
  
  public void makeAllCategoriesNonConfidential(User user)
  {
    JobseekerConfidentialityProfile profile = confidentialProfileFor(user);
    
    for(ResumeField resumeField : resumeFields) 
    {
      profile.removeResumeField(resumeField);
    }
  }

  public void makeAllContactNonConfidential(User user)
  {
    JobseekerConfidentialityProfile profile = confidentialProfileFor(user);
    
    for(ResumeField resumeField : resumeFields) 
    {
      if (resumeField.getCategory() == "contact") 
      {
        profile.removeResumeField(resumeField);
      }
    }
  }
  
  public int numberOfConfidentialFields(User user)
  {
    JobseekerConfidentialityProfile profile = confidentialProfileFor(user);
    return profile.getConfidentialityProfile().size();
  }
  
  public int numberOfContactConfidentialFields(User user)
  {
    JobseekerConfidentialityProfile profile = confidentialProfileFor(user);
    List<ResumeField> contactResumeFields = profile.getConfidentialityProfile();
    List<ResumeField> confidentialContactResumeFields = new ArrayList<>();
    
    for(ResumeField resumeField : contactResumeFields) 
    {
      if (resumeField.getCategory() == "contact") 
      {
        confidentialContactResumeFields.add(resumeField);
      }
    }
    
    return confidentialContactResumeFields.size();
  }

  private JobseekerConfidentialityProfile confidentialProfileFor(User user)
  {
    JobseekerProfile jsp = jobSeekerProfileManager.getJobSeekerProfile(user);
    JobseekerConfidentialityProfile profile = jobseekerConfidentialityProfileDao.fetchJobSeekerConfidentialityProfile(jsp.getId());
    return profile;
  }
  
  @SuppressWarnings("unused")
  private void generatePermanentConfidentialFiles(User user,
                                                  JobseekerConfidentialityProfile profile)
  {
    // stub
  }

}
