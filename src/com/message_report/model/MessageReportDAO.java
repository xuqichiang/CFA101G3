package com.message_report.model;


interface MessageReportDAO {
          int insert(MessageReportVO messageReport);
        
          void findByMrepID(Integer mrep_id); 
        
          void updateMrepStatus(MessageReportVO messageReportStatus);
       
}
