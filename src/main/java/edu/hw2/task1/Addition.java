package edu.hw2.task1;

public record Addition(Expr obj1, Expr obj2) implements Expr {
    @Override
    public double evaluate() {
        return obj1.evaluate() + obj2.evaluate();
    }
}
