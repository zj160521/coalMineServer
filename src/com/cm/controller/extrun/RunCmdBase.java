package com.cm.controller.extrun;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

@Scope("prototype")
@Component
public class RunCmdBase {

	public String cdir;
	public RunCmdLog runlog;
	// private IDeviceLogDao logdao;

	public Process process;
	
	public RunCmdBase() {
		// logdao = SpringContextHolder.getBean("IDeviceLogDao");
		runlog = new RunCmdLog();
	}

	protected int runCmd(String cmdline) {

		try {
			Runtime runtime = Runtime.getRuntime();
			if (cdir == null || cdir.isEmpty()){
				process = runtime.exec(cmdline);
			}else{
				process = runtime.exec(cmdline, null, new File(cdir));
			}
		} catch (Exception e) {
			runlog.setOpresult(-1);
			return -1;
		}

		return 0;
	}
	
	protected int waitFor(Process process) {
		int exitcode = -1;
		try {
			exitcode = process.waitFor();
		} catch (InterruptedException e) {
			runlog.setOpresult(-1);
		}
		runlog.setOpresult(exitcode);
		return exitcode;
	}

	protected void logRun(Process process) throws IOException {
		InputStream stdin = process.getInputStream();
		InputStream stderr = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stdin);
		BufferedReader br = new BufferedReader(isr);
		InputStreamReader isr2 = new InputStreamReader(stderr);
		BufferedReader br2 = new BufferedReader(isr2);

		try {
			String stdin_str = new String();
			String stderr_str = new String();
			String line1 = null;
			String line2 = null;
			while ((line1 = br.readLine()) != null) {
				stdin_str += line1 + "\n";
			}
	
			while ((line2 = br2.readLine()) != null) {
				stderr_str += line2 + "\n";
			}
	
			runlog.setStdin(stdin_str);
			runlog.setStderr(stderr_str);
		} finally {
			br.close();
			br2.close();
			isr.close();
			isr2.close();
		}
	}
	
	protected void killRun(Process process) {
		long pid = getPid(process);
		killByPid(pid);
	}

	private long getPid(Process process) {
		long pid = -1;
		Field field = null;
		if (Platform.isWindows()) {
			try {
				field = process.getClass().getDeclaredField("handle");
				field.setAccessible(true);
				pid = Kernel32.INSTANCE.GetProcessId((WinNT.HANDLE) field
						.get(process));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (Platform.isLinux()) {
			try {
				Class<?> clazz = Class.forName("java.lang.UNIXProcess");
				field = clazz.getDeclaredField("pid");
				field.setAccessible(true);
				pid = (Integer) field.get(process);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		return pid;
	}

	private void killByPid(long pid) {
		try {

			Runtime runtime = Runtime.getRuntime();

			if (Platform.isLinux()) {
				final Process killprocess = runtime.exec("kill -9 " + pid);
				killprocess.waitFor();
				killprocess.destroy();
			} else if (Platform.isWindows()) {
				final Process killprocess = runtime.exec("cmd.exe /c taskkill /PID "+pid+" /F /T ");
				killprocess.waitFor();
				killprocess.destroy();
			}

		} catch (Exception e) {
		}
	}
}
