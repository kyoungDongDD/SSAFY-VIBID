package com.vibid.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Hashtag {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tag_id")
  private Long id;

  @Column(unique = true)
  private String name;

  @OneToMany(cascade = CascadeType.ALL,mappedBy = "hashtag")
  private List<BoardHashtag> board = new ArrayList<BoardHashtag>();

  protected Hashtag() {
  }
}
