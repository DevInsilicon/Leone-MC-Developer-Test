package dev.insilicon.leonemctest;

public class PlayerDataClass {
    // Some basic data such as Kills, Deaths, Money
    private String uuid;
    private double money;
    private double kills;
    private double deaths;

    public PlayerDataClass(String uuid, double money, double kills, double deaths) {
        this.uuid = uuid;
        this.money = money;
        this.kills = kills;
        this.deaths = deaths;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getKills() {
        return kills;
    }

    public void setKills(double kills) {
        this.kills = kills;
    }

    public double getDeaths() {
        return deaths;
    }

    public void setDeaths(double deaths) {
        this.deaths = deaths;
    }
}
