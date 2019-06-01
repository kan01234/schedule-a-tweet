package com.dotterbear.file.upload.bus.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

@Service
public class VisionService {

  private static final Logger log = LoggerFactory.getLogger(VisionService.class);

  public List<EntityAnnotation> getImageLabels(ByteString byteString) throws Exception {
    log.debug("getImageLabels, file: {}", byteString);
    if (byteString == null || byteString.isEmpty())
      return Collections.emptyList();
    try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
      List<AnnotateImageRequest> requests = new ArrayList<>();
      Image img = Image.newBuilder().setContent(byteString).build();
      Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
      AnnotateImageRequest request =
          AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
      requests.add(request);
      BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();
      AnnotateImageResponse res = responses.get(0);
      if (res.hasError()) {
        log.error("vision api response error, error: {}", res.getError().getMessage());
        throw new Exception("vision api return error");
      }
      return res.getLabelAnnotationsList();
    } catch (IOException e) {
      log.error("fail to getImageLabels", e);
    }
    return Collections.emptyList();
  }

}
