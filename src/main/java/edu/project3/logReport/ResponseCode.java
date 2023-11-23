package edu.project3.logReport;

public class ResponseCode {
    private final int code;
    private final String codeName;
    private final Integer issuesCount;

    public ResponseCode(int code, String codeName, Integer issuesCount) {
        this.code = code;
        this.codeName = codeName;
        this.issuesCount = issuesCount;
    }

    public int getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public Integer getIssuesCount() {
        return issuesCount;
    }
}
