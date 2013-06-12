package com.theladders.solid.srp.job;

public class SomeJob implements Job
{
  private final int id;
  private final String title;

  public SomeJob(int id)
  {
    this.id = id;
    this.title = "This is a job with id:" + Integer.toString(id);
  }

  public int getJobId()
  {
    return id;
  }

  public String getTitle()
  {
    return title;
  }

  public boolean doesntExist()
  {
    return false;
  }
  
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SomeJob other = (SomeJob) obj;
    if (id != other.id)
      return false;
    return true;
  }
}
