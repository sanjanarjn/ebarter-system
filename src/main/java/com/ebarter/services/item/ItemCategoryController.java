package com.ebarter.services.item;

import com.ebarter.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item-category")
public class ItemCategoryController {

    @Autowired
    private ItemCategoryService categoryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemCategoryDto> createItemCategory(@RequestBody ItemCategoryDto itemCategoryDto) throws ServiceException {
        return new ResponseEntity<>(categoryService.createItemCategory(itemCategoryDto), HttpStatus.CREATED);
    }
}
