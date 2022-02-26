package com.vibid.chat.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ChatService {

  String filtering(String context) throws IOException;

}
