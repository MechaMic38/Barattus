package com.mechamic38.barattus.persistence.user;

import com.mechamic38.barattus.persistence.common.IDataSource;

import java.util.List;

/**
 * User data source base interface.
 */
public interface IUserDataSource extends IDataSource<UserDTO> {

    List<UserDTO> getAll();
}
