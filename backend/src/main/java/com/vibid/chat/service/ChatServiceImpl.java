package com.vibid.chat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
  @Value("${vibid.badWordPath}")
  private String badWordPath;
  @Override
  public String filtering(String context) throws IOException {

    //---------------------이부분 서버상에 올리고 검출하는 로직 구상하여 속도 개선을 해야합니다.-------------------
    List<String> fwords = new ArrayList<String>();
    File file = new File(".");
    BufferedReader bf = new BufferedReader(new FileReader(badWordPath)); //  욕설 파일 받아오기
    String saveWord;
    while ((saveWord = bf.readLine()) != null) {
      fwords.add(saveWord);  //욕설 정보 리스트에 저장
    }
    //--------------------------------------------------------------------------------------------------

    // 수정 후 :단어마다 for 문을 돌리지 않고 문장단위 indexof 사용하여 속도를 개선함. 속도 개선!!

    for(String fword:fwords) {
      while (true){
        int k=context.indexOf(fword);
        if(k==-1)  break;
        int fwordFirstPointer  = 0;              //욕 첫글자 포인터 이니셜 값
        int fwordLastPointer = context.length(); //욕 마지막 글자 포인터 이니셜 값

        char[] wordDivide =context.toCharArray();
        for(int i=k;i>0;i--){
          if(wordDivide[i]==' ') {
            fwordFirstPointer=i;                 //욕 앞글자가 공백인 포인트 저장
            break;
          }
        }
        for(int i = k; i< wordDivide.length; i++){
          if(wordDivide[i]==' ') {
            fwordLastPointer=i;                  //욕 뒷글자가 공백인 포인트 저장
            break;
          }
        }
        //공백이 없을시
        StringBuilder sb= new StringBuilder();
        if(fwordFirstPointer==0){
          sb.append("사랑해");
          sb.append(context.substring(fwordLastPointer,context.length()));
        }
        else{
          sb.append(context.substring(0,fwordFirstPointer+1));
          sb.append("사랑해");
          sb.append(context.substring(fwordLastPointer,context.length()));
        }
        context=sb.toString();
      }
    }
    return context;
  }
}
