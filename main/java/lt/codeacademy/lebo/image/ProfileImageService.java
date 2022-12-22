package lt.codeacademy.lebo.image;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lt.codeacademy.lebo.appuser.userprofile.UserProfile;

@Service
public class ProfileImageService {

	@Autowired
	private ProfileImageRepository piRepository;
	
	public ProfileImage uploadImage(MultipartFile file, UserProfile userProfile) throws IOException{
		ProfileImage pImage = new ProfileImage();
		pImage.setName(file.getOriginalFilename());
		pImage.setType(file.getContentType());
		pImage.setImageData(ImageUtil.compressImage(file.getBytes()));
		
		userProfile.setProfileImage(pImage);
		return piRepository.save(pImage);
	}
	
	public byte[] openUploadedImage(String fileName) {
		
		Optional<ProfileImage> imageData = piRepository.findByName(fileName);
		
		return ImageUtil.decompressImage(imageData.get().getImageData());
		
	}
	
	public Optional<ProfileImage> findById(Long id){
		return piRepository.findById(id);
	}
}
