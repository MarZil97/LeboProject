package lt.codeacademy.lebo.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import lombok.AllArgsConstructor;
import lt.codeacademy.lebo.appuser.AppUser;
import lt.codeacademy.lebo.appuser.AppUserRole;
import lt.codeacademy.lebo.appuser.AppUserService;
import lt.codeacademy.lebo.appuser.userprofile.UserProfile;
import lt.codeacademy.lebo.appuser.userprofile.UserProfileService;
import lt.codeacademy.lebo.fakebankacc.DepositRequest;
import lt.codeacademy.lebo.fakebankacc.FakeBankAccountService;
import lt.codeacademy.lebo.image.ImageUtil;
import lt.codeacademy.lebo.image.ProfileImage;
import lt.codeacademy.lebo.image.ProfileImageService;
import lt.codeacademy.lebo.item.Item;
import lt.codeacademy.lebo.item.ItemService;
import lt.codeacademy.lebo.registration.RegistrationRequest;
import lt.codeacademy.lebo.registration.RegistrationService;

@Controller
@AllArgsConstructor
public class MainController {
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private ProfileImageService profileImageService;
	
	@Autowired
	private FakeBankAccountService bankAccountService;
	
	@Autowired
	private ItemService itemService;

	@GetMapping
	public String redirect() {
		return "redirect:/index";
	}
	
	
	@GetMapping("/index")
	public String indexPage() {
		return "index";
	}
	
	
	@GetMapping("/signup")
	public String showSignUpForm(Model model) {
		model.addAttribute("request", new RegistrationRequest());
		return "sign-up";
	}
	
	
	@PostMapping("/signuser")
	public String signUp(@ModelAttribute("request") RegistrationRequest request, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "/signup";
		}
		registrationService.register(request);
		return "redirect:/signup/successfull";
	}
	
	
	@GetMapping("/signup/successfull")
	public String signUpSuccessfull() {
		return "sign-up-successfull";
	}
	
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	
	@GetMapping("/filluserprofile/{id}")
	public String showFirstProfileEditPage(Model model, UserProfile userProfile, @AuthenticationPrincipal AppUser appUser) {
		appUser = (AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
		model.addAttribute("user", appUser);
		if(appUser.getAppUserRole().equals(AppUserRole.BUYER)) {
			return "redirect:/buyer/index";
		} else if(appUser.getAppUserRole().equals(AppUserRole.SELLER)) {
			return "redirect:/seller/index";
		} else {
		return "fill-profile";
		}
	}

	
	@PostMapping("/userprofile/{id}")
	public String fillProfile(@AuthenticationPrincipal AppUser appUser, UserProfile userProfile, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "fill-profile";
		}
		appUser = (AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
		appUser.setUserProfile(userProfile);
		userProfileService.changeUserRole(userProfile, appUser);
		bankAccountService.setBankAccountToUser(userProfile);
		userProfileService.saveUserProfileInformation(userProfile);
		if(userProfile.getChooseRole().equalsIgnoreCase("BUYER")) {
			return "redirect:/buyer/index";
		}
	return "redirect:/seller/index";
	}
	
	@GetMapping("/buyer/index")
	public String showBuyerIndexPage(@AuthenticationPrincipal AppUser appUser, Model model, UserProfile userProfile) {
		appUser = (AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
		userProfile = appUser.getUserProfile();
		model.addAttribute("fName", appUser.getFirstName() + " " + appUser.getLastName());
		model.addAttribute("userProfile", userProfile);
		
		return "buyer-index";
	}
	
	@GetMapping("/seller/index")
	public String showSellerIndexPage(@AuthenticationPrincipal AppUser appUser, Model model, UserProfile userProfile) {
		appUser=(AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
		userProfile = appUser.getUserProfile();
		model.addAttribute("fName", appUser.getFirstName() + " " + appUser.getLastName());
		model.addAttribute("userProfile", userProfile);
		
		return "seller-index";
	}
	
	@GetMapping("/uploadform/profileimage")
	public String showProfileImageUploadingForm(@AuthenticationPrincipal AppUser appUser, UserProfile userProfile, Model model) {
		appUser=(AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
		userProfile = appUser.getUserProfile();
		return "upload-profile-img";
	}
	
	@PostMapping("/upload")
	public String uploadProfileImage(@AuthenticationPrincipal AppUser appUser, UserProfile userProfile, Model model,@RequestParam("file") MultipartFile file) throws IOException{
		appUser=(AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
		userProfile = appUser.getUserProfile();
		model.addAttribute("file", file);
		ProfileImage pImage = new ProfileImage();
		pImage.setName(file.getOriginalFilename());
		pImage.setType(file.getContentType());
		pImage.setImageData(ImageUtil.compressImage(file.getBytes()));
		
		profileImageService.uploadImage(file, userProfile);
		
		
		return "redirect:/" + userProfile.getChooseRole().toLowerCase() + "/index";
	}
	
	 @GetMapping("/get/image/{id}")
	    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id, @AuthenticationPrincipal AppUser appUser, UserProfile userProfile) throws IOException {
	    	appUser = (AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
			userProfile = appUser.getUserProfile();
	        final Optional<ProfileImage> dbImage = profileImageService.findById(userProfile.getProfileImage().getId());

	        return ResponseEntity
	                .ok()
	                .contentType(MediaType.valueOf(dbImage.get().getType()))
	                .body(ImageUtil.decompressImage(dbImage.get().getImageData()));
	    }
	    
	    @GetMapping("/editProfile/{id}")
	    public String editProfile(@AuthenticationPrincipal AppUser appUser, Model model, @PathVariable("id")Long id) {
	    	appUser = (AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
	    	id = appUser.getUserProfile().getProfileID();
			UserProfile userProfile = userProfileService.findById(appUser.getUserProfile().getProfileID()).orElseThrow(() -> new IllegalArgumentException("Invalid user"));
			model.addAttribute("profile", userProfile);
			return "update-profile";
	    }
	    
	    @PostMapping("updateProfile/{id}")
	    public String updateProfile(@AuthenticationPrincipal AppUser appUser, UserProfile userProfile, Model model, BindingResult result, @PathVariable("id")Long id) {
	    	appUser = (AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
	    	UserProfile profile = appUser.getUserProfile();
	    	if(result.hasErrors()) {
				return "update-profile";
			}
	    	userProfile.setProfileImage(profile.getProfileImage());
	    	userProfile.setChooseRole(profile.getChooseRole());
	    	userProfile.setProfileID(id);
	    	userProfile.setBankAccount(profile.getBankAccount());
	    	userProfile.setWalletBallance(profile.getWalletBallance());
	    	userProfileService.saveUserProfileInformation(userProfile);
	    	
	    	return "redirect:/" + userProfile.getChooseRole().toLowerCase() + "/index";
	    }
	    
	    @GetMapping("/depositForm")
	    public String depositWallet(@AuthenticationPrincipal AppUser appUser, UserProfile userProfile, Model model) {
	    	appUser = (AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
	    	userProfile = appUser.getUserProfile();
	    	model.addAttribute("request", new DepositRequest());
	  
	    	return "deposit-form";
	    }
	    
	    @PostMapping("/deposit")
	    public String depositWalletPost(@AuthenticationPrincipal AppUser appUser, UserProfile userProfile, Model model, BindingResult result, @ModelAttribute("request")DepositRequest depositRequest){
	    	appUser = (AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
	    	userProfile = appUser.getUserProfile();
	    	bankAccountService.depositToOnlineWallet(userProfile, depositRequest.getDepositRequest());
	    	
	    	return "redirect:/" + userProfile.getChooseRole().toLowerCase() + "/index";
	    	
	    }
	    
	    @GetMapping("/items")
	    public String showItemsPage(@AuthenticationPrincipal AppUser appUser, UserProfile userProfile, Model model) {
	    	appUser = (AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
	    	userProfile = appUser.getUserProfile();
	    	model.addAttribute("items", itemService.findUserItems(userProfile));
	    	return "items";
	    }
	    
	    @GetMapping("/signitems")
	    public String showItemSignForm(@AuthenticationPrincipal AppUser appUser, UserProfile userProfile, Item item) {
	    	appUser = (AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
	    	userProfile = appUser.getUserProfile();
	    	return "add-item";
	    }
	    
	    @PostMapping("/additem")
	    public String addItem(@AuthenticationPrincipal AppUser appUser, UserProfile userProfile, Item item, Model model, BindingResult result) {
	    	appUser = (AppUser) appUserService.loadUserByUsername(userProfileService.getLoggedInUserUsername());
	    	userProfile = appUser.getUserProfile();
	    	if(result.hasErrors()) {
	    		return "add-item";
	    	}
	    	//List<Item> items = userProfile.getItems();
	    	//items.add(item);
	    	itemService.addUserItem(item, userProfile);
	    	itemService.saveItem(item);
	    	
	    	
	    	return "redirect:/" + userProfile.getChooseRole().toLowerCase() + "/index";
	    
	    }
}
