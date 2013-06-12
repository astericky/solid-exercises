package com.theladders.solid.srp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.theladders.solid.srp.http.HttpRequest;
import com.theladders.solid.srp.http.HttpResponse;
import com.theladders.solid.srp.job.Job;
import com.theladders.solid.srp.job.JobSearchService;
import com.theladders.solid.srp.job.application.JobApplicationSystem;
import com.theladders.solid.srp.jobseeker.JobseekerProfileManager;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.resume.ResumeManager;

public class ApplyController
{
  private final JobSearchService        jobSearchService;
  private final JobApplicationSystem    jobApplicationSystem;
  private final ResumeManager           resumeManager;
  private final JobseekerProfileManager jobseekerProfileManager;


  public ApplyController(JobSearchService jobSearchService,
                         JobApplicationSystem jobApplicationSystem,
                         ResumeManager resumeManager,
                         JobseekerProfileManager jobseekerProfileManager)
  {
    this.jobSearchService = jobSearchService;
    this.jobApplicationSystem = jobApplicationSystem;
    this.resumeManager = resumeManager;
    this.jobseekerProfileManager = jobseekerProfileManager;
  }


  public HttpResponse handle(HttpRequest request,
                             HttpResponse response,
                             String origFileName)
  {
    Jobseeker jobseeker = request.getSession().getJobseeker();

    int jobId = getJobIdFromRequest(request);

    Job job = jobSearchService.getJob(jobId);

    Map<String, Object> model = new HashMap<>();
    model.put("jobId", job.getJobId());
    model.put("jobTitle", job.getTitle());
    
    List<String> errList = new ArrayList<>();
    
    try
    {
      boolean isNewResume = !"existing".equals(request.getParameter("whichResume"));
      boolean makeResumeActive = "yes".equals(request.getParameter("makeResumeActive"));
      
      ApplyToJobWorkflow workflow = new ApplyToJobWorkflow(jobApplicationSystem, 
                                                           resumeManager, 
                                                           jobseekerProfileManager);
      workflow.apply(jobseeker, job, origFileName, isNewResume, makeResumeActive);
    }
    catch (ProfileCompletionRequiredException e)
    {
      provideResumeCompletionView(response, model);
      return response;      
    }
    catch (JobDoesNotExistException e)
    {
      provideInvalidJobView(response, jobId);
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

  private static void provideInvalidJobView(HttpResponse response, int jobId)
  {
    Map<String, Object> model = new HashMap<>();
    model.put("jobId", jobId);

    Result result = new Result("invalidJob", model);
    response.setResult(result);
  }
}
