package com.quascenta.petersroad.droidtag.main;

/**
 * Created by AKSHAY on 4/7/2017.
 */

public class Commands {


    /**
     * COMMAND_DEVICE_STATUS -> provides the status of the device
     * RESPONSE ->  Success -> Datalogger is idle
     * -> Datalogger is active , started at dd:mm:yyyy hh:mm:ss  time
     * Error -> ED -> Command Length is invalid
     * <p>
     * REQUEST BYTE LENGTH -> 1 byte
     */
    public static final byte COMMAND_DEVICE_STATUS = 0x53;


    /**
     * COMMAND_DEVICE_CONFIGURE -> Configure Device
     * RESPONSE ->  Success -> Device Configured.
     * <p>
     * Error -> 0xED -> Command Length is invalid
     * -> 0xE0 -> Can't reconfigure : Device is Locked.
     * -> 0xE3 ->Can't configure : Parameters Invalid.
     * <p>
     * REQUEST BYTE LENGTH -> 7 bytes
     * EXAMPLE -> 0x050064012CCD
     * <p>
     * CD – Command
     * 05 – 5 min interval in hex
     * 0064 – 10.0 x 10 TMIN in hex
     * 012C – 30.0 x 10 TMAX in hex
     */
    public static final byte COMMAND_DEVICE_CONFIGURE = (byte) 0xCD;


    /**
     * COMMAND_IF_DEVICE_CONFIGURED -> Device Configuration
     * RESPONSE ->  Success -> Device Configured.
     * Log Interval:LI min
     * Min Temp*10: TMIN C
     * Max Temp*10: TMAX C
     * <p>
     * Error -> ED -> Command Length is invalid
     * <p>
     * <p>
     * REQUEST BYTE LENGTH -> 1 byte
     */
    public static final byte COMMAND_IF_DEVICE_CONFIGURED = (byte) 0xDC;


    /**
     * CLEAR_LOG -> Clear Log
     * RESPONSE ->  Success -> The log and alarm memory is clear.
     * <p>
     * Error ->  0xED -> Command Length is invalid
     * : 0xE1 -> Can't Clear Memory :Datalog is Active.
     * <p>
     * <p>
     * REQUEST BYTE LENGTH -> 1 byte
     */
    public static final byte CLEAR_LOG = (byte) 0xEA;


    /**
     * COMMAND_DEVICE_CLEARCONFIGURATION -> Clear the last saved configuration
     * RESPONSE ->  Success -> The last saved configuration is clear.
     * <p>
     * Error -> ED -> Command Length is invalid
     * 0xE2 -> Can't Clear Config :Datalog is Active.
     * REQUEST BYTE LENGTH -> 1 byte
     */
    public static final byte COMMAND_DEVICE_CLEARCONFIGURATION = (byte) 0xCC;


    /**
     * COMMAND_DEVICE_STARTLOGGING -> Start DataLogging
     * RESPONSE ->  Success -> Datalog is Active
     * <p>
     * Error -> ED -> Command Length is invalid
     * 0xE4 -> Can't start : Datalog is in progress
     * 0xE5 -> Can't start : Device is not configured
     * 0xE6 -> Can't start : Date and Time is Invalid
     * <p>
     * <p>
     * Example ->         DD-MM-YYYY HH:MM    eg. 050620170512DB
     * DD - 1 byte  - Date
     * MM - 1 byte  - Month
     * YY - 2 bytes - Year
     * HH - 1 byte  - Hour
     * MM - 1 byte  - Minute
     * <p>
     * <p>
     * REQUEST BYTE LENGTH -> 7 bytes
     */
    public static final byte COMMAND_DEVICE_STARTLOGGING = (byte) 0xDB;


    /**
     * COMMAND_DEVICE_RESUMELOGGING -> Resumes data logging
     * RESPONSE ->  Success -> Datalog is resumed
     * <p>
     * Error -> ED -> Command Length is invalid
     * 0xE7 -> Can't resume : Datalog is Active
     * Can't resume : Datalog is Idle
     * 0xE8 -> Can't resume : Device is not configured
     * <p>
     * REQUEST BYTE LENGTH -> 1 byte
     */
    public static final byte COMMAND_DEVICE_RESUMELOGGING = (byte) 0x52;


    /**
     * COMMAND_DEVICE_STOPDATALOGGING -> Stops DataLogging
     * RESPONSE ->  Success -> Datalog is idle
     * -> Datalog is paused
     * Error -> ED -> Command Length is invalid
     * <p>
     * REQUEST BYTE LENGTH -> 1 byte
     */
    public static final byte COMMAND_DEVICE_STOPDATALOGGING = (byte) 0xDE;


    /**
     * COMMAND_DEVICE_TRANSFERALARM  ->transfer all the alarms generated during logging
     * RESPONSE ->  Success -> ALMnnnn DD-MM-YYYY HH:MM  xM TEMP:TTTT I
     * nnnn - 2 bytes - alarm message count in hex
     * DD   - 1 byte  - day (alarm) in hex
     * MM   - 1 byte  - month (alarm) in hex
     * YYYY - 2 bytes - year (alarm) in hex
     * HH   - 1 byte  - hour (alarm) in hex
     * MM   - 1 byte  - minute (alarm) in hex
     * x    - 1 byte  - A/P
     * TTTT  - 2 bytes - temperature value in hex
     * I     - 1 byte  - L/H (low/high) indication
     * <p>
     * Error -> ED -> Command Length is invalid
     * Error code : 0xE9	No Alarm Messages to send.
     * <p>
     * REQUEST BYTE LENGTH -> 1 byte
     */
    public static final byte COMMAND_DEVICE_TRANSFERALARM = (byte) 0x41;


    /**
     * COMMAND_DEVICE_TRANSFER_TEMPERATURE -> transfer all the datapoints generated during logging
     * RESPONSE ->  Success -> Datalog is idle
     * -> Datalog is active , started at x time
     * Error -> ED -> Command Length is invalid
     * Error code : 0xED	Error : Command length is Invalid.
     * Error code : 0xEB	Can't transfer data : Datalog is Active
     * Error code : 0xEC	No temperature data to send.
     * <p>
     * <p>
     * <p>
     * REQUEST BYTE LENGTH -> 1 byte
     */
    public static final byte COMMAND_DEVICE_TRANSFER_TEMPERATURE = (byte) 0x54;


}
