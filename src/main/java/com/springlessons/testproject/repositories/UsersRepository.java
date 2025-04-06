package com.springlessons.testproject.repositories;

import com.springlessons.testproject.model.Booking;
import com.springlessons.testproject.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}
