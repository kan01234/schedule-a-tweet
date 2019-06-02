package com.dotterbear.schedule.a.tweet.db.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.dotterbear.schedule.a.tweet.db.model.UploadFile;
import com.dotterbear.schedule.a.tweet.db.repo.UploadFileRepository;

@Service
public class UploadFileService {

  private static final Logger log = LoggerFactory.getLogger(UploadFileService.class);

  @Autowired
  private UploadFileRepository uploadFileRepository;

  public UploadFile findById(String id) {
    log.debug("findById, id: {}", id);
    Optional<UploadFile> uploadFile = uploadFileRepository.findById(id);
    return uploadFile.orElse(null);
  }

  public List<UploadFile> findAll(String direction, String orderBy) {
    log.debug("findAll, direction: {}, orderby: {}", direction, orderBy);
    return uploadFileRepository.findAll(Sort.by(buildSortDirection(direction), orderBy));
  }

  public Page<UploadFile> findAll(int page, int size, String direction, String orderBy) {
    log.debug("findAll, page: {}, size: {}, direction: {}, orderby: {}", page, size, direction,
        orderBy);
    return uploadFileRepository
        .findAll(PageRequest.of(page - 1, size, Sort.by(buildSortDirection(direction), orderBy)));
  }

  public UploadFile save(UploadFile uploadFile) {
    log.debug("save, uploadFile: {}", uploadFile);
    Date now = new Date();
    if (uploadFile.getId() == null)
      uploadFile.setCreateTime(now);
    uploadFile.setAmendTIme(now);
    uploadFileRepository.save(uploadFile);
    return uploadFile;
  }

  private Direction buildSortDirection(String direction) {
    return direction.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
  }

}
