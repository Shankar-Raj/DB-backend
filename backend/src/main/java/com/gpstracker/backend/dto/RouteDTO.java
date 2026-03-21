package com.gpstracker.backend.dto;

import java.util.List;

public class RouteDTO {

    private Long   id;
    private String routeCode;
    private String routeType;
    private String start;           // matches React field name
    private String end;             // matches React field name
    private String startTime;       // "HH:mm"
    private String endTime;         // "HH:mm"
    private String totalDistance;   // String to match React number input
    private List<RouteStationDTO> stations;

    public Long getId()                              { return id; }
    public void setId(Long id)                       { this.id = id; }
    public String getRouteCode()                     { return routeCode; }
    public void setRouteCode(String v)               { this.routeCode = v; }
    public String getRouteType()                     { return routeType; }
    public void setRouteType(String v)               { this.routeType = v; }
    public String getStart()                         { return start; }
    public void setStart(String v)                   { this.start = v; }
    public String getEnd()                           { return end; }
    public void setEnd(String v)                     { this.end = v; }
    public String getStartTime()                     { return startTime; }
    public void setStartTime(String v)               { this.startTime = v; }
    public String getEndTime()                       { return endTime; }
    public void setEndTime(String v)                 { this.endTime = v; }
    public String getTotalDistance()                 { return totalDistance; }
    public void setTotalDistance(String v)           { this.totalDistance = v; }
    public List<RouteStationDTO> getStations()       { return stations; }
    public void setStations(List<RouteStationDTO> v) { this.stations = v; }
}