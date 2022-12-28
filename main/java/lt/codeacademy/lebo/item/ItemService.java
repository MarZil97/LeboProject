package lt.codeacademy.lebo.item;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.codeacademy.lebo.appuser.userprofile.UserProfile;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;

	public void saveItem(Item item) {
		itemRepository.save(item);
	}

	public Optional<Item> findItemById(Long id) {
		return itemRepository.findById(id);
	}

	
	public void addUserItem(Item item, UserProfile userProfile) {
		item.setUserProfile(userProfile);
	}
	
	public List<Item> findUserItems(UserProfile userProfile){
		List<Item> items = itemRepository.findAllByUserProfile(userProfile);
		
		return items;
	}

}
