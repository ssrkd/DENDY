package com.dendy.os.core.vehicle.model

data class TirePressure(
    val pressureBar: Float,
    val temperatureCelsius: Int,
    val isAlert: Boolean = false
)

enum class DriveMode {
    ECO, NORMAL, SPORT
}

enum class AWDMode {
    AUTO, AWD_LOCK
}

enum class GearPosition {
    P, R, N, D
}

data class ClimateState(
    val isPowerOn: Boolean = true,
    val driverTempCelsius: Float = 22.0f,
    val passengerTempCelsius: Float = 22.0f,
    val isAuto: Boolean = true,
    val isAcOn: Boolean = true,
    val fanSpeed: Int = 3, // 1 to 7
    val isRecirculationOn: Boolean = false,
    val isFrontDefrostOn: Boolean = false,
    val isRearDefrostOn: Boolean = false,
    val driverSeatHeatingLevel: Int = 1, // 0, 1, 2, 3
    val passengerSeatHeatingLevel: Int = 0
)

data class HardwareEnvironment(
    val androidVersion: String,
    val apiLevel: Int,
    val cpuArchitecture: String,
    val displayResolution: String,
    val displayDensityDpi: Int,
    val deviceModel: String,
    val isOmodaHardwareDetected: Boolean,
    val vendorBridgeType: String // e.g. "Simulated / Emulator", "Vendor HAL / Service", "OBD-II Dongle"
)

data class VehicleState(
    val isConnected: Boolean = true,
    val vehicleModel: String = "Omoda C5 1.6T AWD (2023)",
    val speedKmh: Int = 42,
    val engineRpm: Int = 1750,
    val gear: GearPosition = GearPosition.D,
    val driveMode: DriveMode = DriveMode.NORMAL,
    val awdMode: AWDMode = AWDMode.AUTO,
    val fuelLevelPercent: Int = 78,
    val rangeRemainingKm: Int = 460,
    val batteryVoltage: Float = 14.2f,
    val outsideTempCelsius: Int = 18,
    val odometerKm: Int = 14250,

    // Tire Pressure Monitoring System (TPMS)
    val frontLeftTire: TirePressure = TirePressure(2.3f, 22),
    val frontRightTire: TirePressure = TirePressure(2.3f, 23),
    val rearLeftTire: TirePressure = TirePressure(2.2f, 21),
    val rearRightTire: TirePressure = TirePressure(2.2f, 21),

    // Doors & Latches
    val isDriverDoorOpen: Boolean = false,
    val isPassengerDoorOpen: Boolean = false,
    val isTrunkOpen: Boolean = false,
    val isHoodOpen: Boolean = false,

    // Climate
    val climate: ClimateState = ClimateState(),

    // Hardware Telemetry
    val hardwareInfo: HardwareEnvironment = HardwareEnvironment(
        androidVersion = "Android 9 / 10 (Pie/Q)",
        apiLevel = 28,
        cpuArchitecture = "ARM64-v8a (MediaTek MT8666 / UIS7862)",
        displayResolution = "1920x720",
        displayDensityDpi = 210,
        deviceModel = "Chery / Omoda T19C Head Unit",
        isOmodaHardwareDetected = false,
        vendorBridgeType = "Simulated Safe Telemetry"
    )
)
