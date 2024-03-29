package com.ui.automation.framework.android;

import com.ui.automation.framework.helpers.OSHelper;
import com.ui.automation.framework.helpers.ProcessHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * The type Adb.
 */
@Slf4j
public class Emulator {
    /**
     * The constant logcatPath.
     */
    public static String logcatPath = System.getProperty("java.io.tmpdir") + (OSHelper.isUnix() ? File.separator : "") + "logcat.txt";
    /**
     * The constant allLogcatPath.
     */
    public static String allLogcatPath = System.getProperty("java.io.tmpdir") + (OSHelper.isUnix() ? File.separator : "") + "AllLogcat.txt";
    private String emulatorProcessName;
    private String avdName;
    private String sdkTarget;
    private String cmdShell;

    /**
     * Instantiates a new Adb.
     *
     * @param avdName   the avd name
     * @param sdkTarget the sdk target
     */
    public Emulator(String avdName, String sdkTarget) {
        this.avdName = avdName;
        this.sdkTarget = sdkTarget;
        if (OSHelper.isWindows()) {
            this.cmdShell = "cmd.exe /C ";
            this.emulatorProcessName = "emulator-arm.exe";
        } else if (OSHelper.isUnix()) {
            this.cmdShell = " ";
            this.emulatorProcessName = "emulator-arm";
        } else {
            this.cmdShell = " ";
            this.emulatorProcessName = "emulator64-arm";
        }
    }

    /**
     * Instantiates a new Adb.
     *
     * @param avdName the avd name
     */
    public Emulator(String avdName) {
        this(avdName, null);
    }


    /**
     * Instantiates a new Adb.
     */
    public Emulator() {
        this(null, null);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args)
            throws Exception {
        Emulator emulatorHelper = new Emulator("test", "android-22");
        emulatorHelper.closeEmulator();
        emulatorHelper.deleteEmulator();
        emulatorHelper.createEmulator();
        emulatorHelper.startEmulator();
        emulatorHelper.closeEmulator();

        Emulator emulatorHelper2 = new Emulator("test");
        emulatorHelper2.restartEmulator();
    }

