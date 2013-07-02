package com.theladders.solid.isp.oldjob;

import com.theladders.solid.isp.oldjob.stubs.City;
import com.theladders.solid.isp.oldjob.stubs.Region;

public interface JobLocation
{
  City getCity();
  Region getRegion();
  String getLocation();
}
