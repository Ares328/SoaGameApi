package com.soasimpleapi.gameapi.controller;


import com.soasimpleapi.gameapi.model.Game;
import com.soasimpleapi.gameapi.model.GameService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameRestController {
    @Autowired
    private GameService gameService;

    @GetMapping("/all")
    public Iterable<Game>getAllGames(){
        return gameService.getAllGames();
    }

    @PostMapping("/add")
    public Iterable<Game> addGame(@Valid @RequestBody Game game){
        try {
            gameService.addGame(game);
        }catch(ServiceException exc) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "game",exc);
        }
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public Game getGame(@PathVariable("id") long id){
        return gameService.getGameByid(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGame(@PathVariable("id") long id){
        gameService.deleteGame(gameService.getGameByid(id));
    }

    @PutMapping("/update/{id}")
    public void updateGame(@PathVariable("id") long id,@Valid @RequestBody Game game) {
        gameService.updateGame(gameService.getGameByid(id),game);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ResponseStatusException.class})
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else {
            errors.put(((ResponseStatusException) ex).getReason(), ex.getCause().getMessage());
        }
        return errors;
    }
}

/*{"name": "Battlefield1","description": "great large scale fps game", "price": 60}*/


/*CREATE*/
/*curl -X POST -H "Content-Type:application/json" --data "{\"name\": \"Battlefield1\", \"description\": \"great large scale fps game\", \"price\": 60}" http://localhost:8080/api/game/add*/
/*READ*/
/*curl http://localhost:8080/api/game/all*/
/*UPDATE*/
/*curl -X PUT -H "Content-Type:application/json" --data "{\"name\": \"Battlefield1\",\"description\": \"fps game\", \"price\": 50}" http://localhost:8080/api/game/update/1*/
/*DELETE*/
/*curl -X DELETE http://localhost:8080/api/game/delete/3*/