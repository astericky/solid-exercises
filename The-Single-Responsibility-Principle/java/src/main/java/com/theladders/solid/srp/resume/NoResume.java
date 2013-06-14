package com.theladders.solid.srp.resume;

public class NoResume implements Resume
{
  private final String resumeName;
  
  public NoResume() 
  {
    this.resumeName = "";
  }
  
  public String getResumeName()
  {
    return resumeName;
  }
  
  public boolean doesntExist()
  {
    return true;
  }
  
}
