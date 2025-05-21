package com.server.utils;

public class WaitListResponseFactory {
    public static WaitListResponse success() {
        return new WaitListResponse(true, null);
    }

    public static WaitListResponse alreadyExists() {
        return new WaitListResponse(true, "You're already on the waitlist!");
    }

    public static WaitListResponse error(String message) {
        return new WaitListResponse(false, message);
    }
}
