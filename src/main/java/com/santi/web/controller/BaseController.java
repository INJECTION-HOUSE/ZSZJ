package  com.santi.web.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.santi.core.common.entity.ErrorBean;
import com.santi.core.common.entity.PagingProperties;
 
public class BaseController {

	protected Logger logger = Logger.getLogger(getClass());
	public static final String REQUEST_STATUS = "status";
	public static final String REQUEST_SUCCESS = "data";
	public static final String REQUEST_FAILED = "failed";
	// Paging fields
	public static final String PAGING_PROPERTIES = "pagingInfo";
	public static final String PAGING_LIST = "models";
	
	protected  Map<String,Object> successResult(Object value){
		
		Map<String,Object> result=new HashMap<String,Object>();
		result.put(REQUEST_SUCCESS, value);
		return result;
	}
	
	protected  Map<String,Object> successPageList(PagingProperties pageInfo,Object list){
		
		Map<String,Object> result=new HashMap<String,Object>();
		Map<String,Object> pageListMap=new HashMap<String,Object>();
		pageListMap.put(PAGING_PROPERTIES, pageInfo);
		pageListMap.put(PAGING_LIST, list);
		
		result.put(REQUEST_SUCCESS, pageListMap);
		
		return result;
	}
	
	
	
	protected  Map<String,Object> successAttendanceList(PagingProperties pageInfo,Object list){
		//Map<String,Object> result=new HashMap<String,Object>();
		Map<String,Object> pageListMap=new HashMap<String,Object>();
		pageListMap.put(PAGING_PROPERTIES, pageInfo);
		pageListMap.put(PAGING_LIST, list);				
		//result.put(REQUEST_SUCCESS, pageListMap);
		return pageListMap;
	}
	
	
	  public  Map<String,Object> failedResult(Integer errorType,String value){
			
			Map<String,Object> result=new HashMap<String,Object>();
			ErrorBean errorBean=new ErrorBean();
			errorBean.setErrorType(errorType);
			errorBean.setErrorMesg(value);
			result.put(REQUEST_FAILED,errorBean );
			return result;
		}
	  
	  @ExceptionHandler (Exception.class)
	    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
	    public ModelAndView handleAllExceptions(Exception ex) {
		  ex.printStackTrace();
	        return null;
	    }
	  
	  public static int getMonthLastDay(int year, int month)
		  {
		  Calendar a = Calendar.getInstance();
		  a.set(Calendar.YEAR, year);
		  a.set(Calendar.MONTH, month - 1);
		  a.set(Calendar.DATE, 1);
		  a.roll(Calendar.DATE, -1);
		  int maxDate = a.get(Calendar.DATE);
		  return maxDate;

		  }

}
