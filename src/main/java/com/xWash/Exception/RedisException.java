package com.xWash.Exception;

public class RedisException extends Exception{
    public RedisException(){
        super("RedisException");
    }
    public RedisException(String msg){
        super(msg);
    }

}
