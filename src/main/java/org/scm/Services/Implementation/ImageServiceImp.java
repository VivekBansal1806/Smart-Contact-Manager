package org.scm.Services.Implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.scm.Services.Interfaces.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImp implements ImageService {

    private final Cloudinary cloudinary;
    private final Logger logger= LoggerFactory.getLogger(ImageServiceImp.class);

    public ImageServiceImp(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile picture,String pictureId) {

        try {
            byte[] data = new byte[picture.getInputStream().available()];
            picture.getInputStream().read(data);
            cloudinary.uploader().upload(data,ObjectUtils.asMap(
                    "public_id", pictureId
            ));
        } catch (IOException e) {
            logger.error("Image upload failed: {}", e.getMessage());
            return null;
        }
        return this.getUrlById(pictureId);
    }

    @Override
    public  String getUrlById(String public_id) {
        return cloudinary.url().generate(public_id);
    }
}
