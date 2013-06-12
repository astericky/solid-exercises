package com.theladders.solid.srp.job;

import java.util.HashMap;
import java.util.Map;


public class JobRepository
{
  private final Map<Integer, SomeJob> jobs;

  public JobRepository()
  {
    this.jobs = new HashMap<>();
  }

  public void addJob(SomeJob aJob)
  {
    jobs.put(aJob.getJobId(), aJob);
  }

  public SomeJob getJob(int jobId)
  {
    return jobs.get(jobId);
  }
}
