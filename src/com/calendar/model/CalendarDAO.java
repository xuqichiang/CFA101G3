package com.calendar.model;

import java.util.List;

public interface CalendarDAO {
	
	//抓取所有場地訂單的預約日期
	public List<CalendarVO> getAllLocStroke();
	//抓取一個賣家的場地訂單預約日期
	public List<CalendarVO> getLocmemStroke(Integer mem_id);
	//抓取所有攝影訂單的預約日期
	public List<CalendarVO> getAllPhoStroke();
	//抓取一個賣家的攝影訂單預約日期
	public List<CalendarVO> getPhomemStroke(Integer mem_id);
}
