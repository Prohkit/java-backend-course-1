package edu.hw2.task4;

public record CallingInfo(String className, String methodName) {
    public static CallingInfo callingInfo() {
        int prevElementInStackTrace = 1;
        StackTraceElement[] traceElements = new Exception().getStackTrace();
        String className = traceElements[prevElementInStackTrace].getClassName();
        String methodName = traceElements[prevElementInStackTrace].getMethodName();
        return new CallingInfo(className, methodName);
    }
}
