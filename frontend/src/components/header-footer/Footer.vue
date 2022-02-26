<template>
  <div id="footer">
    <div class="logo">
      Vibid
    </div>
    <div class="inner">
      <div class="left caution">
        <p class="caution-title">
          CAUTION
        </p>
        <ul class="caution-list">
          <li>
            <div class="dot"></div>
            거래 진행 시 상대방과의 모든 대화, 영상을 촬영해두세요. <br />
            <span>비비드에서는 사용자의 개인정보를 저장하지 않습니다.</span>
          </li>
          <li>
            <div class="dot"></div>
            가급적 상품을 수령한 뒤에 대금을 송금해주세요.
          </li>
          <li>
            <div class="dot"></div>
            사기가 의심되는 경우, 신속하게 지역 경찰서에 민원처리를 해주세요<br />
            <span>지구대 혹은 파출소가 아닌 경찰서로 가셔야 합니다.</span><br />
            <span>민원처리시 필요한 증거들은 신고자가 직접 준비하여 제출해야 합니다.</span><br />
          </li>
          <div class="dot"></div>
          <li>
            <div class="dot"></div>
            가급적 개인주소지 이용을 피해주세요.
          </li>
        </ul>
      </div>
      <div class="right member">
        <p class="member-title">
          MEMBER
        </p>
        <ul class="member-list">
          <li><span>이동호</span><span>edh121@gmail.com</span></li>
          <li><span>김경동</span><span>rlarudehd32@gmail.com</span></li>
          <li><span>이주현</span><span>agange7427@gmail.com</span></li>
          <li><span>이제훈</span><span>jerry7041256@gmail.com</span></li>
          <li><span>윤원정</span><span>111_96@naver.com</span></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
#footer {
  width: 100vw;
  height: 400px;
  color: #ffffff;
  background-color: $main-color1;
  .logo {
    width: 100vw;
    text-align: center;
    font-family: "Sacramento", cursive;
    font-size: 60px;
    color: $main-color4;
    margin-bottom: 10px;
  }
  .inner {
    display: flex;
    justify-content: space-around;
    width: 1050px;
    margin: 0 auto;
    @media #{$tablet} {
      width: 100vw;
    }
    .caution {
      width: 60%;
      font-size: 16px;
      margin-left: 20px;
      .caution-title {
        font-weight: 500;
        font-size: 32px;
        margin-bottom: 20px;
      }
      .caution-list {
        margin-left: 20px;
        li {
          position: relative;
          .dot {
            position: absolute;
            width: 5px;
            height: 5px;
            top: 8px;
            left: -10px;
            border-radius: 50%;
            background-color: white;
          }
          margin-bottom: 10px;
          span {
            margin-left: 20px;
          }
        }
      }
    }
    .member {
      width: 40%;
      .member-title {
        font-weight: 500;
        font-size: 32px;
        margin-bottom: 20px;
      }
      .member-list {
        margin-left: 20px;
        font-size: 16px;
        span {
          margin-right: 10px;
        }
      }
    }
  }
  @media #{$tablet} {
    width: 100vw;
  }
}
</style>
