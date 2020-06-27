package com.boostbrain.brainbooster;

public interface Communicator {

    public void passData(int data);
    public int getData();

    public void shareData(String[] data);
    public String[] getShareData();

}
