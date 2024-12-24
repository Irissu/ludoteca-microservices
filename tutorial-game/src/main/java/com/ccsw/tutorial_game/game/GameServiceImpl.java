package com.ccsw.tutorial_game.game;

import com.ccsw.tutorial_game.common.SearchCriteria;
import com.ccsw.tutorial_game.game.model.Game;
import com.ccsw.tutorial_game.game.model.GameDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Game get(Long id) {

        //return authorRepository.findById(id).orElse(null);
        return gameRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Game> find(String title, Long idCategory) {
        GameSpecification titleSpec = new GameSpecification(new SearchCriteria("title", ":", title));
        GameSpecification categorySpec = new GameSpecification(new SearchCriteria("idCategory", ":", idCategory));

        Specification<Game> spec = Specification.where(titleSpec).and(categorySpec);

        return this.gameRepository.findAll(spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, GameDto dto) {

        Game game;

        game = (id == null) ? new Game() : this.gameRepository.findById(id).orElse(null);

        BeanUtils.copyProperties(dto, game, "id", "author", "category");
        game.setIdAuthor(dto.getAuthor().getId());
        game.setIdCategory(dto.getCategory().getId());

        this.gameRepository.save(game);
    }
}