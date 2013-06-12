package com.theladders.solid.srp.job;

public interface Job
{
  int getJobId();
  String getTitle();
  boolean doesntExist();
}
