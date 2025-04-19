package com.algosec.registration;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    // כאן ניתן להוסיף שיטות מותאמות אישית אם צריך
}
