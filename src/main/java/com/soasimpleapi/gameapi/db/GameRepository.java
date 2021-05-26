package com.soasimpleapi.gameapi.db;

import com.soasimpleapi.gameapi.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game,Long> {

}
