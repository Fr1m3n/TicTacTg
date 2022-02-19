package DataBase.Models.User;

import DataBase.Models.Game.GameRecord;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
@Data
@NoArgsConstructor
public class UserRecord {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private GameRecord currentGame;

    public UserRecord(Long id) {
        this.id = id;
    }

}
