package com.theladders.solid.srp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.theladders.solid.srp.http.HttpRequest;
import com.theladders.solid.srp.http.HttpResponse;
import com.theladders.solid.srp.job.Job;
import com.theladders.solid.srp.job.JobSearchService;
import com.theladders.solid.srp.job.application.ApplicationFailureException;
import com.theladders.solid.srp.job.application.JobApplicationResult;
import com.theladders.solid.srp.job.application.JobApplicationSystem;
import com.theladders.solid.srp.job.application.UnprocessedApplication;
import com.theladders.solid.srp.jobseeker.JobseekerProfile;
import com.theladders.solid.srp.jobseeker.JobseekerProfileManager;
import com.theladders.solid.srp.jobseeker.ProfileStatus;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.resume.MyResumeManager;
import com.theladders.solid.srp.resume.Resume;
import com.theladders.solid.srp.resume.ResumeManager;

public class ApplyController
{
  private final JobseekerProfileManager jobseekerProfileManager;
  private final JobSearchService        jobSearchService;
  private final JobApplicationSystem    jobApplicationSystem;
  private final ResumeManager           resumeManager;
  private final MyResumeManager         myResumeManager;

  public ApplyController(JobseekerProfileManager jobseekerProfileManager,
                         JobSearchService jobSearchService,
                         JobApplicationSystem jobApplicationSystem,
                         ResumeManager resumeManager,
                         MyResumeManager myResumeManager)
  {
    this.jobseekerProfileManager = jobseekerProfileManager;
    this.jobSearchService = jobSearchService;
    this.jobApplicationSystem = jobApplicationSystem;
    this.resumeManager = resumeManager;
    this.myResumeManager = myResumeManager;
  }

  public HttpResponse handle(HttpRequest request,
                             HttpResponse response,
                             String origFileName)
  {
    Jobseeker jobseeker = request.getSession().getJobseeker();

    int jobId = getJobIdFromRequest(request);

    Job job = jobSearchService.getJob(jobId);

    if (job == null)
    {
      provideInvalidJobView(response, jobId);
      return response;
    }

    Map<String, Object> model = new HashMap<>();

    List<String> errList = new ArrayList<>();

    model.put("jobId", job.getJobId());
    model.put("jobTitle", job.getTitle());
    
    try
    {
      boolean isNewResume = !"existing".equals(request.getParameter("whichResume"));
      boolean makeResumeActive = "yes".equals(request.getParameter("makeResumeActive"));
      
      apply(jobseeker, job, origFileName, isNewResume, makeResumeActive);
    }
    catch (ProfileCompletionRequiredException e)
    {
      
      provideResumeCompletionView(response, model);
      return response;      
    }
    catch (Exception e)
    {
      errList.add("We could not process your application.");
      provideErrorView(response, errList, model);
      return response;
    }

    provideApplySuccessView(response, model);

    return response;
  }

  private boolean profileCompletionRequired(Jobseeker jobseeker,
                                            JobseekerProfile profile)
  {
    return !jobseeker.isPremium() && (profile.getStatus().equals(ProfileStatus.INCOMPLETE) ||
                                   profile.getStatus().equals(ProfileStatus.NO_PROFILE) ||
                                   profile.getStatus().equals(ProfileStatus.REMOVED));
  }

  private int getJobIdFromRequest(HttpRequest request)
  {
    String jobIdString = request.getParameter("jobId");
    int jobId = Integer.parseInt(jobIdString);
    return jobId;
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

  private void apply(Jobseeker jobseeker,
                     Job job,
                     String fileName,
                     boolean isNewResume,
                     boolean makeResumeActive) throws ProfileCompletionRequiredException
  {
    JobseekerProfile profile = jobseekerProfileManager.getJobSeekerProfile(jobseeker);
    Resume resume;

    if (isNewResume)
    {
      resume = resumeManager.saveResume(jobseeker, fileName);

      if (resume != null && makeResumeActive)
      {
        myResumeManager.saveAsActive(jobseeker, resume);
      }
    }
    else
    {
      resume = myResumeManager.getActiveResume(jobseeker.getId());
    }

    
    UnprocessedApplication application = new UnprocessedApplication(jobseeker, job, resume);
    JobApplicationResult applicationResult = jobApplicationSystem.apply(application);

    if (applicationResult.failure())
    {
      throw new ApplicationFailureException(applicationResult.toString());
    }
    
    if(profileCompletionRequired(jobseeker, profile))
    {
      throw new ProfileCompletionRequiredException();
    }
  }

  private static void provideInvalidJobView(HttpResponse response, int jobId)
  {
    Map<String, Object> model = new HashMap<>();
    model.put("jobId", jobId);

    Result result = new Result("invalidJob", model);
    response.setResult(result);
  }
}
