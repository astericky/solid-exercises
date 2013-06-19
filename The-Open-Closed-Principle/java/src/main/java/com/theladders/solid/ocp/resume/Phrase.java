package com.theladders.solid.ocp.resume;

public class Phrase
{
  private boolean isConfidential;
  private String phrase;
  
  public Phrase(String phrase)
  {
    this.phrase = phrase;
  }

  public boolean isConfidential()
  {
    return isConfidential;
  }

  public void setConfidential(boolean isConfidential)
  {
    this.isConfidential = isConfidential;
  }
}
