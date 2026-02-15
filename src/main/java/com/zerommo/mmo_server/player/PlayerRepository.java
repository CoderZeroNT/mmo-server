package com.zerommo.mmo_server.player;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface PlayerRepository extends MongoRepository<Player, String> {
    Optional<Player> findByNameIgnoreCase(String name);
    List<Player> findByUserId(String userId);
    long countByUserId(String userId);
}
