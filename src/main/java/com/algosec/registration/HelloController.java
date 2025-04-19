package com.algosec.registration;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class HelloController {

    // קבלת בקשה GET עבור דף הבית
    @GetMapping("/")
    public String hello() {
        return "שלום, האפליקציה שלך פועלת!";
    }

    // קבלת בקשה POST להרשמה
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // לוגיקה של הרשמה (למשל הוספה למסד נתונים)
        return "המשתמש נרשם בהצלחה!";
    }
}
