package com.bnym.pr.dto;

import java.util.HashMap;
import java.util.Map;

public enum Designation {
	
	ProgrammerAnalyst(1),
	Associate(2), 
	SeniorAssociate(3), 
	Manager(4), 
	SeniorManager(5), 
	Director(6),
	Anonymous(7);
	
	private static final Map<Integer, Designation> lookup = new HashMap();
     static {
         for(Designation d : Designation.values())
             lookup.put(d.getDesignationValue(), d);
     }
	
	private Integer designationValue; 
	  
    public Integer getAction() 
    { 
        return this.designationValue; 
    } 
  
    private Designation(Integer designationValue) 
    { 
        this.designationValue = designationValue; 
    } 
    
    public int getDesignationValue() { 
    	return designationValue; 
    }
    
    public static Designation get(int designationValue) { 
         return lookup.get(designationValue); 
    }
    
}

