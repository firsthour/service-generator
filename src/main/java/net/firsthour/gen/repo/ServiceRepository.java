package net.firsthour.gen.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.firsthour.gen.model.Service;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {}
