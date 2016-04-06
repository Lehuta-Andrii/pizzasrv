package org.study.pizzaservice.domain.accumulativecard;

public interface AccumulativeCard {
    
    public int getId();

    public double getSum();

    public void setSum(double sum);

    public void addToCard(double sum);

}
