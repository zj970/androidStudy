package com.example.remote.api;

import com.example.remote.protocol.EProtocol;

public interface IOnClickItemCallback {

    /**
     * item点击
     * @param position 点击位置
     */
    void onItemClick(int position);
}