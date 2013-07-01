package com.theladders.solid.isp.oldjob;

import com.theladders.solid.isp.oldjob.stubs.City;
import com.theladders.solid.isp.oldjob.stubs.Region;

public interface Location
{
  City getCity();
  Region getRegion();
  String getLocation();
}
