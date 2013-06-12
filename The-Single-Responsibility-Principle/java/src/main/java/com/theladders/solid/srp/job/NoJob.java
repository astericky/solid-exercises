package com.theladders.solid.srp.job;

public class NoJob implements Job
{
  public int getJobId()
  {
    return 0;
  }
  
  public String getTitle()
  {
    return "";
  }
  
  public boolean doesntExist()
  {
    return true;
  }
}
