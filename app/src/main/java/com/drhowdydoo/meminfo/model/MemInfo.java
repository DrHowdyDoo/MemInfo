package com.drhowdydoo.meminfo.model;

public class MemInfo {

    private String attribute;
    private String value;
    private boolean showIcon;

    public MemInfo(String attribute, String value, boolean showIcon) {
        this.attribute = attribute;
        this.value = value;
        this.showIcon = showIcon;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isShowIcon() {
        return showIcon;
    }

    public void setShowIcon(boolean showIcon) {
        this.showIcon = showIcon;
    }
}
