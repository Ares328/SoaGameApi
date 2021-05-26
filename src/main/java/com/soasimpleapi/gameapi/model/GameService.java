package com.soasimpleapi.gameapi.model;

import com.soasimpleapi.gameapi.db.GameRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public Iterable<Game> getAllGames(){
        return gameRepository.findAll();
    }

    public Game addGame(Game bus) throws ServiceException {
        return gameRepository.save(bus);
    }

    public void deleteGame(Game game){
        gameRepository.delete(game);
    }

    public void updateGame(Game oldgame,Game newgame){
        Optional<Game> inbound= gameRepository.findById(oldgame.getId());
        newgame.setId(inbound.get().getId());
        gameRepository.save(newgame);

    }


    public Game getGameByid(long id){
        return gameRepository.findById(id).get();
    }
}
