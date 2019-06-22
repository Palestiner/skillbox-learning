package model;

import entity.WorkTime;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

class XmlTagHandler extends DefaultHandler {
  private static final Logger log = Logger.getLogger(XmlTagHandler.class);
  private static SimpleDateFormat visitDateFormat =
      new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

  private HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    if (qName.equals("visit")) {
      Integer station = Integer.parseInt(attributes.getValue("station"));
      Date time = null;
      try {
        time = visitDateFormat.parse(attributes.getValue("time"));
      } catch (ParseException e) {
        log.error(e);
      }
      WorkTime workTime = voteStationWorkTimes.get(station);
      if (workTime == null) {
        workTime = new WorkTime();
        voteStationWorkTimes.put(station, workTime);
      }
      if (time != null) {
        workTime.addVisitTime(time.getTime());
      }
    }
  }

  public HashMap<Integer, WorkTime> getVoteStationWorkTimes() {
    return voteStationWorkTimes;
  }
}
