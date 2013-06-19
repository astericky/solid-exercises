package com.theladders.solid.ocp.jobseeker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.theladders.solid.ocp.resume.Phrase;
import com.theladders.solid.ocp.resume.ConfidentialPhraseCategory;

public class JobseekerConfidentialityProfile
{
  private Map<String, List<Phrase>> confidentialityProfile;

  public JobseekerConfidentialityProfile()
  {
    confidentialityProfile = new HashMap<>();
  }

  public boolean resetConfidentialFlagsForCategory(ConfidentialPhraseCategory category)
  {
    boolean isChanged = false;

    List<Phrase> phrases = this.getConfidentialPhrases(category);
    if (phrases != null)
    {
      for (Phrase phrase : phrases)
      {
        if (phrase.isConfidential())
        {
          phrase.setConfidential(false);
          isChanged = true;
        }
      }
    }

    return isChanged;
  }

  private List<Phrase> getConfidentialPhrases(ConfidentialPhraseCategory category)
  {
    return confidentialityProfile.get(category.name());
  }
}
