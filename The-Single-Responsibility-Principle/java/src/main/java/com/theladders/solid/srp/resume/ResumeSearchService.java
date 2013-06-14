package com.theladders.solid.srp.resume;

import com.theladders.solid.srp.jobseeker.Jobseeker;

public class ResumeSearchService
{
  private final ResumeManager resumeManager;
  
  public ResumeSearchService(ResumeManager resumeManager) 
  {
    this.resumeManager = resumeManager;
  }
  
  public Resume getResume(Jobseeker jobseeker,
                          String fileName, 
                          boolean isNewResume, 
                          boolean makeResumeActive) 
  {
    Resume resume;
    
    if (fileName == null) {
       resume = new NoResume();
    } else if (isNewResume)
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
}
