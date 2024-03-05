package interfaces;

import Entites.Coach;

import java.util.List;

public interface IServiceCoach<T> {
    public void addEntity2(T t);
    public void deleteEntity(T t);
    public void updateEntity2(T t);

    //public boolean coachExists(String name);
    public List<Coach> display();
}
