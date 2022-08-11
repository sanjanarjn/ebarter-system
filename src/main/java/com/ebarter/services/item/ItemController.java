package com.ebarter.services.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> uploadItemForLending(@RequestBody ItemDto item) {
        return new ResponseEntity<>(itemService.onboardItem(item), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "bulk=true")
    public ResponseEntity<Boolean> uploadItemsForLending(@RequestBody List<ItemDto> items) {
       return new ResponseEntity<>(itemService.onboardItems(items), HttpStatus.OK);
    }
}
