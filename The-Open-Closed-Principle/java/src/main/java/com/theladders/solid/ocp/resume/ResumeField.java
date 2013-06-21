package com.theladders.solid.ocp.resume;

public class ResumeField
{
  private String name;
  private String category;
  
  public ResumeField(String name, String category)
  {
    this.name = name;
    this.category = category;
  }
  
  public String getName()
  {
    return name;
  }
  
  public String getCategory()
  {
    return category;
  }
}
