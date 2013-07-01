package com.theladders.solid.isp.oldjob;

import java.util.Collection;
import java.util.List;

import com.theladders.solid.isp.oldjob.stubs.City;
import com.theladders.solid.isp.oldjob.stubs.Discipline;
import com.theladders.solid.isp.oldjob.stubs.Experience;
import com.theladders.solid.isp.oldjob.stubs.Industry;
import com.theladders.solid.isp.oldjob.stubs.JobFunction;
import com.theladders.solid.isp.oldjob.stubs.Region;
import com.theladders.solid.isp.oldjob.JobStatus;
import com.theladders.solid.isp.oldjob.stubs.PositionLevel;
import com.theladders.solid.isp.oldjob.stubs.Sector;

/**
 * Job Interface.
 */
public interface Job extends CompanyInformation, 
                             Compensation,
                             Date,
                             InternalInformation,
                             JobDescription,
                             JobStatus,
                             Location,
                             Recruiter
{

  /**
   * Returns a unique identifier for this job. In the web application, this currently maps to
   * job_location_id in the Database. Scripts may use other values.
   *
   * @return unique identifier for this job.
   */
  int getJobId();

  /**
   * Returns the real job_id.
   *
   * @return job id
   */
  Integer getParentJobId();
  int getJobSiteId();
  
  String getCompany();
  Integer getCompanySize();
  Sector getSector();
  
  String getCompensation();
  String getCompensationSalary();
  String getCompensationBonus();
  String getCompensationOther();

  Date getOriginalPublicationDate();
  Date getPublicationDate();
  Date getEntryDate();
  Date getUpdateTime();  
  
  City getCity();
  Region getRegion();
  String getLocation();

  String getTitle();
  PositionLevel getPositionLevel();
  String getDescription();
  String getShortDescription();
  List<Discipline> getDisciplines();
  Experience getExperience();
  String reportsTo();
  Industry getIndustry();

  /**
   * Get the (internally set) editor's note.
   *
   * @return editor's note.
   */
  String getEditorNote();  
  
  /**
   * Get the URL for this job. This is only valid for external (harvested) jobs (! isJobReq).
   *
   * @return URL for this job.
   */
  String getUrl();

  /**
   * Does the job have a particular status? There's a legacy thing where a job could have more than
   * one status, hence this method... Status should be moved out of PublicationInfo though, and this
   * should be a getStatus() method...
   *
   * @param status
   *          status to check against.
   * @return true if job has this status, false otherwise.
   */
  boolean hasStatus(JobStatus status);

  boolean isDeleted();

  boolean isExpired();

  /**
   * Is this job filled?
   *
   * @return true if this job is filled, false otherwise.
   */
  boolean isFilled();

  /**
   * Is this job a Marketing job? If this flag is set, basic access is allowed to this job (where
   * otherwise it would be premium) from certain landing pages.
   *
   * @return true if this is marked for marketing, false otherwise.
   */
  // TODO: This should only ever be true for JobReq, refactor into the JobReq interface
  boolean isMarketing();

  Collection<JobFunction> getJobFunctions();
}
