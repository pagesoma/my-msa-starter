package com.starter.adapters.commandhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class HTTPCommandHandler {

  protected static final String STATUS_CODE = "statusCode";
  protected static final String HEADER = "header";
  protected static final String BODY = "body";

}
