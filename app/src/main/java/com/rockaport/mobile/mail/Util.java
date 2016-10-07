package com.rockaport.mobile.mail;

public class Util {
    private static final int REQUEST_CODE_MASK = 0x0000ffff;

    public static int getRequestCode(Class classType) {
        return REQUEST_CODE_MASK & classType.hashCode();
    }
}
