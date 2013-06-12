package com.theladders.solid.srp.job;


public class JobSearchService
{
  private final JobRepository repository;

  public JobSearchService(JobRepository repository)
  {
    this.repository = repository;
  }

  public Job getJob(int jobId)
  {
    SomeJob job = repository.getJob(jobId);
    
    if (job == null) return new NoJob();
    return job;
  }
}
