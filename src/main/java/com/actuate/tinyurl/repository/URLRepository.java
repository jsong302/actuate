package com.actuate.tinyurl.repository;

import com.actuate.tinyurl.model.URL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository<URL, String> {

}