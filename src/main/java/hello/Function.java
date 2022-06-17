package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hello.Application.ArenaUpdate;
import hello.Application.PlayerState;

@Service
public class Function {
    
    public List<PlayerState> locateList(ArenaUpdate arenaUpdate, boolean checkMyself) {

        List<PlayerState> resultList = new ArrayList<PlayerState>();
        String myselfHref = arenaUpdate.get_links().getSelf().getHref();
        
        Map<String, PlayerState> playerMap = arenaUpdate.getArena().getState();

        if (checkMyself) {
            PlayerState myself = playerMap.get(myselfHref);
            resultList.add(myself);
        } else {
            playerMap.remove(myselfHref);
            for (Map.Entry<String, PlayerState> entry:playerMap.entrySet()) {
                resultList.add(entry.getValue());
            }
        }

        return resultList;
        
    }
    
  public boolean isOthersInRange(PlayerState self, List<PlayerState> others) {
    Location selfLoc = new Location(self.x, self.y);
    switch (self.getDirection()) {
      case "N":
        return isOthersInFrontOfNorth(selfLoc, others);
      case "E":
        return isOthersInFrontOfEast(selfLoc, others);
      case "S":
        return isOthersInFrontOfSouth(selfLoc, others);
      case "W":
        return isOthersInFrontOfWest(selfLoc, others);
    }

    return false;
  }


  private boolean isOthersInFrontOfNorth(Location selfLoc, List<PlayerState> others) {
    return others.stream().anyMatch(player -> player.y <= (selfLoc.y - 3));
  }

  private boolean isOthersInFrontOfEast(Location selfLoc, List<PlayerState> others) {
    return others.stream().anyMatch(player -> player.x >= (selfLoc.x + 3));
  }

  private boolean isOthersInFrontOfSouth(Location selfLoc, List<PlayerState> others) {
    return others.stream().anyMatch(player -> player.y >= (selfLoc.y + 3));
  }

  private boolean isOthersInFrontOfWest(Location selfLoc, List<PlayerState> others) {
    return others.stream().anyMatch(player -> player.x <= (selfLoc.x - 3));
  }

}
