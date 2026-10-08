package leaderboard;

// attributes
private String filePath;                          // "leaderboard.txt"
private ArrayList<LeaderboardEntry> entries;

public Leaderboard(String filePath)               // constructor, starts with an empty list
public void load()
        // reads the file line by line, fromFileLine for each, adds to entries
        // file doesn't exist yet → just keep the list empty
public void save()
        // writes every entry's toFileLine() to the file, one per line
public void recordWin(String name)
        // findEntry: found → addWin(), not found → add new entry with 1 win
        // then save()
public ArrayList<LeaderboardEntry> getSortedEntries()
        // entries sorted by wins, most first
private LeaderboardEntry findEntry(String name)
        // compare with equalsIgnoreCase, return null if not found
