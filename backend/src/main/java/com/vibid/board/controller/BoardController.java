package com.vibid.board.controller;

import com.vibid.api.ApiResult;
import com.vibid.auth.jwt.domain.JwtAuthentication;
import com.vibid.board.controller.dto.*;
import com.vibid.board.domain.Board;
import com.vibid.board.service.BoardService;
import com.vibid.exception.NotFoundException;
import com.vibid.member.domain.Member;
import com.vibid.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;
  private final MemberService memberService;

  @PostMapping  //	판매글 작성
  public ApiResult<Long> write(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @RequestBody PostingRequestDto postingRequestDto) {
    return ApiResult.OK(boardService.write(authentication.getId(), postingRequestDto).getId());
  }

  @GetMapping("/{id}") // 판매글 id로 상세 조회
  public ApiResult<PostDto> post(@AuthenticationPrincipal JwtAuthentication authentication,
                                 @PathVariable Long id) {
    return ApiResult.OK(boardService.findById(id)
      .map(board -> PostDto.from(board))
      .orElseThrow(() -> new NotFoundException(Board.class, id)));
  }

  @GetMapping("/main")
  public ApiResult<Map<String, List<PostPreviewDto>>> main() {
    Map<String, List<PostPreviewDto>> results = new HashMap<>();
    // VIBID's PICK은 그냥 최신순으로
    results.put("pick",
      boardService.findAllByCondition(new ArrayList<>(), false, SortType.DATE, OrderType.DESC, 1, 12));

    // 인기순 정렬
    results.put("popularity",
      boardService.findAllByCondition(new ArrayList<>(), false, SortType.WISH, OrderType.DESC, 1, 12));

    // 라이브 시청자 순
    results.put("live",
      boardService.findAllByPopularityLive(OrderType.DESC, 12));

    return ApiResult.OK(results);
  }

  @GetMapping("/goods")
  public ApiResult<List<PostPreviewDto>> goods(@AuthenticationPrincipal JwtAuthentication authentication,
                                               @RequestParam(required = false) List<String> tags,
                                               @RequestParam(required = false, defaultValue = "false") boolean live,
                                               @RequestParam(required = false, defaultValue = "DATE") SortType sort,
                                               @RequestParam(required = false, defaultValue = "DESC") OrderType order,
                                               @RequestParam(required = false, defaultValue = "1") int page) {
    if (tags == null)
      tags = new ArrayList<>();
    return ApiResult.OK(boardService.findAllByCondition(tags, live, sort, order, page, 15));
  }

  @PutMapping("/{id}") // 해당 id 판매글 수정
  public ApiResult<Boolean> boardUpdate(@AuthenticationPrincipal JwtAuthentication authentication,
                                        @PathVariable Long id,
                                        @RequestBody PostingRequestDto postingRequestDto) {

    return ApiResult.OK(boardService.update(authentication.getId(), id, postingRequestDto));  // 완료후 이동위치 미정
  }

  @PatchMapping("/isLiftUp/{id}") // 해당 id 판매글 끌올
  public ApiResult<Boolean> boardIsLiftUp(@AuthenticationPrincipal JwtAuthentication authentication,
                                          @PathVariable Long id) {

    return ApiResult.OK(boardService.liftUp(authentication.getId(), id));  // 완료후 이동위치 미정
  }

  @DeleteMapping("/{id}") // 해당 id 판매글 삭제
  public ApiResult<Boolean> boardDelete(@AuthenticationPrincipal JwtAuthentication authentication,
                                        @PathVariable Long id) {
    return ApiResult.OK(boardService.delete(authentication.getId(), id));  // 완료후 이동위치 미정
  }

  @PatchMapping("/wish/{id}")
  public ApiResult<WishDto> like(@AuthenticationPrincipal JwtAuthentication authentication,
                                 @PathVariable Long id) {

    return ApiResult.OK(memberService.findById(authentication.getId())
        .map(member -> boardService.wish(member, id))
        .map(result -> new WishDto(result))
      .orElseThrow(() -> new NotFoundException(Member.class, authentication.getId())));

  }

}
