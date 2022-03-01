package Game;

public class Account {

    private TileType _currentTile = TileType.Ancient;

    public Account() {}

    public void SetNextTile(TileType type) {
        int oldValue = _currentTile.ordinal();
        int newValue =  type.ordinal();

        if (oldValue < newValue)
            _currentTile = type;
    }
}
