package test;

import entities.Academy;
import services.ServiceAcademy;
import utils.MyDataBase;

public class Main {
    public static void main(String[] args){
        MyDataBase cnx = MyDataBase.getInstance();

        Academy a3 = new Academy(7,"louay ", "mohamed ali ","aa","jj");

        ServiceAcademy sa = new ServiceAcademy();

        //sa.addEntity(a3);
        //sa.deleteEntity(5);
        sa.updateEntity(a3);

    }
}
