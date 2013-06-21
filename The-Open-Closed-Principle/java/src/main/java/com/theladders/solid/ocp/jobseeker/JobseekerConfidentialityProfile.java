package com.theladders.solid.ocp.jobseeker;

import java.util.ArrayList;
import java.util.List;
import com.theladders.solid.ocp.resume.ResumeField;


public class JobseekerConfidentialityProfile
{
  private List<ResumeField> confidentialityProfile = new ArrayList<>();
  
  public void addResumeField(ResumeField resumeField)
  {
    confidentialityProfile.add(resumeField);
  }
  
  public void removeResumeField(ResumeField resumeField)
  {
    confidentialityProfile.remove(resumeField);
  }

  @SuppressWarnings("unused")
  public List<ResumeField> getConfidentialityProfile()
  {
    return confidentialityProfile;
  }
}
