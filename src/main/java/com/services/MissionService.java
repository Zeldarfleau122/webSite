package com.services;

import com.persistence.MissionRepository;

public class MissionService {
    private MissionRepository missionRepository;

    /**
     * Default constructor
     *
     * @param missionRepository Interface connection with persistence layer
     */
    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public boolean[] getMissionsStatus(String clientIP) {
        return missionRepository.getMissionsStatus(clientIP) ;
    }
}
