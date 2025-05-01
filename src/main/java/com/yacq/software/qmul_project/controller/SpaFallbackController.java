package com.yacq.software.qmul_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaFallbackController {

  /**
   * Forward any unmapped paths (apart from API routes) to index.html
   * so that client‐side routing can take over.
   */
  @RequestMapping({
    "/", 
    "/restaurants/**", 
    "/tables/**", 
    "/customers/**", 
    "/reservations/**"
  })
  public String forwardToSpa() {
    // index.html will be served from src/main/resources/static/index.html
    return "forward:/index.html";
  }
}
