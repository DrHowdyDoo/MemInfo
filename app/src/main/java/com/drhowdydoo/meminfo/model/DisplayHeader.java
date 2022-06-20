package com.drhowdydoo.meminfo.model;

public class DisplayHeader {


    private String totalMem;
    private String usedMem;
    private String freeMem;
    private int usedFreeRatio;

    public DisplayHeader(String totalMem, String usedMem, String freeMem, int usedFreeRatio) {
        this.totalMem = totalMem;
        this.usedMem = usedMem;
        this.freeMem = freeMem;
        this.usedFreeRatio = usedFreeRatio;
    }

    public String getTotalMem() {
        return totalMem;
    }

    public void setTotalMem(String totalMem) {
        this.totalMem = totalMem;
    }

    public String getUsedMem() {
        return usedMem;
    }

    public void setUsedMem(String usedMem) {
        this.usedMem = usedMem;
    }

    public String getFreeMem() {
        return freeMem;
    }

    public void setFreeMem(String freeMem) {
        this.freeMem = freeMem;
    }

    public int getUsedFreeRatio() {
        return usedFreeRatio;
    }

    public void setUsedFreeRatio(int usedFreeRatio) {
        this.usedFreeRatio = usedFreeRatio;
    }
}
