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

}
