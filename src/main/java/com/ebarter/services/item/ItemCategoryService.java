package com.ebarter.services.item;

import com.ebarter.services.exceptions.ExceptionMessages;
import com.ebarter.services.exceptions.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCategoryService {

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ItemCategoryDto createItemCategory(ItemCategoryDto itemCategoryDto) throws ServiceException {

        if(itemCategoryRepository.existsByName(itemCategoryDto.getName()))
            throw new ServiceException(ExceptionMessages.CATEGORY_WITH_SAME_NAME_EXISTS);

        ItemCategory category = itemCategoryRepository.save(modelMapper.map(itemCategoryDto, ItemCategory.class));
        return modelMapper.map(category, ItemCategoryDto.class);
    }
}
