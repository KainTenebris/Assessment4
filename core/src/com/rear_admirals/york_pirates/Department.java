package com.rear_admirals.york_pirates;

import static java.lang.Math.max;
import static java.lang.Math.pow;

public class Department {

    private final String name;
    private String product;
    private int base_price;
    private PirateGame pirateGame;

    public Department(String name, String product, PirateGame pirateGame) {
        this.name = name;
        this.product = product;
        this.base_price = 10;
        this.pirateGame = pirateGame;
    }

    public int getPrice() {
        if (product == "Defence") {
            return (int) (base_price * pow(1.5, max(0, pirateGame.getPlayer().getPlayerShip().getDefence() - 4)));
        } else if (product == "Attack"){
            return (int) (base_price * pow(1.5, max(0, pirateGame.getPlayer().getPlayerShip().getAttack() - 4)));
        } else if (product == "Accuracy"){
            return (int) (base_price *pow(1.5, max(0, pirateGame.getPlayer().getPlayerShip().getAccuracy() - 4)));
        }
        else {return 0;}
    }
    public String getName()         { return name;      }
    public String getProduct()      { return product;   }

    public boolean purchase(){
        if ( pirateGame.getPlayer().payGold(getPrice()) ) {
            if (product == "Defence") {
                pirateGame.getPlayer().getPlayerShip().setDefence(pirateGame.getPlayer().getPlayerShip().getDefence() + 2);
                return true;
            } else if (product == "Accuracy") {
                pirateGame.getPlayer().getPlayerShip().setAccuracy(pirateGame.getPlayer().getPlayerShip().getAccuracy() + 2);
                return true;
            }
            else if (product == "Attack") {
                pirateGame.getPlayer().getPlayerShip().setAttack(pirateGame.getPlayer().getPlayerShip().getAttack() + 2);
                return true;
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            return false;
        }
    }
}
