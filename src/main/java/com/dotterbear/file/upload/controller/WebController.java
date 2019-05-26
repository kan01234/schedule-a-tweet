package com.dotterbear.file.upload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

  @RequestMapping(value = "/fb-login", method = RequestMethod.GET)
  public String fbLogin() {
    return "fb-login";
  }
}
