package be.sami.Model;

public interface Observable {

    void addObserver(Observer o);
//    void removeObserver(Observer o);
    void notifyObserver(Object object,int param);

}
