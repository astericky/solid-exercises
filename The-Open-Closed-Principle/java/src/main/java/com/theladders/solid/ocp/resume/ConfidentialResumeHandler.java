package com.theladders.solid.ocp.resume;

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
    JobseekerProfile jsp = jobSeekerProfileManager.getJobSeekerProfile(user);
    JobseekerConfidentialityProfile profile = jobseekerConfidentialityProfileDao.fetchJobSeekerConfidentialityProfile(jsp.getId());
    
    for(ResumeField resumeField : resumeFields) {
      profile.removeResumeField(resumeField);
    }
  }
  
  public void makeAllContactNonConfidential(User user, String category)
  {
    JobseekerProfile jsp = jobSeekerProfileManager.getJobSeekerProfile(user);
    JobseekerConfidentialityProfile profile = jobseekerConfidentialityProfileDao.fetchJobSeekerConfidentialityProfile(jsp.getId());
    
    for(ResumeField resumeField : resumeFields) {
      if (category == resumeField.getCategory()) {
        profile.removeResumeField(resumeField);
      }
    }
  }

  @SuppressWarnings("unused")
  private void generatePermanentConfidentialFiles(User user,
                                                  JobseekerConfidentialityProfile profile)
  {
    // stub
  }

}
