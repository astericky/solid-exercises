package com.theladders.solid.isp.oldjob;

import java.util.ArrayList;
import java.util.List;

/**
 * Job Interface.
 */
public abstract class Job implements TheLaddersJob
{
  private CompanyInformation companyInformation;
  private JobDescription description;
  private JobCompensation compensation;
  private List<JobLocation> locations = new ArrayList<JobLocation>();
  private Recruiter recruiter;
  private Date date;
  private JobStatus status;
  private String editorNote = "";
  private String url = "";
  
  public Job(CompanyInformation companyInformation, 
             JobDescription description,
             List<JobLocation> locations,
             JobCompensation compensation, 
             Recruiter recruiter,
             Date date)
  {
    this.companyInformation = companyInformation;
    this.description = description;
    this.locations = locations;
    this.compensation = compensation;
    this.recruiter = recruiter;
    this.date = date;
  }
  
  public String getEditorNote() {
    return this.editorNote;
  }
  public String getUrl() {
    return this.url;
  }
  public boolean isMarketing() {
    return true;
  }

  public CompanyInformation getCompanyInformation()
  {
    return companyInformation;
  }

  public JobDescription getDescription()
  {
    return description;
  }

  public JobCompensation getCompensation()
  {
    return compensation;
  }

  public List<JobLocation> getLocations()
  {
    return locations;
  }

  public Recruiter getRecruiter()
  {
    return recruiter;
  }

  public Date getDate()
  {
    return date;
  }

  public JobStatus getStatus()
  {
    return status;
  }
}
