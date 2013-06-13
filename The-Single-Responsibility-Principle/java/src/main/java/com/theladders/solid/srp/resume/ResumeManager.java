package com.theladders.solid.srp.resume;

import com.theladders.solid.srp.jobseeker.Jobseeker;

public class ResumeManager
{
  private final ResumeRepository resumeRepository;
  private final ActiveResumeRepository activeResumeRepository;

  public ResumeManager(ResumeRepository resumeRepository,
                       ActiveResumeRepository repository)
  {
    this.resumeRepository = resumeRepository;
    this.activeResumeRepository = repository;
  }

  public Resume saveResume(Jobseeker jobseeker,
                           String fileName)
  {
    Resume resume = new Resume(fileName);
    resumeRepository.saveResume(jobseeker.getId(), resume);

    return resume;
  }
  
  public void saveAsActive(Jobseeker jobseeker,
                           Resume resume)
  {
    activeResumeRepository.makeActive(jobseeker.getId(), resume);
  }

  public Resume getActiveResume(int jobseekerId)
  {
    return activeResumeRepository.activeResumeFor(jobseekerId);
  }
}
