package br.com.efransys.template.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import static br.com.efransys.template.util.Assert.has;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;


/**
 * Created by rafael-pestano on 22/11/16.
 */
@Named
@ApplicationScoped
public class AdminConfig implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(AdminConfig.class);

    private Properties adminConfigFile;//default config
    private Properties userConfigFile;//user defined properties
    private String loginPage;
    private String indexPage;
    // private String errorPage;
    private String dateFormat;
    private String templatePath;
    private Integer breadCrumbMaxSize;
    private boolean renderMessages;
    private boolean renderAjaxStatus;
    private boolean disableFilter;
    private boolean enableRipple;
    private boolean renderBreadCrumb;
    private boolean enableSlideMenu;
    private String rippleElements;
    private Integer ambiente;
    private String  msgAmbiente;
    private String  emailAmbiente;
    private String  quartzHtmlPlanilha;
    private String  comercialXlsDir;
    private String  comercialXlsCliente;


    @PostConstruct
    public void init() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        adminConfigFile = new Properties();
        userConfigFile = new Properties();
        try (InputStream is = cl.getResourceAsStream(("config/admin-config.properties"))) {
            userConfigFile.load(is);
        } catch (Exception ex) {
            log.warn("Could not load user defined admin template properties.", ex);
        }

        try (InputStream isDefault = cl.getResourceAsStream(("config/admin-config.properties"))) {
            adminConfigFile.load(isDefault);
        } catch (Exception ex) {
            log.error("Could not load admin template default properties.", ex);
        }

        loadDefaults();
    }

    protected void loadDefaults() {
        loginPage = getProperty("admin.loginPage");
       // errorPage = getProperty("admin.errorPage");
        indexPage = getProperty("admin.indexPage");
        dateFormat = getProperty("admin.dateFormat");
        templatePath = getProperty("admin.templatePath");
        breadCrumbMaxSize = Integer.parseInt(getProperty("admin.breadcrumbSize"));
        renderMessages = Boolean.parseBoolean(getProperty("admin.renderMessages"));
        renderAjaxStatus = Boolean.parseBoolean(getProperty("admin.renderAjaxStatus"));
        disableFilter = Boolean.parseBoolean(getProperty("admin.disableFilter"));
        enableRipple = Boolean.parseBoolean(getProperty("admin.enableRipple"));
        renderBreadCrumb = Boolean.parseBoolean(getProperty("admin.renderBreadCrumb"));
        rippleElements = getProperty("admin.rippleElements");
        enableSlideMenu =  Boolean.parseBoolean(getProperty("admin.enableSlideMenu"));
        ambiente =  Integer.parseInt(getProperty("admin.ambiente"));
        msgAmbiente = getProperty("admin.ambiente." + ambiente + ".message");
        emailAmbiente = getProperty("admin.ambiente." + ambiente + ".email");
        quartzHtmlPlanilha = getProperty("quartz.html.planilha");
        comercialXlsDir = getProperty("comercial.xls.dir");
        comercialXlsCliente = getProperty("comercial.xls.cliente");

    }

    private String getProperty(String property) {
        return has(userConfigFile.getProperty(property)) ? userConfigFile.getProperty(property) : adminConfigFile.getProperty(property);
    }


    public String getLoginPage() {
        return loginPage;
    }

    public String getIndexPage() {
        return indexPage;
    }

//    public String getErrorPage() {
//        return errorPage;
//    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public void setIndexPage(String indexPage) {
        this.indexPage = indexPage;
    }

//    public void setErrorPage(String errorPage) {
//        this.errorPage = errorPage;
//    }

    public boolean isDisableFilter() {
        return disableFilter;
    }

    public void setDisableFilter(boolean disableFilter) {
        this.disableFilter = disableFilter;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public Integer getBreadCrumbMaxSize() {
        return breadCrumbMaxSize;
    }

    public void setBreadCrumbMaxSize(Integer breadCrumbMaxSize) {
        this.breadCrumbMaxSize = breadCrumbMaxSize;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public boolean isRenderMessages() {
        return renderMessages;
    }

    public void setRenderMessages(boolean renderMessages) {
        this.renderMessages = renderMessages;
    }

    public boolean isRenderAjaxStatus() {
        return renderAjaxStatus;
    }

    public void setRenderAjaxStatus(boolean renderAjaxStatus) {
        this.renderAjaxStatus = renderAjaxStatus;
    }

    public boolean isEnableRipple() {
        return enableRipple;
    }

    public void setEnableRipple(boolean enableRipple) {
        this.enableRipple = enableRipple;
    }

    public boolean isRenderBreadCrumb() {
        return renderBreadCrumb;
    }

    public void setRenderBreadCrumb(boolean renderBreadCrumb) {
        this.renderBreadCrumb = renderBreadCrumb;
    }

    public String getRippleElements() {
        return rippleElements;
    }

    public void setRippleElements(String rippleElements) {
        this.rippleElements = rippleElements;
    }

    public boolean isEnableSlideMenu() {
        return enableSlideMenu;
    }

    public void setEnableSlideMenu(boolean enableSlideMenu) {
        this.enableSlideMenu = enableSlideMenu;
    }

    public static Logger getLog() {
        return log;
    }

    public Properties getAdminConfigFile() {
        return adminConfigFile;
    }

    public void setAdminConfigFile(Properties adminConfigFile) {
        this.adminConfigFile = adminConfigFile;
    }

    public Properties getUserConfigFile() {
        return userConfigFile;
    }

    public void setUserConfigFile(Properties userConfigFile) {
        this.userConfigFile = userConfigFile;
    }

    public Integer getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Integer ambiente) {
        this.ambiente = ambiente;
    }

    public String getMsgAmbiente() {
        return msgAmbiente;
    }

    public void setMsgAmbiente(String msgAmbiente) {
        this.msgAmbiente = msgAmbiente;
    }

    public String getEmailAmbiente() {
        return emailAmbiente;
    }

    public void setEmailAmbiente(String emailAmbiente) {
        this.emailAmbiente = emailAmbiente;
    }

    public String getQuartzHtmlPlanilha() {
        return quartzHtmlPlanilha;
    }

    public void setQuartzHtmlPlanilha(String quartzHtmlPlanilha) {
        this.quartzHtmlPlanilha = quartzHtmlPlanilha;
    }

    public String getComercialXlsCliente() {
        return comercialXlsCliente;
    }

    public void setComercialXlsCliente(String comercialXlsCliente) {
        this.comercialXlsCliente = comercialXlsCliente;
    }

    public String getComercialXlsDir() {
        return comercialXlsDir;
    }

    public void setComercialXlsDir(String comercialXlsDir) {
        this.comercialXlsDir = comercialXlsDir;
    }
}
