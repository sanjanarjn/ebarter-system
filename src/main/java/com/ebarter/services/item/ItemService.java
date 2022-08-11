package com.ebarter.services.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public boolean onboardItems(List<ItemDto> itemDtos) {
        List<Item> items = new ArrayList<>();
        for(ItemDto dto : itemDtos) {
            items.add(convertDtoToEntity(dto));
        }
        itemRepository.saveAll(items);
        return true;
    }

    private Item convertDtoToEntity(ItemDto dto) {
        Item item = modelMapper.map(dto, Item.class);
        return item;
    }

    public boolean onboardItem(ItemDto item) {
        itemRepository.save(convertDtoToEntity(item));
        return true;
    }
}
