package com.theladders.solid.isp.oldjob;

public class JobOpening {
  private CompanyInformation companyInformation;
  private JobDescription description;
  private JobCompensation compensation;
  private JobLocation location;
  private Recruiter recruiter;
  private Date date;
  private JobStatus status;
  
  public JobOpening(CompanyInformation companyInformation, 
                    JobDescription description, 
                    JobCompensation compensation, 
                    Recruiter recruiter,
                    JobLocation location,
                    Date date)
  {
    this.companyInformation = companyInformation;
    this.description = description;
    this.location = location;
    this.compensation = compensation;
    this.recruiter = recruiter;
    this.date = date;
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

  public JobLocation getLocation()
  {
    return location;
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
