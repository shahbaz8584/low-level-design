package DesignPattern.behavioralDesign.ObserverPattern.observable;

import java.util.ArrayList;
import java.util.List;

import DesignPattern.behavioralDesign.ObserverPattern.observer.NotifyAlertObserver;

public class IphoneStockObservable implements StockManage {

    List<NotifyAlertObserver> observersList = new ArrayList<>();
    int stock = 0;

    @Override
    public void addStock(NotifyAlertObserver observer) {
        observersList.add(observer);
        
    }

    @Override
    public void notifyEveryOne() {
        for(NotifyAlertObserver observer : observersList){
            observer.updateStock();
        }
        
    }

    @Override
    public void removeStock(NotifyAlertObserver observer) {
        observersList.remove(observer);
        
    }

    @Override
    public void setStock(int newStock) {
        if(this.stock == 0){
            for(NotifyAlertObserver observer : observersList){
                observer.updateStock();
            }
        }
        stock = stock + newStock;
    }

    
}
