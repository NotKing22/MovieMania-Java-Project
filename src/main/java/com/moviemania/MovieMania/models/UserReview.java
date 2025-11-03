package com.moviemania.MovieMania.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
@Table(name = "USER_REVIEW")
public class UserReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;             // id único da review (PK)

    @Column(name = "USER_ID")
    private Integer userId;         // id do usuário (pode repetir)

    @Column(name = "NOTA")
    private int nota;

    @Column(name = "FILME_ID")
    private Long filmeId;
    
    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }

    public Long getFilmeId() { return filmeId; }
    public void setFilmeId(Long filmeId) { this.filmeId = filmeId; }
    
    private String posterPath;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}

/*INSERT INTO USER_REVIEW (USER_ID, NOTA, FILME_ID) VALUES
(1, 5, 3),
(1, 4, 1311032);*/