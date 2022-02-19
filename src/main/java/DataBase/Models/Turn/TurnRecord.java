package DataBase.Models.Turn;

import DataBase.Models.Game.GameRecord;
import DataBase.Models.User.UserRecord;
import TicTacToe.Turn;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "turn")
@Data
@NoArgsConstructor
public class TurnRecord {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private GameRecord game;

    @Enumerated(EnumType.STRING)
    @Column(name = "turn_type")
    private Turn turnType;

    @OneToOne
    private UserRecord user;

    @Column(name = "x", nullable = false)
    private Integer coordinateX;

    @Column(name = "y", nullable = false)
    private Integer coordinateY;

    public TurnRecord(GameRecord game, Integer x, Integer y, Turn turn) {
        this.game = game;
        this.coordinateX = x;
        this.coordinateY = y;
        this.turnType = turn;
    }

}
