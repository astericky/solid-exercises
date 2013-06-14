package com.theladders.solid.srp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.theladders.solid.srp.http.HttpRequest;
import com.theladders.solid.srp.http.HttpResponse;
import com.theladders.solid.srp.job.Job;
import com.theladders.solid.srp.job.JobSearchService;
import com.theladders.solid.srp.job.application.FailedApplication;
import com.theladders.solid.srp.job.application.JobApplicationResult;
import com.theladders.solid.srp.job.application.JobApplicationSystem;
import com.theladders.solid.srp.jobseeker.JobseekerProfile;
import com.theladders.solid.srp.jobseeker.JobseekerProfileManager;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.jobseeker.ProfileStatus;
import com.theladders.solid.srp.resume.Resume;
import com.theladders.solid.srp.resume.ResumeSearchService;

public class ApplyController
{
  private final JobSearchService        jobSearchService;
  private final JobApplicationSystem    jobApplicationSystem;
  private final ResumeSearchService     resumeSearchService;
  private final JobseekerProfileManager jobseekerProfileManager;


  public ApplyController(JobSearchService jobSearchService,
                         JobApplicationSystem jobApplicationSystem,
                         ResumeSearchService resumeSearchService,
                         JobseekerProfileManager jobseekerProfileManager)
  {
    this.jobSearchService = jobSearchService;
    this.jobApplicationSystem = jobApplicationSystem;
    this.resumeSearchService = resumeSearchService;
    this.jobseekerProfileManager = jobseekerProfileManager;
  }


  public HttpResponse handle(HttpRequest request,
                             HttpResponse response,
                             String origFileName)
  {
    Jobseeker jobseeker = request.getSession().getJobseeker();
    JobseekerProfile profile = jobseekerProfileManager.getJobSeekerProfile(jobseeker);
    
    int jobId = getJobIdFromRequest(request);

    Job job = jobSearchService.getJob(jobId);

    Map<String, Object> model = new HashMap<>();
    model.put("jobId", job.getJobId());
    model.put("jobTitle", job.getTitle());
    
    List<String> errList = new ArrayList<>();
    
    boolean isNewResume = !"existing".equals(request.getParameter("whichResume"));
    boolean makeResumeActive = "yes".equals(request.getParameter("makeResumeActive"));
      
    ApplyToJobWorkflow workflow = new ApplyToJobWorkflow(jobApplicationSystem);
      
    Resume resume = resumeSearchService.getResume(jobseeker, origFileName, isNewResume, makeResumeActive);
    JobApplicationResult result = workflow.apply(jobseeker, job, resume);
      
    if (result.failure()) 
    {
      FailedApplication failed = (FailedApplication) result;
        
      if (failed.reason().equals("Job does not exist."))
      {
        provideInvalidJobView(response, jobId);
        return response;
      }
        
      if (failed.reason().equals("We're busy.")) 
      {
        errList.add("We could not process your application.");
        provideErrorView(response, errList, model);
        return response;
      }
      
      if (failed.reason().equals("Resume does not exist."))
      {
        provideResumeErrorView(response, model);
        return response;
      }
    }
      
    if (profileCompletionRequired(jobseeker, profile))
    {
      provideResumeCompletionView(response, model);
      return response;
    }
    
    provideApplySuccessView(response, model);
    
    return response;
  }

  private int getJobIdFromRequest(HttpRequest request)
  {
    String jobIdString = request.getParameter("jobId");
    int jobId = Integer.parseInt(jobIdString);
    return jobId;
  }
  
  private boolean profileCompletionRequired(Jobseeker jobseeker,
                                            JobseekerProfile profile)
  {
    return !jobseeker.isPremium() && (profile.getStatus().equals(ProfileStatus.INCOMPLETE) ||
                                   profile.getStatus().equals(ProfileStatus.NO_PROFILE) ||
                                   profile.getStatus().equals(ProfileStatus.REMOVED));
  } 

  private static void provideApplySuccessView(HttpResponse response, Map<String, Object> model)
  {
    Result result = new Result("success", model);
    response.setResult(result);
  }

  private static void provideResumeCompletionView(HttpResponse response, Map<String, Object> model)
  {
    Result result = new Result("completeResumePlease", model);
    response.setResult(result);
  }

  private static void provideErrorView(HttpResponse response, List<String> errList, Map<String, Object> model)
  {
   Result result = new Result("error", model, errList);
   response.setResult(result);
  }
  
  private static void provideResumeErrorView(HttpResponse response, Map<String, Object> model)
  {
    Result result = new Result("error", model);
    response.setResult(result);
  }

  private static void provideInvalidJobView(HttpResponse response, int jobId)
  {
    Map<String, Object> model = new HashMap<>();
    model.put("jobId", jobId);

    Result result = new Result("invalidJob", model);
    response.setResult(result);
  }
}
