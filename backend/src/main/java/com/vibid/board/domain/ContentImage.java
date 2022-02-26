package com.vibid.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ContentImage {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "content_image_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  private String imageUrl;

  protected ContentImage (String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public static ContentImage from(String filePath) {
    return new ContentImage(filePath);
  }

  public void setBoard(Board board) {
    this.board = board;
    this.board.getContentImageUrl().add(this);
  }
}
