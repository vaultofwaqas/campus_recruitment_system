package com.waqkz.campusrecruitmentsystem.AccountInfoFlow;

/**
 * Created by Waqas on 2/26/2017.
 */

public interface ServiceListener<T> {

    public void success(T obj);
    public void error(ServiceError serviceError);
}