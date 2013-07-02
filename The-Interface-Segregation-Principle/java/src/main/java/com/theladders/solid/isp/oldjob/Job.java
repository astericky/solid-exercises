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
             Recruiter recruiter)
  {
    this.setCompanyInformation(companyInformation);
    this.setDescription(description);
    this.setLocations(locations);
    this.setCompensation(compensation);
    this.setRecruiter(recruiter);
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

  public void setCompanyInformation(CompanyInformation companyInformation)
  {
    this.companyInformation = companyInformation;
  }

  public JobDescription getDescription()
  {
    return description;
  }

  public void setDescription(JobDescription description)
  {
    this.description = description;
  }

  public JobCompensation getCompensation()
  {
    return compensation;
  }

  public void setCompensation(JobCompensation compensation)
  {
    this.compensation = compensation;
  }

  public List<JobLocation> getLocations()
  {
    return locations;
  }

  public void setLocations(List<JobLocation> locations)
  {
    this.locations = locations;
  }

  public Recruiter getRecruiter()
  {
    return recruiter;
  }

  public void setRecruiter(Recruiter recruiter)
  {
    this.recruiter = recruiter;
  }

  public Date getDate()
  {
    return date;
  }

  public void setDate(Date date)
  {
    this.date = date;
  }

  public JobStatus getStatus()
  {
    return status;
  }

  public void setStatus(JobStatus status)
  {
    this.status = status;
  }
}
