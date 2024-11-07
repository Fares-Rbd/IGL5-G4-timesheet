package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.faces.webapp.FacesServlet;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TimesheetApplication.class)
class TimesheetApplicationTests {

    private final TimesheetApplication application = new TimesheetApplication();

    @Test
    void testMain() {
        // Just verify it doesn't throw any exception
        TimesheetApplication.main(new String[]{"--spring.profiles.active=test"});
        assertTrue(true);
    }

    @Test
    void testServletRegistrationBean() {
        ServletRegistrationBean<FacesServlet> registrationBean = application.servletRegistrationBean();

        assertNotNull(registrationBean, "ServletRegistrationBean should not be null");
        assertEquals("*.jsf", registrationBean.getUrlMappings().iterator().next(),
                "URL pattern should be *.jsf");
        assertTrue(registrationBean.getServlet() instanceof FacesServlet,
                "Servlet should be instance of FacesServlet");
    }

    @Test
    void testRewriteFilter() {
        FilterRegistrationBean<?> filterBean = application.rewriteFilter();

        assertNotNull(filterBean, "FilterRegistrationBean should not be null");
        assertEquals("/*", filterBean.getUrlPatterns().iterator().next(),
                "URL pattern should be /*");
    }
}