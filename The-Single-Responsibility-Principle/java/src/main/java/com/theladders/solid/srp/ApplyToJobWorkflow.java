package com.theladders.solid.srp;

import com.theladders.solid.srp.job.Job;
import com.theladders.solid.srp.job.application.ApplicationFailureException;
import com.theladders.solid.srp.job.application.FailedApplication;
import com.theladders.solid.srp.job.application.JobApplicationResult;
import com.theladders.solid.srp.job.application.JobApplicationSystem;
import com.theladders.solid.srp.job.application.UnprocessedApplication;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.jobseeker.JobseekerProfile;
import com.theladders.solid.srp.jobseeker.JobseekerProfileManager;
import com.theladders.solid.srp.jobseeker.ProfileStatus;
import com.theladders.solid.srp.resume.Resume;

public class ApplyToJobWorkflow
{
  private final JobApplicationSystem    jobApplicationSystem;
  
  public ApplyToJobWorkflow(JobApplicationSystem jobApplicationSystem)
  {
    this.jobApplicationSystem = jobApplicationSystem;
  }

  public JobApplicationResult apply(Jobseeker jobseeker,
                    Job job,
                    Resume resume)
  {    
    if (job.doesntExist())
    {
      return new FailedApplication("Job does not exist.");
    }
    
    if (resume.doesntExist()) {
      return new FailedApplication("Resume does not exist.");
    }

    UnprocessedApplication application = new UnprocessedApplication(jobseeker, job, resume);
    JobApplicationResult applicationResult = jobApplicationSystem.apply(application);
    
    return applicationResult;
  }
}
