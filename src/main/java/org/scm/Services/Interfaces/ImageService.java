package org.scm.Services.Interfaces;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Component
public interface ImageService {

    String uploadImage( MultipartFile picture,String pictureId);

    String getUrlById(String  public_id);
}
