package edu.hw2.task1;

public record Constant(int constant) implements Expr {
    @Override
    public double evaluate() {
        return constant;
    }
}