    /**
     * Restart adb.
     *
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    public void restartAdb()
            throws IOException, InterruptedException, ExecutionException {
        for (int i = 0; i < 3; i++) {
            log.info("Beginning Restart adb");
            String cmd = this.cmdShell + "adb start-server";
            log.info("cmd==" + cmd);

            Process process = Runtime.getRuntime().exec(cmd);
            String restart = ProcessHelper.getStreamResult(process, 20, false);
            if (!restart.contains("TimeoutException")) {
                return;
            }
        }
    }

    /**
     * Close adb.
     */
    public void closeAdb() {
        try {
            log.info("Beginning Close Adb.");
            if (OSHelper.isWindows()) {
                ProcessHelper.closePidsByName("adb.exe");
            } else {
                ProcessHelper.closePidsByName("adb");
            }
            log.info("close Adb scucessfully.");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Create emulator.
     *
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    public void createEmulator()
            throws IOException, InterruptedException, ExecutionException {
        log.info("Beginning Create emulator.");
        String cmd = this.cmdShell + "android create avd --name " + this.avdName + " --target " + this.sdkTarget + " --force --sdcard 100M";
        if (ProcessHelper.getErrorResult().contains("This platform has more than one ABI. Please specify one using --abi")) {
            log.info("enter abi configuration");
            cmd = cmd + " --abi armeabi-v7a";
            log.info("cmd = " + cmd);
        }
        log.info("cmd==" + cmd);

        Process process = Runtime.getRuntime().exec(cmd);
        Thread.sleep(2000L);

        PrintWriter stdin = new PrintWriter(process.getOutputStream());
        stdin.println("no");
        stdin.close();
        try {
            ProcessHelper.getStreamResult(process);
        } catch (IOException e) {
            log.info(e.getClass().getName());
            log.info(ProcessHelper.getErrorResult());
            if (ProcessHelper.getErrorResult().contains("This platform has more than one ABI. Please specify one using --abi")) {
                log.info(e.getClass().getName());
                createEmulator();
            } else {
                throw e;
            }
        }
        log.info("Create emulator sucessfully.");
    }

    /**
     * Close emulator.
     */
    public void closeEmulator() {
        try {
            log.info("Beginning Close emulator.");
            ProcessHelper.closePidsByName(this.emulatorProcessName);
            log.info("Close all emulators successfully.");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Delete emulator.
     *
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    public void deleteEmulator()
            throws IOException, InterruptedException, ExecutionException {
        log.info("Beginning Delete emulator.");
        String cmd = this.cmdShell + "android delete avd -n " + this.avdName;
        log.info("cmd==" + cmd);

        Process process = Runtime.getRuntime().exec(cmd);

        ProcessHelper.getStreamResult(process, 20, false);
        log.info("Delete emulator scucessfully.");
    }


    /**
     * Restart emulator.
     *
     * @throws Exception the exception
     */
    public void restartEmulator()
            throws Exception {
        closeEmulator();
        restartAdb();
        startEmulator();
    }

    /**
     * Start emulator string.
     *
     * @return the string
     * @throws Exception the exception
     */
    public String startEmulator()
            throws Exception {
        boolean start = false;
        String exception = "";
        String emulatorName = "";
        for (int i = 0; i < 3; i++) {
            try {
                log.info("Beginning Start emulator.");
                Thread.sleep(2000L);
                String cmd = this.cmdShell + "emulator -avd " + this.avdName + " -partition-size 256";
                log.info("cmd==" + cmd);
                Process process = Runtime.getRuntime().exec(cmd);
                ProcessHelper.getStreamResult(process);
                Thread.sleep(60000L);
                emulatorName = getDeviceName();
                start = waitForFullyBooted(600000, emulatorName);
                Thread.sleep(5000L);
            } catch (Exception e) {
                start = false;
                exception = e.toString();
            }
            if (!start) {
                closeEmulator();
                closeAdb();
                Thread.sleep(5000L);
            } else {
                log.info("Start emulator scucessfully.");
                return emulatorName;
            }
        }
        throw new IOException("start emulator failed after try 3 times <br>" + exception);
    }

    /**
     * Remount.
     *
     * @throws IOException the io exception
     */
    public void remount()
            throws IOException {
        log.info("Beginning adb remount.");
        for (int i = 0; i < 3; i++) {
            try {
                String cmd = this.cmdShell + " adb -s " + getDeviceName() + " remount";
                log.info("cmd==" + cmd);
                Process process = Runtime.getRuntime().exec(cmd);
                String okResult = ProcessHelper.getStreamResult(process);
                if (okResult.contains("succeeded")) {
                    log.info("adb remount successfully");
                    return;
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
        throw new IOException("adb remount failed");
    }


    private ArrayList<String> getEmulatorPid(String processName, String taskList) {
        ArrayList<String> pids = new ArrayList<String>();
        String[] processes = taskList.split("\r\n");
        for (String process1 : processes) {
            if (process1.contains(processName)) {
                log.info(processName + "==" + process1);
                String[] process = process1.split(processName);
                if (process[1] != null) {
                    String pid = process[1].trim().split(" ")[0];
                    log.info("pid==" + pid);
                    pids.add(pid);
                }
            }
        }
        return pids;
    }

    private String listDevices()
            throws Exception {
        for (int i = 0; i < 3; i++) {
            log.info("Beginning list Devices.");
            String cmd = this.cmdShell + "adb devices";
            log.info("cmd==" + cmd);

            Process process = Runtime.getRuntime().exec(cmd);
            String okResult = ProcessHelper.getStreamResult(process, 20L, true, true);
            if (!okResult.contains("TimeoutException")) {
                log.info("list Devices scucessfully.");
                return okResult;
            }
        }
        throw new Exception("Timeout");
    }

    private boolean getEmulatorStatus(String emulatorName)
            throws Exception {
        log.info("Beginning getprop init.svc.bootanim");
        String cmd = this.cmdShell + "adb -s " + emulatorName + " shell getprop init.svc.bootanim";
        log.info("cmd==" + cmd);
        Process process = null;
        process = Runtime.getRuntime().exec(cmd);
        String okResult = ProcessHelper.getStreamResult(process, 120L, true, true);
        return okResult.contains("stopped");
    }

    /**
     * Wait for fully booted boolean.
     *
     * @param timeout      the timeout
     * @param emulatorName the emulator name
     * @return the boolean
     * @throws Exception the exception
     */
    public boolean waitForFullyBooted(int timeout, String emulatorName)
            throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("startTime=" + startTime);
        long endTime = startTime + timeout;
        log.info("endTime=" + endTime);
        while (System.currentTimeMillis() < endTime) {
            boolean status = getEmulatorStatus(emulatorName);
            if (status) {
                return true;
            }
            Thread.sleep(5000);
        }
        return false;
    }

    /**
     * Gets device name.
     *
     * @return the device name
     * @throws Exception the exception
     */
    public String getDeviceName()
            throws Exception {
        String result = listDevices();
        log.info("listDevices: " + result);
        String[] splitDevices = result.split("\r\n");
        for (int j = 0; j < splitDevices.length; j++) {
            if (splitDevices[j].contains("device") && !splitDevices[j].contains("devices")) {
                log.info("splitDevice[" + j + "]: " + splitDevices[j]);
                return splitDevices[j].split("\\s")[0];
            }
        }
        throw new Exception("通过adb device 未找到设备名称");
    }

}

