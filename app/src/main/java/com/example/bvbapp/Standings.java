package com.example.bvbapp;

public class Standings implements Comparable {
    Integer pos;
    Integer playedGames;
    String teamName;
    Integer points;

    public Standings() {
    }

    public Standings(Integer pos, Integer playedGames, String teamName, Integer points) {
        this.pos = pos;
        this.playedGames = playedGames;
        this.teamName = teamName;
        this.points = points;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Integer getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(Integer playedGames) {
        this.playedGames = playedGames;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public int compareTo(Object o) {
    if(this.pos>((Standings) o).pos)
    return 1;
    else if(this.pos<((Standings) o).pos)
        return -1;
    else
        return 0;
    }
}
