package DataBase.Models.Game;

import DataBase.Models.Turn.TurnRecord;
import DataBase.Models.User.UserRecord;
import TicTacToe.GameState;
import Utils.ConfigurationLoader;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "game")
@Data
@NoArgsConstructor
public class GameRecord {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne
    private UserRecord firstPlayer;

    @OneToOne
    private UserRecord secondPlayer;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private GameState state = GameState.STARTING;

    @OneToOne
    private UserRecord winner;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TurnRecord> turns;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "invite_code")
    private String inviteCode;


    public GameRecord(UserRecord firstPlayer, GameState state) {
        this.firstPlayer = firstPlayer;
        this.state = state;
    }

    public UserRecord getAnotherPlayer(UserRecord user) {
        if(user == getFirstPlayer()) {
            return getSecondPlayer();
        } else {
            return getFirstPlayer();
        }
    }


}
