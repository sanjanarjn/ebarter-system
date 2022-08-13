package com.ebarter.services.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public List<ItemDto> onboardItems(List<ItemDto> itemDtos) {
        List<Item> items = new ArrayList<>();
        for(ItemDto dto : itemDtos) {
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

    public ItemDto onboardItem(ItemDto itemDto) {
        Item item = itemRepository.save(convertDtoToEntity(itemDto));
        return modelMapper.map(item, ItemDto.class);
    }

    public List<ItemDto> getItems() {
        Page<Item> itemPage = itemRepository.findByAvailabilityStatus(ItemAvailabilityStatus.AVAILABLE, PageRequest.of(0, 100));
        List<ItemDto> itemDtos = new ArrayList<>();
        for(Item item : itemPage.getContent()) {
            itemDtos.add(modelMapper.map(item, ItemDto.class));
        }
        return itemDtos;
    }
}
