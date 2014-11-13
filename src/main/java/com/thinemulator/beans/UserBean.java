package com.thinemulator.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserBean {

	 @NotNull
	    @Size(max = 64)
	     public String username;
	    
	    @Size(max = 64)
	    public String password;

	    @NotNull
	    @Size(max = 64)
	    public String email;

}
