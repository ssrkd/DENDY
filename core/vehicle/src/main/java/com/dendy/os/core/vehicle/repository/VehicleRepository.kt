package com.dendy.os.core.vehicle.repository

import com.dendy.os.core.vehicle.model.ClimateState
import com.dendy.os.core.vehicle.model.DriveMode
import com.dendy.os.core.vehicle.model.VehicleState
import kotlinx.coroutines.flow.StateFlow

interface VehicleRepository {
    val vehicleState: StateFlow<VehicleState>
    fun setClimateTemperature(driverTemp: Float, passengerTemp: Float)
    fun setFanSpeed(speed: Int)
    fun toggleAc()
    fun toggleRecirculation()
    fun setDriveMode(mode: DriveMode)
    fun setSeatHeating(isDriver: Boolean, level: Int)
    fun probeHardwareCapabilities()
}

