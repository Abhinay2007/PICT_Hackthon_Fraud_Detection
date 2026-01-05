package com.pict.fraudDetect.service;

import com.pict.fraudDetect.model.UserStats;

public interface UserStatsService {
    UserStats calculateUserStats(String userId);
}
