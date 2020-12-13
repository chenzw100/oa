package com.example.demo.utils;

import org.apache.logging.log4j.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PermissionContext {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Object handler;
    private String referer = "";
    private String uri;
    private String url;
    private String queryString;
    private String sessionId;
    private Long userId;

    private String html;


    public PermissionContext() {
    }

    public PermissionContext(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        this.setReq(req);
        this.resp = resp;
        this.handler = handler;
    }

    public String getReferer() {
        return this.referer;
    }

    public String getUri() {
        return this.uri;
    }

    public HttpServletRequest getReq() {
        return this.req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
        this.referer = req.getHeader("referer");
        this.uri = req.getRequestURI();
        this.url = req.getRequestURL().toString();
        this.queryString = req.getQueryString();
    }

    public HttpServletResponse getResp() {
        return this.resp;
    }

    public void setResp(HttpServletResponse resp) {
        this.resp = resp;
    }

    public Object getHandler() {
        return this.handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getQueryString() {
        return this.queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }



    /*public String makePath(String s) {
        if (this.config.getDomain().endsWith("/")) {
            if (s.startsWith("/")) {
                s = s.substring(1);
            }
        } else if (!s.startsWith("/")) {
            s = "/" + s;
        }

        return this.config.getDomain() + s;
    }*/

   /* public void nonPermission() {
        try {
            String requestType = this.req.getHeader("X-Requested-With");
            if (requestType == null) {
                this.sendRedirect("/nonAccessable.do");
                return;
            }

            String path = this.makePath("/nonAccessable.do");
            this.resp.setStatus(403);
            this.resp.addHeader("nonAccessable", path);
            this.resp.getWriter().write("nonAccessable");
            this.resp.flushBuffer();
        } catch (IOException var3) {
            this.log.error(var3.getMessage(), var3);
        }

    }

    public void noAccess() throws IOException {
        String contentType = this.req.getHeader("Content-Type");
        if (Strings.isNotBlank(contentType) && contentType.contains("application/json")) {
            this.resp.setContentType("application/json");
            BaseOutput<Object> output = BaseOutput.failure("未登录");
            output.setCode("401");
            this.setHtml(JSON.toJSONString(output));
        } else {
            String requestType = this.req.getHeader("X-Requested-With");
            if (requestType == null) {
                this.sendRedirect("/noAccess.do");
            } else {
                String path = this.makePath("/noAccess.do");
                this.resp.addHeader("noAccess", path);
                this.resp.setStatus(401);
                this.resp.getWriter().write("noAccess");
                this.resp.flushBuffer();
            }
        }
    }

    public RedisManager getRM() {
        if (this.rm == null) {
            this.rm = new RedisManager(this.config.getRedisUtil());
        }

        return this.rm;
    }*/

    public String getSessionId() {
        if (this.sessionId == null) {
            this.sessionId = this.req.getParameter("SessionId");
            if (Strings.isBlank(this.sessionId)) {
                this.sessionId = this.req.getHeader("SessionId");
            }

            if (Strings.isNotBlank(this.sessionId)) {
                WebContent.setCookie("SessionId", this.sessionId);
            } else {
                this.sessionId = WebContent.getCookieVal("SessionId");
            }
        }

        return this.sessionId;
    }

    /*public UserTicket getUser() {
        if (this.getSessionId() == null) {
            return null;
        } else {
            UserTicket user = this.getRM().getUserRedis().getUserForId(this.getUserId());
            return user != null ? user : this.getUserFor(this.getSessionId());
        }
    }

    public List<UserTicket> getAuthorizer() {
        String authKey = this.req.getParameter("authKey");
        if (Strings.isBlank(authKey)) {
            authKey = this.req.getHeader("authKey");
            if (Strings.isBlank(authKey)) {
                return null;
            }
        }

        List<UserTicket> auths = new ArrayList();
        String[] arr$ = authKey.split(",");
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String a = arr$[i$];
            auths.add(this.getUserFor(a));
        }

        return auths;
    }

    public UserTicket getUserFor(String sessionId) {
        return Strings.isBlank(sessionId) ? null : this.getRM().getUserRedis().getUser(sessionId);
    }

    public Long getUserId() {
        if (this.getSessionId() == null) {
            return null;
        } else {
            if (this.userId == null) {
                this.userId = this.getRM().getUserRedis().getSessionUserIdKey(this.getSessionId());
            }

            return this.userId;
        }
    }*/

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() {
        return this.html;
    }
}
