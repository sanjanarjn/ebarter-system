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
    public ResponseEntity<ItemDto> uploadItemForLending(@RequestBody ItemDto item) {
        return new ResponseEntity<>(itemService.onboardItem(item), HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "bulk=true")
    public ResponseEntity<List<ItemDto>> uploadItemsForLending(@RequestBody List<ItemDto> items) {
        return new ResponseEntity<>(itemService.onboardItems(items), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDto>> getAllAvailableItems() {
        return new ResponseEntity<>(itemService.getItems(), HttpStatus.OK);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, params = {"ownerId"})
    public ResponseEntity<List<ItemDto>> getAllAvailableItems(@RequestParam("ownerId") long ownerId) {
        return new ResponseEntity<>(itemService.getItems(), HttpStatus.OK);
    }
}
