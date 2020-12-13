package com.example.demo.utils;

import javax.servlet.http.HttpServletRequest;


public class SessionContext {
    private static ThreadLocal<SessionContext> holder = new ThreadLocal();
    private PermissionContext pc;


    public static void resetLocal() {
        holder.remove();
    }

    private SessionContext() {
        PermissionContext config = (PermissionContext)WebContent.get("req");
        this.pc = config;
        holder.set(this);
    }

    public static synchronized SessionContext getSessionContext() {
        SessionContext context = (SessionContext)holder.get();
        if (context == null) {
            new SessionContext();
        }

        return (SessionContext)holder.get();
    }

    public static synchronized void remove() {
        holder.remove();
    }

   /* public UserTicket getUserTicket() {
        if (this.userTicket == null) {
            this.userTicket = this.pc.getUser();
        }

        return this.userTicket;
    }

    public UserTicket getUserTicket(String sessionId) {
        return this.pc.getUserFor(sessionId);
    }*/

    public Long getUserId() {
        HttpServletRequest request = (HttpServletRequest) WebContent.get("req");
        Object userId = request.getSession().getAttribute("userId");
        return Long.parseLong(userId.toString());
    }
    public String getUserName() {
        HttpServletRequest request = (HttpServletRequest) WebContent.get("req");
        Object userName = request.getSession().getAttribute("userName");
        return userName.toString();
    }

  /*  public List<UserTicket> getAuthorizer() {
        return this.pc.getAuthorizer();
    }

    public Pair<String> currentDataAuth(String key) {
        return this.currentDataAuth(this.pc.getUserId(), key);
    }

    public Pair<String> currentDataAuth(Long userId, String key) {
        String json = this.pc.getRM().getDataAuthRedis().currentdataAuth(userId, key);
        if (Strings.isBlank(json)) {
            return null;
        } else {
            Pair<String> pair = (Pair)Json.fromJson(new NutType(Pair.class, new Type[]{String.class}), json);
            return pair;
        }
    }

    public void refreshAuthData(String type, Integer mode, Object obj) {
        if (this.authDataApiService == null) {
            this.authDataApiService = new AuthDataApiService("", this.pc.getConfig().getDomain());
        }

        this.authDataApiService.refreshAuthData(type, mode, obj);
    }

    public UserInfoApiService fetchUserApi() {
        if (this.userInfoApiService == null) {
            this.userInfoApiService = new UserInfoApiService("", this.pc.getConfig().getDomain());
        }

        return this.userInfoApiService;
    }

    public List<Pair<String>> dataAuth(String key) {
        return this.dataAuth(this.getUserId(), key);
    }

    public List<Pair<String>> dataAuth(Long userId, String key) {
        List<Pair<String>> list = new ArrayList();
        return (List)(this.getUserId() == null ? list : WebContent.getPC().getRM().getDataAuthRedis().dataAuth(key, userId));
    }

    public static synchronized boolean hasAccess(String method, String url) {
        return hasAccess(WebContent.getPC().getUserId(), method, url);
    }

    public static synchronized boolean hasAccess(Long userId, String method, String url) {
        return WebContent.getPC().getRM().getUserResRedis().checkUserRes(userId, method, url);
    }*/
}
