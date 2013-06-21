package com.theladders.solid.ocp.resume;

import java.util.List;
import com.theladders.solid.ocp.user.User;

public class ResumeConfidentialityManager
{
  private final ConfidentialResumeHandler confidentialResumeHandler;

  public ResumeConfidentialityManager(ConfidentialResumeHandler confidentialResumeHandler)
  {
    this.confidentialResumeHandler = confidentialResumeHandler;
  }

  public void makeAllContactInfoNonConfidential(User user)
  {
    confidentialResumeHandler.makeAllContactNonConfidential(user);
  }

  public void makeAllCategoriesNonConfidential(User user)
  {
    confidentialResumeHandler.makeAllCategoriesNonConfidential(user);
  }
  
  public Integer numberOfConfidentialFields(User user)
  {
    return confidentialResumeHandler.numberOfConfidentialFields(user);
  }
  
  public Integer numberOfConfidentialContactFields(User user)
  {
    return confidentialResumeHandler.numberOfContactConfidentialFields(user);
  }
}
