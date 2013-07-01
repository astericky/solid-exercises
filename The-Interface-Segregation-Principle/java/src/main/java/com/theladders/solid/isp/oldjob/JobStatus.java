package com.theladders.solid.isp.oldjob;

public interface JobStatus
{
  boolean isPostedByRecruiter();
  boolean isDeleted();
  boolean isExpired();
  boolean isFilled();
  boolean hasStatus(JobStatus status);
}
