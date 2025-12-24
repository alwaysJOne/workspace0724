package com.kh.commu.global.common;

public class CommonEnums {

    public enum Status {
        Y, N;

        public static Status getDefault() {
            return Y;
        }
    }
}

