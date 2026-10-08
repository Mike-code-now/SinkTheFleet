package leaderboard;

// attributes
private String name;
private int wins;

public LeaderboardEntry(String name, int wins)   // constructor
public String getName()
public int getWins()
public void addWin()                              // wins++
public String toFileLine()                        // returns e.g. "Mike,3"
public static LeaderboardEntry fromFileLine(String line)
