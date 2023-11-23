package edu.project3.logReport;

import java.util.Objects;

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

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseCode that = (ResponseCode) o;
        return code == that.code && Objects.equals(codeName, that.codeName)
            && Objects.equals(issuesCount, that.issuesCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, codeName, issuesCount);
    }
}
