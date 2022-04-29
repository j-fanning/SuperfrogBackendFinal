package edu.tcu.cs.superfrogbackendfinal.domain;

public class StatusCode {
    public static final int SUCCESS = 200; //success
    public static final int FAILURE = 400; //failure
    public static final int UNAUTHORIZED = 401; //user or pass incorrect
    public static final int FORBIDDEN = 403; //insufficient right
    public static final int INTERNAL_SERVER_ERROR = 500; //repetitive ops
}
