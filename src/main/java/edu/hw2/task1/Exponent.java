package edu.hw2.task1;

public record Exponent(Expr obj, double power) implements Expr {
    @Override
    public double evaluate() {
        return Math.pow(obj.evaluate(), power);
    }
}
