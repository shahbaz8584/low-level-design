package DesignPattern.behavioralDesign.ObserverPattern.observable;

import DesignPattern.behavioralDesign.ObserverPattern.observer.NotifyAlertObserver;

public interface StockManage {

    void addStock( NotifyAlertObserver observer);

    void removeStock(NotifyAlertObserver observer);

    void notifyEveryOne();

    void setStock(int newStock);
}
