package com.dotterbear.file.upload.controller;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dotterbear.file.upload.bus.service.TweetService;
import com.dotterbear.file.upload.exception.StorageFileNotFoundException;

@Controller
public class TweetController {

  @GetMapping("/images/{uploadFileId}")
  @ResponseBody
  public ResponseEntity<Resource> getUploadImage(@PathVariable String uploadFileId) {
    Resource file = tweetService.getUploadImage(uploadFileId);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @Autowired
  private TweetService tweetService;

   @GetMapping("/")
   public String listUploadedFiles(Model model) {
//   model.addAttribute("files", uploadFileService.findAll("desc", "amendTime"));
     model.addAttribute("scheduledtweets", tweetService.findScheduledTweets());
     model.addAttribute("postedTweets", tweetService.findPostedTweets());
   return "index";
   }

//   @RequestMapping(value = "/scheduled-tweet", method = RequestMethod.GET)
//   public String listScheduledTweet(Model model) {
//     model.addAttribute("tweets", tweetService.findScheduledTweet());
//     return "scheduledTweet";
//   }

  @PostMapping("/add-tweet")
  public String addTweet(@RequestParam("tweetText") String tweetText,
      @RequestParam("tweetFile") MultipartFile tweetFile,
      @RequestParam("tweetDatetime") String tweetDatetime, RedirectAttributes redirectAttributes) throws ParseException {
    tweetService.addTweet(tweetText, tweetFile, tweetDatetime);
//    redirectAttributes.addFlashAttribute("message",
//        "You successfully uploaded " + tweetFile.getOriginalFilename() + "!");
    return "redirect:/";
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }

}
