package com.theladders.solid.srp.resume;

public class SomeResume implements Resume
{
  private final String resumeName;

  public SomeResume(String resumeName)
  {
    this.resumeName = resumeName;
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
    result = prime * result + ((resumeName == null) ? 0 : resumeName.hashCode());
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
    SomeResume other = (SomeResume) obj;
    if (resumeName == null)
    {
      if (other.resumeName != null)
        return false;
    }
    else if (!resumeName.equals(other.resumeName))
      return false;
    return true;
  }
}
