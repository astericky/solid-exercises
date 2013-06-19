package com.theladders.solid.ocp.resume;

public class ConfidentialItem
{
  private String phrase;
  private boolean isConfidential;
  
  public ConfidentialItem(String phrase, boolean isConfidential) {
   this.phrase = phrase;
   this.isConfidential = isConfidential;
  }
  
  public boolean getConfidentiality() {
    return isConfidential;
  }
}
  
