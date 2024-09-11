package com.drjava.journal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import com.drjava.journal.entity.JournalEntry;
import com.drjava.journal.entity.User;
import com.drjava.journal.service.JournalEntryService;
import com.drjava.journal.service.UserService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
     public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<?> createJournal(@RequestBody JournalEntry entry, @PathVariable String userName) {
        try {
            journalEntryService.saveEntry(entry, userName);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("id/{entityId}")
    public ResponseEntity<?> getJournalEntryByID(@PathVariable ObjectId entityId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(entityId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{entityId}")
    public ResponseEntity<?> deleteJournalEntryByID(@PathVariable ObjectId entityId, @PathVariable String userName) {
        JournalEntry old = journalEntryService.findById(entityId).orElse(null);
        if (old != null) {
            try {
                journalEntryService.deleteById(entityId, userName);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{userName}/{entityId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId entityId, @PathVariable String userName, @RequestBody JournalEntry entry) {
        JournalEntry old = journalEntryService.findById(entityId).orElse(null);
        if (old != null) {
            old.setTitle(entry.getTitle() != null && !entry.getTitle().isEmpty() ? entry.getTitle() : old.getTitle());
            old.setContent(entry.getContent() != null && !entry.getContent().isEmpty() ? entry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
