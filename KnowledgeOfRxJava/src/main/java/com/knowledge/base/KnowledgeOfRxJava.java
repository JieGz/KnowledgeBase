package com.knowledge.base;

import io.reactivex.rxjava3.core.Flowable;

/**
 * @author jieguangzhi
 * @date 2022-08-27
 */
public class KnowledgeOfRxJava {

    public static void main(String[] args) {
        Flowable.just("RxJava").subscribe(System.out::println);
    }
}
