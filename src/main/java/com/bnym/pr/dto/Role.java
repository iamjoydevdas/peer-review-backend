package com.bnym.pr.dto;

import java.util.HashMap;
import java.util.Map;

public enum Role {
	
	ADMIN(1), 
	REVIEWER(2), 
	EVALUTER(3);
	
	private static final Map<Integer, Role> lookup = new HashMap();
    static {
        for(Role d : Role.values())
            lookup.put(d.getRoleValue(), d);
    }
	
	private Integer roleValue; 
	  
   public Integer getAction() 
   { 
       return this.roleValue; 
   } 
 
   private Role(Integer roleValue) 
   { 
       this.roleValue = roleValue; 
   } 
   
   public int getRoleValue() { 
   	return roleValue; 
   }
   
   public static Role get(int roleValue) { 
        return lookup.get(roleValue); 
   }
}


