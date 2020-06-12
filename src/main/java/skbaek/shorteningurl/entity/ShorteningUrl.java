package skbaek.shorteningurl.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Getter @Setter
@Table
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShorteningUrl implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(unique = true, columnDefinition = "not null")
    private String longUrl;

    @Column(unique = true)
    private String shortUrl;

    @ColumnDefault("0")
    private int callCnt;

}

