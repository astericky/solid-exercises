package com.theladders.solid.isp.oldjob;

import java.util.List;
import com.theladders.solid.isp.oldjob.stubs.Discipline;
import com.theladders.solid.isp.oldjob.stubs.Experience;
import com.theladders.solid.isp.oldjob.stubs.Industry;
import com.theladders.solid.isp.oldjob.stubs.PositionLevel;

public interface JobDescription
{
  String getTitle();
  PositionLevel getPositionLevel();
  String getDescription();
  String getShortDescription();
  List<Discipline> getDisciplines();
  Experience getExperience();
  String reportsTo();
  Industry getIndustry();
}
