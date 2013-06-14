package com.theladders.solid.srp.job.application;


public class FailedApplication implements JobApplicationResult
{
  private final String reason;
  
  public FailedApplication(String reason)
  {
    this.reason = reason;
  }
  
  @Override
  public boolean failure()
  {
    return true;
  }
  
  public String reason() 
  {
    return reason;
  }
}
