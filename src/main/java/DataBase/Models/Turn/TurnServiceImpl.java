package DataBase.Models.Turn;

import DataBase.Models.Game.GameRecord;
import DataBase.Models.Game.GameService;
import DataBase.Models.Game.GameServiceImpl;
import DataBase.Models.User.UserDAO;
import DataBase.Models.User.UserDAOImpl;
import TicTacToe.Field;
import TicTacToe.Turn;

import java.util.List;

public class TurnServiceImpl implements TurnService {
    private TurnDAO turnDAO = new TurnDAOImpl();
    private UserDAO userDAO = new UserDAOImpl();
    private GameService gameService = new GameServiceImpl();

    @Override
    public Field getFieldForUserId(Long userId) {
        GameRecord game = userDAO.getById(userId).getCurrentGame();
        Field field = new Field();
        if (game == null) {
            return field;
        }
        List<TurnRecord> turns = turnDAO.getByGame(game);

        for (TurnRecord turn : turns) {
            field.setCell(
                    turn.getCoordinateY(),
                    turn.getCoordinateX(),
                    turn.getTurnType()
            );
        }

        return field;
    }

    @Override
    public Field doTurn(Long userId, int i, int j) {
        GameRecord gameRecord = gameService.getCurrentGameForUserId(userId);
        Field field = getFieldForUserId(userId);

        Turn turn = field.doTurn(i, j);
        if (turn == null) {
            return null;
        }
        TurnRecord turnRecord = new TurnRecord(gameRecord, j, i, turn);
        turnRecord.setUser(userDAO.getById(userId));
        turnDAO.save(turnRecord);

        return field;
    }
}
