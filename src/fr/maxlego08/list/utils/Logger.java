package fr.maxlego08.list.utils;

import java.util.Calendar;

public class Logger {
	
	private final Calendar calendar = Calendar.getInstance();
	
	public void log(String message){
		System.out.println(get() + message);
	}
	
	public void log(String message, Object...args){
		log(String.format(message, args));
	}
	
	private String get(){
		return "["+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND)+"] ";
	}
	
}
