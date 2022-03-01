package Game;

import java.security.Key;
import java.util.Map;

public class World {

    private final Map<TileType, Tile> _tiles;

    public World(Map<TileType, Tile> tiles) {
        _tiles = tiles;
    }

    public void UnlockedAble(Data data) {

        if (data.IsAllowedAncient == true)
            _tiles.get(TileType.Ancient).Unlocked();
        else
            _tiles.get(TileType.Ancient).Locked();

        if (data.IsAllowedSlave == true)
            _tiles.get(TileType.Slave).Unlocked();
        else
            _tiles.get(TileType.Slave).Locked();

        if (data.IsAllowedFeudalism == true)
            _tiles.get(TileType.Feudalism).Unlocked();
        else
            _tiles.get(TileType.Feudalism).Locked();

        if (data.IsAllowedProletarian == true)
            _tiles.get(TileType.Proletariat).Unlocked();
        else
            _tiles.get(TileType.Proletariat).Locked();
    }

    public void LockedAll() {
        _tiles.get(TileType.Ancient).Locked();
        _tiles.get(TileType.Slave).Locked();
        _tiles.get(TileType.Feudalism).Locked();
        _tiles.get(TileType.Proletariat).Locked();
    }
}
