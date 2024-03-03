package com.silverhaired.com;

public interface VideoViewListener
{
    static final int START = 0;
    static final int PAUSE = 1;
    static final int STOP = 2;

    public void videoViewCallback(int aAction);
}
