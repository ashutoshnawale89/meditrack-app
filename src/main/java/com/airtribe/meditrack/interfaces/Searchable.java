package com.airtribe.meditrack.interfaces;

import java.util.List;
import java.util.Optional;


public interface Searchable<T> {

    Optional<T> findById(long id);

    List<T> findByName(String name);
}


