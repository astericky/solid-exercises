package com.theladders.solid.srp;

import com.theladders.solid.srp.job.Job;
import com.theladders.solid.srp.job.application.ApplicationFailureException;
import com.theladders.solid.srp.job.application.JobApplicationResult;
import com.theladders.solid.srp.job.application.JobApplicationSystem;
import com.theladders.solid.srp.job.application.UnprocessedApplication;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.jobseeker.JobseekerProfile;
import com.theladders.solid.srp.jobseeker.JobseekerProfileManager;
import com.theladders.solid.srp.jobseeker.ProfileStatus;
import com.theladders.solid.srp.resume.Resume;
import com.theladders.solid.srp.resume.ResumeManager;

public class ApplyToJobWorkflow
{
  private final JobApplicationSystem    jobApplicationSystem;
  private final ResumeManager           resumeManager;
  private final JobseekerProfileManager jobseekerProfileManager;

  
  public ApplyToJobWorkflow(JobApplicationSystem jobApplicationSystem,
                            ResumeManager resumeManager,
                            JobseekerProfileManager jobseekerProfileManager)
  {
    this.jobApplicationSystem = jobApplicationSystem;
    this.resumeManager = resumeManager;
    this.jobseekerProfileManager = jobseekerProfileManager;
  }

  public void apply(Jobseeker jobseeker,
                    Job job,
                    Resume resume) throws ProfileCompletionRequiredException, JobDoesNotExistException
  {
    if (job.doesntExist())
    {
      throw new JobDoesNotExistException();
    }

    JobseekerProfile profile = jobseekerProfileManager.getJobSeekerProfile(jobseeker);

    UnprocessedApplication application = new UnprocessedApplication(jobseeker, job, resume);
    JobApplicationResult applicationResult = jobApplicationSystem.apply(application);

    if (applicationResult.failure())
    {
      throw new ApplicationFailureException(applicationResult.toString());
    }

    if (profileCompletionRequired(jobseeker, profile))
    {
      throw new ProfileCompletionRequiredException();
    }
  }

  public Resume getJobseekerResume(Jobseeker jobseeker,
                                    String fileName,
                                    boolean isNewResume,
                                    boolean makeResumeActive)
  {
    Resume resume;
    
    if (isNewResume)
    {
      resume = resumeManager.saveResume(jobseeker, fileName);

      if (resume != null && makeResumeActive)
      {
        resumeManager.saveAsActive(jobseeker, resume);
      }
    }
    else
    {
      resume = resumeManager.getActiveResume(jobseeker.getId());
    }
    
    return resume;
  }

 private boolean profileCompletionRequired(Jobseeker jobseeker,
                                            JobseekerProfile profile)
  {
    return !jobseeker.isPremium() && (profile.getStatus().equals(ProfileStatus.INCOMPLETE) ||
                                   profile.getStatus().equals(ProfileStatus.NO_PROFILE) ||
                                   profile.getStatus().equals(ProfileStatus.REMOVED));
  }  
}
