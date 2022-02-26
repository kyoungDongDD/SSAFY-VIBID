package com.vibid.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vibid.bid.domain.Trade;
import com.vibid.board.controller.dto.PostingRequestDto;
import com.vibid.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@JsonIgnore
	private Member member;

	private String thumbnailImageUrl;

	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	@JsonIgnore
	private List<ContentImage> ContentImageUrl = new ArrayList<>();

	private String title;

	private String content;

	@Builder.Default
	private Integer viewCount = 0;

	@Builder.Default
	private Integer wishCount = 0;

	@Builder.Default
	private LocalDateTime registDate = LocalDateTime.now();

	@Builder.Default
	private Boolean isLiftUp = false;

	@Builder.Default
	private Boolean isLive = false;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "bid_info_id")
	@JsonIgnore
	private BidInfo bidInfo;

	// 연관관계 메서드
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	@JsonIgnore
	private List<BoardHashtag> boardHashtags = new ArrayList<>();

	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private Set<Wish> wishes = new HashSet<>();

	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@Builder.Default
	private List<Trade> trades = new ArrayList<>();

	public void setMember(Member member) {
		this.member = member;
		member.getBoards().add(this);
	}

	public void setBidInfo(BidInfo bidInfo) {
		this.bidInfo = bidInfo;
		this.bidInfo.setBoard(this);
	}

	// 비즈니스 메서드
	public Board update(PostingRequestDto requestDto, List<Hashtag> hashtags) {
		this.title = requestDto.getTitle();
		this.content = requestDto.getContent();
		this.thumbnailImageUrl = requestDto.getThumbnailImageUrl();

		requestDto.getContentImageUrl().forEach(
			img -> {
				ContentImage contentImage = ContentImage.from(img);
				contentImage.setBoard(this);
			}
		);

		this.bidInfo.update(requestDto.getStartingPrice(), requestDto.getBidding(), requestDto.getBidStartTime());

		boardHashtags.clear();
		hashtags.forEach(
			hashtag -> {
				BoardHashtag boardHashtag = BoardHashtag.builder()
					.board(this)
					.hashtag(hashtag)
					.build();
				boardHashtags.add(boardHashtag);
			}
		);
		return this;
	}

	public void lifeUp(){
		this.registDate=LocalDateTime.now();
		this.isLiftUp = true;
	}

	public void liveOn(){
		this.isLive= true;
	}

	public void liveOff(){
		this.isLive= false;
	}

	public void wishOn() {
		this.wishCount++;
	}

	public void wishOff() {
		this.wishCount--;
	}

	public void addViewCount() {
		this.viewCount++;
	}

	public boolean isAuthor(Long id) {
		return this.member.getId() == id;
	}

	public List<String> getHashtags() {
		return boardHashtags.stream()
			.map(boardHashtag -> boardHashtag.getHashtag().getName())
			.collect(Collectors.toList());
	}

	public Integer getWishCount() {
		return wishes.size();
	}

	public boolean isWish() {
		return wishes.contains(Wish.from(member,this));
	}
}



