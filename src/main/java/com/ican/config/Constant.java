package com.ican.config;

import com.ican.SpringContextHolder;
import org.springframework.context.ApplicationContext;

public class Constant {

    public static ApplicationContext BF = null;

    public static IcanFacade IcanFacade;
    public static ServiceFacade ServiceFacade;
    public static DaoFacade DaoFacade;

    static {
        if (BF == null) {
            BF = SpringContextHolder.getApplicationContext();
            if (BF != null) {
                if (IcanFacade == null) {
                    IcanFacade = (IcanFacade) BF.getBean("IcanFacade");
                }
                if (ServiceFacade == null) {
                    ServiceFacade = (ServiceFacade) BF.getBean("ServiceFacade");
                }
                if (DaoFacade == null) {
                    DaoFacade = (DaoFacade) BF.getBean("DaoFacade");
                }
            }
        }

    }
}
