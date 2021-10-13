import java.awt.*;
import java.util.HashMap;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    HashMap<Location, Waypoint> openVertices = new HashMap<>();
    HashMap<Location, Waypoint> closedVertices = new HashMap<>();

    public AStarState(Map2D map) {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    public Map2D getMap() {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint() {
        Waypoint waypoint = null;
        for (Waypoint wp : openVertices.values()){
            if (waypoint == null || wp.getTotalCost() < waypoint.getTotalCost()) {
                waypoint = wp;
            }
        }
        return waypoint;
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP) {
        if (openVertices.containsKey(newWP.getLocation())){
            if (newWP.getPreviousCost() > openVertices.get(newWP.getLocation()).getPreviousCost()){
                return false;
            }
        }
        openVertices.put(newWP.getLocation(), newWP);
        return true;
    }


    /** Returns the current number of open waypoints.
     * **/
    public int numOpenWaypoints() {
        return openVertices.size();
    }

    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc) {
        closedVertices.put(loc, openVertices.get(loc));
        openVertices.remove(loc);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc) {
        return closedVertices.containsKey(loc);
    }
}

