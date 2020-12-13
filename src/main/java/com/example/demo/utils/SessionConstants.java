package com.example.demo.utils;

public class SessionConstants {
    public static final String SESSION_KEY_PREFIX = "DILI_MANAGE_SESSION_";
    public static final String CACHED_RESOURCE_LIST_KEY = "DILI_MANAGE_RESOURCE_LIST";
    public static final String LOGGED_USER = "common:loggedUser";
    public static final String RESOURCE = "common:resource";
    public static final String SESSION_ID = "SessionId";
    public static final String AUTH_KEY = "authKey";
    public static final String MANAGE_PERMISSION_CONTEXT = "manage.permission_context";
    public static Integer SESSION_TIMEOUT = 86400;
    public static Integer SESSIONID_USERID_TIMEOUT = 86400;
    public static Integer COOKIE_TIMEOUT;
    public static Integer NEVER_EXPIRE;
    public static final String USER_LOGIN_KEY = "manage:user:login:userId:";
    public static final String USER_ROLES_KEY = "manage:userRole:userId:";
    public static final String USER_DATAAUTH_KEY = "manage:userDataAuth:userId:";
    public static final String USER_CURRENT_KEY = "manage:current:userId:";
    public static final String ROLE_RES_KEY = "manage:roleRes:roleId:";
    public static final String USER_RESURL_KEY = "manage:userResUrl:userId:";
    public static final String ALL_RES_KEY = "manage:allResUrl";
    public static final String RES_GRP_KEY = "manage:resGrpId:";
    public static final String SESSION_USERID_KEY = "manage:sessionUserId:sessionId";
    public static final String USERID_SESSION_KEY = "manage:userIdSession:userId:";
    public static final String ROLE_USER_KEY = "manage:roleUser:roleId:";
    public static final String RES_ROLE_KEY = "manage:resRole:resId:";
    public static final String DATA_AUTH_KEY = "manage:dataAuth:";
    public static final String URL_RESGRP_KEY = "manage:urlResGrp:urlstr:";
    public static final String KICK_OLDSESSIONID_KEY = "manage:kickOldSessionId:";

    public SessionConstants() {
    }

    static {
        COOKIE_TIMEOUT = SESSION_TIMEOUT * 48;
        NEVER_EXPIRE = -1;
    }
}
