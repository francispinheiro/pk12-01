package br.com.efransys.erp.bean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by jrzielinski on 07/01/17.
 */
@Named
@SessionScoped
public class SkinMB implements Serializable {
	
    private String theme = "skin-teal";

    // light skin-blue; skin-green; skin-red; skin-yellow; skin-purple; skin-teal; teste bg-red-gradient
    public void changeTheme(String theme){
        this.theme = theme;
    }


    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
