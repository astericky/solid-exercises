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
    this.setCompanyInformation(companyInformation);
    this.setDescription(description);
    this.setCompensation(compensation);
    this.setRecruiter(recruiter);
    this.setLocation(location);
    this.setDate(date);
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

  public JobLocation getLocation()
  {
    return location;
  }

  public void setLocation(JobLocation location)
  {
    this.location = location;
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
