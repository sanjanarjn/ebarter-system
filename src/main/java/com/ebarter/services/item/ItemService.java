package com.ebarter.services.item;

import com.ebarter.services.exceptions.ExceptionMessages;
import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public List<ItemDto> onboardItems(User user, List<ItemDto> itemDtos) {
        List<Item> items = new ArrayList<>();
        for(ItemDto dto : itemDtos) {
            dto.setOwnerId(user.getId());
            items.add(convertDtoToEntity(dto));
        }
        items = saveItems(items);

        List<ItemDto> savedDtos = new ArrayList<>();
        for (Item item : items) {
            savedDtos.add(modelMapper.map(item, ItemDto.class));
        }
        return savedDtos;
    }

    public List<Item> saveItems(List<Item> items) {
        return itemRepository.saveAll(items);
    }

    public void updateItemStatus(List<Long> itemIds, ItemAvailabilityStatus newStatus) {
        List<Item> items = itemRepository.findAllById(itemIds);
        for(Item item : items) {
            item.setAvailabilityStatus(newStatus);
        }
        itemRepository.saveAll(items);
    }

    private Item convertDtoToEntity(ItemDto dto) {
        Item item = modelMapper.map(dto, Item.class);
        return item;
    }

    public ItemDto onboardItem(User user, ItemDto itemDto) {
        itemDto.setOwnerId(user.getId());
        Item item = itemRepository.save(convertDtoToEntity(itemDto));
        return modelMapper.map(item, ItemDto.class);
    }

    public List<ItemDto> getItems() {
        Page<Item> itemPage = itemRepository.findByAvailabilityStatus(ItemAvailabilityStatus.AVAILABLE,
                PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "modifiedTime")));
        List<ItemDto> itemDtos = new ArrayList<>();
        for(Item item : itemPage.getContent()) {
            itemDtos.add(modelMapper.map(item, ItemDto.class));
        }
        return itemDtos;
    }

    public List<ItemDto> getItems(long categoryId, long ownerId) {
        Page<Item> itemPage = itemRepository.findByCategoryIdAndOwnerIdAndAvailabilityStatus(
                categoryId, ownerId, ItemAvailabilityStatus.AVAILABLE,
                PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "modifiedTime")));
        List<ItemDto> itemDtos = new ArrayList<>();
        for(Item item : itemPage.getContent()) {
            itemDtos.add(modelMapper.map(item, ItemDto.class));
        }
        return itemDtos;
    }

    public Item getItem(long id) throws ServiceException {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if(itemOptional.isPresent())
            return itemOptional.get();
        throw new ServiceException(MessageFormat.format(ExceptionMessages.ENTITY_ID_NOT_FOUND, id));
    }
}
